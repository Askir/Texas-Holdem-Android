package de.szut.dqi12.texasholdem.connection;

import android.os.AsyncTask;
import android.util.Log;

import de.szut.dqi12.texasholdem.Controller;

/**
 * Created by Alex on 02.10.2015.
 */
public class SendMessage extends AsyncTask<String, Void, Void> {

    private Controller controller;

    public SendMessage() {
        controller = Controller.getInstance();
    }

    @Override
    protected Void doInBackground(String... strings) {
        if(Controller.getInstance().getConnection().getConnectionStatus()) {
            controller.getConnection().getWriter().println(strings[0]);
            controller.getConnection().getWriter().flush();
            Log.d("send", strings[0]);
        }
        return null;
    }
}
