package de.szut.dqi12.texasholdem;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.connection.Connection;
import de.szut.dqi12.texasholdem.connection.Decryption;
import de.szut.dqi12.texasholdem.connection.Receive;
import de.szut.dqi12.texasholdem.connection.Send;
import de.szut.dqi12.texasholdem.guibackbone.Options;

/**
 * Created by Alex on 28.09.2015.
 */
public class Controller {

    private Connection connection;
    private Send send;
    private Receive receive;
    private Decryption decryption;
    private long ping;
    private Options options;
    private Activity activeActivity;
    private Handler mHandler;


    public Activity getActiveActivity() {
        return activeActivity;
    }

    public void setActiveActivity(Activity activeActivity) {
        this.activeActivity = activeActivity;
    }



    public static Controller instance;


    private Controller(){}

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
            instance.start();
        }
        return instance;
    }

    private void start() {
        Log.d("controller", "start started");
        decryption = new Decryption();
        connection = new Connection();
        send = new Send();
        receive = new Receive();
        options = new Options();
        decryption.startDecryption();
        Log.d("controller", "start finished");
    }
    public void connectionEstablished(){
        receive.startReceive();
    }
    public void connectionClosed(){
        receive.stopReceive();
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activeActivity, "Connection lost", Toast.LENGTH_SHORT);
            }
        });

    }
    public void stop(){
        receive.stopReceive();
    }
    public Options getOptions(){
        return options;
    }

    public boolean sendAction(String action, String[] params) {
        return send.sendAction(action, params);
    }
    public Connection getConnection() {
        return connection;
    }

    public Receive getReceive() {
        return receive;
    }

    public Decryption getDecryption() {return decryption;}

    public Send getSend() {
        return send;
    }

    public void setPing(long ping) {
        this.ping = ping;
    }

    public long getPing(){
        return ping;
    }
}
