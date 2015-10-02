package de.szut.dqi12.texasholdem.connection;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Decryption {

    private List<String> newMessages;

    public Decryption() {
        newMessages = Collections.synchronizedList(new ArrayList<String>());

    }




    public void addNewMessage(String newMessage){
        newMessages.add(newMessage);
    }

    private class Decrypt extends AsyncTask<List<String>, Void, Void> {

        @Override
        protected Void doInBackground(List<String>... params) {
            List<String> newMessages = params[0];

            for(String s : newMessages){
                String[] splits  = s.split(";");
                //String action = s.substring(0, s.indexOf(";"));
                switch(splits[0]){
                    case "FOLD":
                        break;
                }
            }
            return null;
        }
    }
}

