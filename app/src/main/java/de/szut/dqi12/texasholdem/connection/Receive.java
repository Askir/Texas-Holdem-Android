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
 */
public class Receive {

    private Thread receiveThread;
    private String TAG = "Receive";

    public Receive() {
        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Controller.getInstance().getDecryption().addNewMessage(getMessage());
                    try {
                        Thread.sleep(100,0);
                    } catch (InterruptedException e) {
                        Log.d(TAG,"interrupted in sleep");
                        return;
                    }
                    if (Thread.interrupted()) {
                        Log.d(TAG,"interrupted");
                        return;
                    }
                }
            }
        });
    }

    public void startReceive() {
        receiveThread.start();
    }

    public void stopReceive() {
        receiveThread.interrupt();
    }


    public String getMessage() {
        BufferedReader reader = Controller.getInstance().getConnection().getReader();
        InputStreamReader input = Controller.getInstance().getConnection().getInput();
        String message = null;
        try {
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "new message : " + message);
        return message;

    }
}
