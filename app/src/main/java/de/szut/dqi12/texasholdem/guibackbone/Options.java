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
 */
public class Options {

    private static int volume = 0; //Database
    private static String email; // Serverside only
    private static String username; // Database and Serverside (update on programm start)7
    private static DatabaseConnection dbCon;

    static {
        dbCon = new DatabaseConnection();

        volume = dbCon.getVolume();
        username = dbCon.getUsername();

    }

    public static void onLogin(String email) {
        Options.email = email;
    }

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

    public static boolean forgotPassword() {
        //TODO fill function
        return true;
    }

    public static void setVolume(int value) {
        Options.volume = value;
    }

    public static String getUsername() {
        return username;
    }

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

    public static void onClose() {
        dbCon.setVolume(volume);
        dbCon.setUsername(username);
    }

}
