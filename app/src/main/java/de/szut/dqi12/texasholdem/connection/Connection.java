package de.szut.dqi12.texasholdem.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Jascha on 22.09.2015.
 */
//This class is supposed to be used for the general Server connection. Exchange of data should be handled by send and receive.
public class Connection {

    public static String serverIP = "127.0.0.1";
    public static int serverPort = 12345;
    private Socket serverCon;
    private InputStream input;
    private OutputStream output;
    private static Connection instance;

    private Connection(){
        try {
            serverCon = new Socket(serverIP,serverPort);
        } catch (IOException e) {
            //make Toast connection failed + print e. I have no Idea how to do this outside of the Main Activity tbh.
            return;
        }
        try {
            input = serverCon.getInputStream();
            output = serverCon.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            //make Toast connection failed + print e. I have no Idea how to do this outside of the Main Activity tbh.
        }
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
        Send.sendClientAction(ClientAction.DISCONNECT);
        try {
            serverCon.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        input = null;
        output = null;
    }

    public OutputStream getOutput(){
        return this.output;
    }

    public InputStream getInput(){
        return this.input;
    }

}
