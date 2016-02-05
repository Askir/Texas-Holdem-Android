package de.szut.dqi12.texasholdem.connection;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import de.szut.dqi12.texasholdem.Controller;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Receive extends AsyncTask< Void, Void, Integer> {

    public Receive(){

    }

    @Override
    protected Integer doInBackground(Void... params) {
        while(Controller.getInstance().getConnection().getConnectionStatus()){
            Controller.getInstance().getDecryption().addNewMessage(getMessage());
        }
        return 0;
    }



    public String getMessage(){
        BufferedReader reader = Controller.getInstance().getConnection().getReader();
        InputStreamReader input = Controller.getInstance().getConnection().getInput();
        String message = null;
        try {
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;

    }
}
