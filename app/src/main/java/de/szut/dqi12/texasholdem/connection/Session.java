package de.szut.dqi12.texasholdem.connection;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jascha Beste on 16.10.2015.
 */
public class Session implements Recallable {

    private String expectedAction;
    private String[] expectedParams;
    private Controller con;

    public Session(){
        con = Controller.getInstance();
        expectedAction = null;
        expectedParams = null;
    }

    public void login(String username, String password){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());
        String encryptedPassword = new String(messageDigest.digest());
        String[] login = {username,encryptedPassword};
        con.getSend().sendAction(ClientAction.LOGIN,login);
        expectedAction = ServerAction.LOGGEDIN;
        con.getDecryption().addExpectation(this);

    }

    @Override
    public void inform(String action, String[] params) {
        switch(action) {
            case ServerAction.LOGGEDIN:
                //TODO inform GUI and send necessary info to the server etc.
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
