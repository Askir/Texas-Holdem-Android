package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;
import de.szut.dqi12.texasholdem.gui.ChangePassword;
import de.szut.dqi12.texasholdem.gui.Settings;

/**
 * Created by Jascha on 09.10.2015.
 * This class handles everything in regards to settings and options in the game
 */
public class Options {

    private static int volume = 0; // The current volume level
    private static String email; // The current active email (unused atm)
    private static String username; // The currently active username (should be safed in a database for the next login)  TODO: implement this comment
    private static DatabaseConnection dbCon; //The databse connection to safe our data between appstarts

    //just a static codeblock for initialization of variables
    static {
        dbCon = new DatabaseConnection();

        volume = dbCon.getVolume();
        username = dbCon.getUsername();
    }

    public static void onLogin(String email) {
        Options.email = email;
    }

    /**
     * Issueing a password change request
     * @param changePasswordActivity the currently active changePassword activity
     * @param oldPassword The old password
     * @param newPassword The new password
     */
    public static void changePassword(final ChangePassword changePasswordActivity, String oldPassword, String newPassword) {
        String[] params = {oldPassword, newPassword};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEPASSWORD, params);
        Controller.getInstance().getDecryption().addExpectation(new Recallable() {
            long timestamp = System.currentTimeMillis();

            @Override
            public long getMaxWaitTIme() {
                return 5000;
            }

            @Override
            public long getTimeStamp() {
                return timestamp;
            }

            @Override
            public void inform(String action, String[] params) {
                Handler mHandler = new Handler(Looper.getMainLooper());
                if (action.equals(ServerAction.CHANGE)) {
                    changePasswordActivity.passwordChange(params[1]);
                } else {
                    changePasswordActivity.passwordChange("Server timeout");
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
        });
        return;
    }

    /**
     * This function requests a new password from the server
     */
    public static void forgotPassword() {
        //TODO fill function
        return;
    }

    /**
     * Set the volume
     * @param value the new volume value
     */
    public static void setVolume(int value) {
        Options.volume = value;
    }

    /**
     *
     * @return the active username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * This funtion issues a changeusername request
     * @param settingsActivity the currently active settings activity
     * @param username the new username
     */
    public static void changeUsername(final Settings settingsActivity, String username) {

        String[] params = {username};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEUSERNAME, params);
        Controller.getInstance().getDecryption().addExpectation(new Recallable() {
            long timestamp = System.currentTimeMillis();

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
                    if (params[1].equals("true")) {
                        Options.username = params[2];
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
        });
        return;
    }

    /**
     * This function issues a changeEmail request
     * @param settingsActivity the currently active settings activity
     * @param email the new email address
     */
    public static void changeEmail(final Settings settingsActivity, String email) {
        String[] params = {email};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEEMAILADDRESS, params);
        Controller.getInstance().getDecryption().addExpectation(new Recallable() {

            long timestamp = System.currentTimeMillis();

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
        });
        return;
    }

    /**
     * This method should be called if the app closes to safe all data in the database
     */
    public static void onClose() {
        dbCon.setVolume(volume);
        dbCon.setUsername(username);
    }

}
