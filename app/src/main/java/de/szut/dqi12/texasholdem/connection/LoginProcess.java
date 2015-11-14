package de.szut.dqi12.texasholdem.connection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.gui.Login;

/**
 * Created by Jascha on 14.11.2015.
 */
public class LoginProcess extends AsyncTask<String,Integer , Boolean> {
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
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Boolean doInBackground(String... params) {
        String username;
        String password;
        username = params[0];
        password = params[1];

        Controller.getInstance().getCurrentSession().login(username,password);
        boolean connected = false;
        int i = 0;
        while(i<=timeout && !connected){
            i++;
            connected = Controller.getInstance().getCurrentSession().getConnectionStatus();
            SystemClock.sleep(1000);
        }
        return connected;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result == true){
            //login.loggedIN();
        }
        else{
            //login.loginFailed();
        }
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
}
