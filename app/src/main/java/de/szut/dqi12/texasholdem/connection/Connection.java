package de.szut.dqi12.texasholdem.connection;

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

    public static String serverIP = "127.0.0.1";
    public static int serverPort = 12345;
    private Socket serverCon;
    private InputStreamReader input;
    private OutputStream output;
    private BufferedReader reader;
    private PrintWriter writer;
    private static Connection instance;
    private Controller controller;
    private boolean connectionStatus=false;


    private Connection(){
        try {
            serverCon = new Socket(serverIP,serverPort);
        } catch (IOException e) {
            //make Toast connection failed + print e. I have no Idea how to do this outside of the Main Activity tbh.
            return;
        }
        try {
            input = new InputStreamReader(serverCon.getInputStream());
            output = serverCon.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            //make Toast connection failed + print e. I have no Idea how to do this outside of the Main Activity tbh.
        }
        reader = new BufferedReader(input);
        writer = new PrintWriter(output);
        controller = Controller.getInstance();
        connectionStatus=true;
    }
    public static Connection getInstance(){
        if(instance==null){
            instance = new Connection();
        }
        return instance;
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
        controller.sendAction(ClientAction.DISCONNECT, null);
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
