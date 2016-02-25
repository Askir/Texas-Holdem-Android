package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha Beste on 11.11.2015.
 * /F0010/ Registrierung
 */
public class Register implements Recallable {

    //Handler to call functions in the register activity on the UI thread
    private Handler mHandler; //The handler used to execute tasks on the ui thread
    //reference to the register activity to callback the function through the Handler thread after the Registration has been completed
    private de.szut.dqi12.texasholdem.gui.Register registerActivity; //the currently activer register activity
    private long timeout = 5000; //The maximum time in milliseconds that this class will wait for a server response
    private long timestamp = 0;  //The timestamp used for the recallable interface (always update this before you call: addExpectation())

    /**
     * creates a new Register object
     * @param registerActivity the currently active register activity
     */
    public Register(de.szut.dqi12.texasholdem.gui.Register registerActivity) {
        //linking the Handler with the UI Thread
        mHandler = new Handler(Looper.getMainLooper());
        this.registerActivity = registerActivity;
    }

    /*
    username = username
    password = password
    repassword = retyped password
    email = email Address
    return value is true if the request has been executed; false if the passwords do not match

     */

    /**
     * executes a register attempt with the given values
     * @param username the requested username
     * @param password the requested password (will be hashed with SHA-256)
     * @param repassword the requested password for confirmation
     * @param email the email to be registered with
     * @return returns if the passwords match
     */
    public boolean executeRegister(String username, String password, String repassword, String email) {

        if (password.equals(repassword)) {
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            messageDigest.update(password.getBytes());
            String encryptedPassword = new String(messageDigest.digest());
            String[] registerContent = {username, encryptedPassword, email};
            Controller.getInstance().getSend().sendAction(ClientAction.REGISTER, registerContent);
            Controller.getInstance().getDecryption().addExpectation(this);
            timestamp = System.currentTimeMillis();

            return true;
        } else {
            return false;
        }
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
    public void inform(String action, final String[] params) {
        if (action.equals(ServerAction.REGISTERACK)) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    registerActivity.inform(params[0]);
                }
            });
        }
        else{
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    registerActivity.inform("timeout");
                }
            });
        }
    }

    @Override
    public String Action() {
        return ServerAction.REGISTERACK;
    }

    @Override
    public String Params() {
        return null;
    }
}
