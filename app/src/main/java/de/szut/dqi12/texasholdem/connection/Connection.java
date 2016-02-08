package de.szut.dqi12.texasholdem.connection;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;

/**
 * Created by Jascha on 22.09.2015.
 */
//This class is supposed to be used for the general Server connection. Exchange of data should be handled by send and receive.
public class Connection {

    public static String serverIP = "192.168.178.54";
    public static int serverPort = 12346;
    private Socket serverCon;
    private InputStreamReader input;
    private OutputStream output;
    private BufferedReader reader;
    private PrintWriter writer;
    private Controller controller;
    private boolean connectionStatus=false;
    private Handler mHandler;
    private String TAG = "connection";


    private class openConnection extends AsyncTask<String,Integer , String>{
        protected String doInBackground(String... stuff){
            try {
                serverCon = new Socket(serverIP,serverPort);
            } catch (IOException e) {
                Log.d(TAG, "connection failed");
                e.printStackTrace();
                //make Toast connection failed + print e. I have no Idea how to do this outside of the Main Activity tbh.
                return null;
            }
            try {
                input = new InputStreamReader(serverCon.getInputStream());
                output = serverCon.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                //make Toast connection failed + print e. I have no Idea how to do this outside of the Main Activity tbh.
            }
            Log.d(TAG,"connected");
            reader = new BufferedReader(input);
            writer = new PrintWriter(output);
            connectionStatus=true;
            Controller.getInstance().connectionEstablished();

            return null;
        }

    }
    public Connection(){
        openConnection op = new openConnection();
        op.execute();
    }

    public boolean connect(String ip, int port){
        try {
            serverCon = new Socket(ip , port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        serverIP=ip;
        serverPort=port;
        return true;
    }

    public void disconnect(){
        Controller.getInstance().sendAction(ClientAction.DISCONNECT, null);
        try {
            serverCon.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        input = null;
        output = null;
        connectionStatus=false;
    }

    public OutputStream getOutput(){return this.output;    }

    public InputStreamReader getInput(){
        return this.input;
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public PrintWriter getWriter() {
        return this.writer;
    }

    public boolean getConnectionStatus(){
        return connectionStatus;
    }
}
