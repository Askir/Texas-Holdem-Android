package de.szut.dqi12.texasholdem.connection.session;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jascha Beste on 16.10.2015.
 */
public class Session implements Recallable {

    private String expectedAction;
    private String[] expectedParams;
    private ConnectionStatus connected = ConnectionStatus.DISCONNECTED;
    private long timestamp = 0;
    private long timeout = 5000;

    public ConnectionStatus getConnectionStatus(){
        return connected;
    }


    public Session(){
        expectedAction = null;
        expectedParams = null;
    }
    
    public void login(String username, String password){
        //password is being hashed for transmittion
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());
        String encryptedPassword = new String(messageDigest.digest());
        String[] login = {username,encryptedPassword};

        //sending the login request to the server and waiting for an answer
        timestamp = System.currentTimeMillis();
        Controller.getInstance().getSend().sendAction(ClientAction.LOGIN,login);
        expectedAction = ServerAction.SESSION;
        Controller.getInstance().getDecryption().addExpectation(this);
        this.timestamp = System.currentTimeMillis();

    }

    public void logout(){
        expectedAction = ServerAction.SESSION;
    }


    public long getMaxWaitTIme() {
        return timeout;
    }


    public long getTimeStamp() {
        return timestamp;
    }

    @Override
    public void inform(String action, String[] params) {
        switch(action) {
            case ServerAction.SESSION:
                switch (params[0]){
                    case "FAILEDLOGIN":
                        connected = ConnectionStatus.FAILEDLOGIN;
                        //TODO inform GUI about failed login attempt
                        switch(params[1]){
                            case "PW":

                                //wrong username password combination
                                break;
                            default:
                                //something else went wrong maybe server overload
                                break;
                        }

                        break;
                    case "LOGGEDIN":
                        connected = ConnectionStatus.CONNECTED;
                        //TODO inform GUI and send necessary info to the server etc.
                        break;
                    case "LOGGEDOUT":
                        connected = ConnectionStatus.DISCONNECTED;
                        //TODO inform GUI and the whole programm about the successful disconnect
                        break;
            }

        }
    }

    @Override
    public String Action() {
        return expectedAction;
    }

    @Override
    public String[] Params() {
        return expectedParams;
    }

}
