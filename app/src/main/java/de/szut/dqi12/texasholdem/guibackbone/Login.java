package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha Beste on 07.02.2016.
 */
public class Login implements Recallable {

    private long timeout = 5000;
    private long timestamp = 0;
    private String recallAction = ServerAction.SESSION;
    private de.szut.dqi12.texasholdem.gui.Login loginActivity;
    private Handler mHandler;
    private String TAG = "login";

    public Login(de.szut.dqi12.texasholdem.gui.Login loginActivity) {
        this.loginActivity = loginActivity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void sendLoginRequest(String username, String password) {
        Controller con = Controller.getInstance();
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
                case "LOGGEDIN":
                    Log.d(TAG, "Logged in");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loginActivity.loginresult(0); // Login successful
                        }
                    });
                    break;
                case "LOGGINFAILED":
                    Log.d(TAG, "Login failed");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loginActivity.loginresult(1); // Login denied
                        }
                    });
                    break;
                case "LOGGEDOUT":
                    break;

            }
        }
        else if(action.equals(ServerAction.NORESPONSE)){
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
