package de.szut.dqi12.texasholdem.connection;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import de.szut.dqi12.texasholdem.Controller;

/**
 * Created by Jascha on 22.09.2015.
 * This class manages receiving messages from the server
 */
public class Receive {

    private Thread receiveThread; //The thread that manages the receiving itself
    private String TAG = "Receive"; //The TAG for Log output of this class (mainly debug information)

    /**
     * creates a Receive object
     */
    public Receive() {
        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = getMessage();
                    if (message != null) {
                        Controller.getInstance().getDecryption().addNewMessage(message);
                    }
                    try {
                        Thread.sleep(100, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d(TAG, "interrupted in sleep");
                        return;
                    }
                    if (Thread.interrupted()) {
                        Log.d(TAG, "interrupted");
                        return;
                    }
                }
            }
        });
    }

    /**
     * starts the receiving process
     */
    public void startReceive() {
        receiveThread.start();
    }

    /**
     * stops the receiving process
     */
    public void stopReceive() {
        receiveThread.interrupt();
    }

    /**
     * Waits for the next server message and returns it as a string
     * @return The received Server message
     */
    public String getMessage() {
        BufferedReader reader = Controller.getInstance().getConnection().getReader();
        String message = null;
        try {
            message = reader.readLine();
        } catch (IOException e) {
            Controller.getInstance().connectionClosed();
            e.printStackTrace();
        }
        Log.d(TAG, "new message : " + message);
        return message;

    }
}
