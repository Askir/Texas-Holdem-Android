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
 * This class manages the connection to the server
 */
//This class is supposed to be used for the general Server connection. Exchange of data should be handled by send and receive.
public class Connection {

    public static String serverIP = "192.168.178.54"; //The ip of the server
    public static int serverPort = 8888; //The port of the Server
    private Socket serverCon; //The connected socket
    private InputStreamReader input; //The inputstream of the socketconnection
    private OutputStream output; //The outputstream of the socketconnection
    private BufferedReader reader; //The connected reader
    private PrintWriter writer; //The connected writer
    private boolean connectionStatus = false; //The status of the connection (default: not connected)
    private String TAG = "connection"; //The TAG for log outputs (mostly debug)

    //open connection class that connects to the server in a second thread (connecting sockets in the ui thread is not possible in android)
    private class openConnection extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... stuff) {
            try {
                serverCon = new Socket(serverIP, serverPort);
            } catch (IOException e) {
                Log.d(TAG, "connection failed");
                e.printStackTrace();
                return null;
            }
            try {
                input = new InputStreamReader(serverCon.getInputStream());
                output = serverCon.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            Log.d(TAG, "connected");
            reader = new BufferedReader(input);
            writer = new PrintWriter(output);
            connectionStatus = true;
            Controller.getInstance().connectionEstablished();

            return null;
        }

    }

    public Connection() {
        openConnection op = new openConnection();
        op.execute();
    }

    /**
     * connects the client to another server
     * @param ip the new ip address
     * @param port the new port
     * @return if the connection was successful or not
     */
    public boolean connect(String ip, int port) {
        try {
            serverCon = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        serverIP = ip;
        serverPort = port;
        return true;
    }

    /**
     * disconnects the client
     */
    public void disconnect() {
        Controller.getInstance().sendAction(ClientAction.DISCONNECT, null);
        try {
            serverCon.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        input = null;
        output = null;
        connectionStatus = false;
    }

    public OutputStream getOutput() {
        return this.output;
    }

    public InputStreamReader getInput() {
        return this.input;
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public PrintWriter getWriter() {
        return this.writer;
    }

    public boolean getConnectionStatus() {
        return connectionStatus;
    }
}
