package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;
import de.szut.dqi12.texasholdem.gui.ChangePassword;
import de.szut.dqi12.texasholdem.gui.Settings;

/**
 * Created by Jascha on 09.10.2015.
 * This class handles everything in regards to settings and options in the game
 * /F0070/ Einstellungen
 */
public class Options {

    private int volume = 0; // The current volume level
    private String email; // The current active email (unused atm)
    private String username; // The currently active username (should be safed in a database for the next login)  TODO: implement this comment
    private DatabaseConnection dbCon; //The databse connection to safe our data between appstarts
    private static String TAG = "Options";
    private static Options instance;

    //just a static codeblock for initialization of variables
    public static Options getInstance(){
        if(instance == null){
            instance = new Options();
        }
        return instance;
    }
    private Options(){
        dbCon = new DatabaseConnection();

        volume = dbCon.getVolume();
        username = dbCon.getUsername();

    }

    private class PasswordChange implements Recallable{
        ChangePassword changePasswordActivty;
        private long timestamp;
        public PasswordChange(ChangePassword cp) {
            changePasswordActivty = cp;
            timestamp = System.currentTimeMillis();
        }

        @Override
        public long getMaxWaitTIme() {
            return 5000;
        }

        @Override
        public long getTimeStamp() {
            return timestamp;
        }

        @Override
        public void inform(String action, final String[] params) {
            Log.d(TAG,"password change"+params[0]);
            Handler mHandler = new Handler(Looper.getMainLooper());
            if (action.equals(ServerAction.CHANGE)) {mHandler.post(new Runnable() {
                @Override
                public void run() {
                    changePasswordActivty.passwordChange(params[1]);
                }
            });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePasswordActivty.passwordChange("Server timeout");
                    }
                });

            }
        }

        @Override
        public String Action() {
            return ServerAction.CHANGE;
        }

        @Override
        public String Params() {
            return "password";
        }


    }
    private class UsernameChange implements Recallable{
        private long timestamp;
        private Settings settingsActivity;
        public UsernameChange(Settings settingsActivity){
            this.settingsActivity = settingsActivity;
            timestamp = System.currentTimeMillis();
        }

        @Override
        public long getMaxWaitTIme() {
            return 5000;
        }

        @Override
        public long getTimeStamp() {
            return timestamp;
        }

        @Override
        public void inform(String action, final String[] params) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            if (action.equals(ServerAction.CHANGE)) {
                Log.d(TAG,"username change"+params[2]);
                if (params[1].equals("true")) {
                    username = params[2];
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        settingsActivity.usernameChange(params[1]);

                    }
                });

            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        settingsActivity.usernameChange("Server timeout");
                    }
                });

            }

        }

        @Override
        public String Action() {
            return ServerAction.CHANGE;
        }

        @Override
        public String Params() {
            return "username";
        }

    }
    private class EmailChange implements Recallable{
        private long timestamp;
        private Settings settingsActivity;
        public EmailChange(Settings settingsActivity){
            this.settingsActivity=settingsActivity;
            timestamp = System.currentTimeMillis();
        }

        @Override
        public long getMaxWaitTIme() {
            return 5000;
        }

        @Override
        public long getTimeStamp() {
            return timestamp;
        }

        @Override
        public void inform(String action, final String[] params) {
            Log.d(TAG,"email change"+params[0]);
            Handler mHandler = new Handler(Looper.getMainLooper());
            if (action.equals(ServerAction.CHANGE))
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        settingsActivity.emailChange(params[1]);
                    }
                });
            else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        settingsActivity.emailChange("Server timeout");
                    }
                });
            }

        }

        @Override
        public String Action() {
            return ServerAction.CHANGE;
        }

        @Override
        public String Params() {
            return "email";
        }

    }

    public void onLogin(String email) {
        email = email;
    }

    /**
     * Issueing a password change request
     * @param changePasswordActivity the currently active changePassword activity
     * @param oldPassword The old password
     * @param newPassword The new password
     */
    // /F0220/ Passwort
    public void changePassword(final ChangePassword changePasswordActivity, String oldPassword, String newPassword) {
        String[] params = {oldPassword, newPassword};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEPASSWORD, params);
        Controller.getInstance().getDecryption().addExpectation(new PasswordChange(changePasswordActivity));
        return;
    }

    /**
     * This function requests a new password from the server
     */
    public void forgotPassword() {
        //TODO fill function
        return;
    }

    /**
     * Set the volume
     * @param value the new volume value
     */
    // /F0240/ Spielmusik
    public void setVolume(int value) {volume = value;
    }

    /**
     *
     * @return the active username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This funtion issues a changeusername request
     * @param settingsActivity the currently active settings activity
     * @param username the new username
     */
    // /F0210/ Benutzername
    public void changeUsername(final Settings settingsActivity, String username) {

        String[] params = {username};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEUSERNAME, params);
        Controller.getInstance().getDecryption().addExpectation(new UsernameChange(settingsActivity));
        return;
    }

    /**
     * This function issues a changeEmail request
     * @param settingsActivity the currently active settings activity
     * @param email the new email address
     */
    // /F0230/ Email
    public void changeEmail(final Settings settingsActivity, String email) {
        String[] params = {email};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEEMAILADDRESS, params);
        Controller.getInstance().getDecryption().addExpectation(new EmailChange(settingsActivity));
        return;
    }

    /**
     * This method should be called if the app closes to safe all data in the database
     */
    public void onClose() {
        dbCon.setVolume(volume);
        dbCon.setUsername(username);
    }

}
