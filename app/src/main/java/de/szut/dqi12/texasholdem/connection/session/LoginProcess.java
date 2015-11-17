package de.szut.dqi12.texasholdem.connection.session;

import android.os.AsyncTask;
import android.os.SystemClock;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.connection.LoginResult;
import de.szut.dqi12.texasholdem.gui.Login;

/**
 * Created by Jascha on 14.11.2015.
 */
public class LoginProcess extends AsyncTask<String,Integer , ConnectionStatus> {
    private int timeout = 5;
    private Login login;

    public LoginProcess(Login login){
        this.login=login;
    }
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param loginArray The login array to be processed.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected ConnectionStatus doInBackground(String... loginArray) {
        String username;
        String password;
        username = loginArray[0];
        password = loginArray[1];

        Controller.getInstance().getCurrentSession().login(username,password);
        ConnectionStatus connected = ConnectionStatus.DISCONNECTED;
        int i = 0;
        while(i<=timeout && connected==ConnectionStatus.DISCONNECTED){
            i++;
            connected = Controller.getInstance().getCurrentSession().getConnectionStatus();
            SystemClock.sleep(1000);
        }
        return connected;
    }

    @Override
    protected void onPostExecute(ConnectionStatus result) {
        switch(result){
            case CONNECTED:
                login.loginstatus(LoginResult.USERDATACORRECT);
                break;
            case FAILEDLOGIN:
                login.loginstatus(LoginResult.USERDATAINCORRECT);
                break;
            case DISCONNECTED:
                login.loginstatus(LoginResult.TIMEOUT);
                break;
        }
    }

}
