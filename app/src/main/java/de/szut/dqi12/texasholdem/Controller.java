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
import de.szut.dqi12.texasholdem.game.GameController;
import de.szut.dqi12.texasholdem.guibackbone.Options;

/**
 * Created by Alex on 28.09.2015.
 * Singelton that manages the whole application
 */
public class Controller {

    private Connection connection;
    private Send send;
    private Receive receive;
    private Decryption decryption;
    private long ping;
    private Activity activeActivity;
    private Handler mHandler;
    private String username;
    private GameController activeGame;

    public void setActiveGame(GameController activeGame){
        this.activeGame = activeGame;
    }

    public GameController getActiveGame(){
        return activeGame;
    }


    public Activity getActiveActivity() {
        return activeActivity;
    }

    public void setActiveActivity(Activity activeActivity) {
        this.activeActivity = activeActivity;
    }


    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
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
                Toast.makeText(activeActivity.getBaseContext(), "Connection lost", Toast.LENGTH_SHORT);
            }
        });

    }
    public void stop(){
        receive.stopReceive();
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
