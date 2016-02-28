package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha Beste on 07.02.2016.
 * This class manages login in with your account
 * /F0020/ Anmeldung
 * /F0030/ Abmeldung
 */
public class Login implements Recallable {

    private long timeout = 5000; //The maximum time in milliseconds that this class will wait for a server response
    private long timestamp = 0; //The timestamp used for the recallable interface (always update this before you call: addExpectation())
    private String recallAction = ServerAction.SESSION; //The ServerAction this class is waiting for (always update this before you call: addExpectation())
    private de.szut.dqi12.texasholdem.gui.Login loginActivity; //The currently active loginActivity
    private Handler mHandler; //The handler to execute tasks on the ui thread
    private String TAG = "login"; //The TAG for log outputs (mainly debug)

    /**
     * creates a new Login object
     * @param loginActivity the currently active login activity
     */
    public Login(de.szut.dqi12.texasholdem.gui.Login loginActivity) {
        this.loginActivity = loginActivity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * executes a login request (the active login activity will be informed about the result)
     * @param username the username used in the request
     * @param password the password used in the request (will be hashed with SHA-256)
     */
    public void sendLoginRequest(String username, String password) {
        Controller con = Controller.getInstance();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());
        String encryptedPassword = new String(messageDigest.digest());
        String[] params = {username, password};
        timestamp = System.currentTimeMillis();
        con.getDecryption().addExpectation(this);
        con.getSend().sendAction(ClientAction.LOGIN, params);
    }

    @Override
    public long getMaxWaitTIme() {
        return timeout;
    }

    @Override
    public long getTimeStamp() {
        return timestamp;
    }

    @Override
    public void inform(String action, String[] params) {
        if (action.equals(ServerAction.SESSION)) {
            switch (params[0]) {
                case "LOGIN":
                    //successful login infroming GUI
                    Log.d(TAG, "Logged in");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loginActivity.loginresult(0); // Login successful
                        }
                    });
                    break;
                case "FAILEDLOGIN":
                    //login has been denied (incrorrect password or username) informing GUI
                    Log.d(TAG, "Login failed");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loginActivity.loginresult(1); // Login denied
                        }
                    });
                    break;
                case "LOGOUT":
                    //Logout successful
                    //TODO: implement logout and inform GUI afterwards
                    break;

            }
        } else if (action.equals(ServerAction.NORESPONSE)) {
            //The server timedout informing GUI
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    loginActivity.loginresult(2); // Server timeout
                }
            });
        }
    }

    @Override
    public String Action() {
        return recallAction;
    }

    @Override
    public String Params() {
        return null;
    }
}
