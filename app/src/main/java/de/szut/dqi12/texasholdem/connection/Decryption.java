package de.szut.dqi12.texasholdem.connection;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.chat.ChatController;

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
                String[] parameters = splits[2].split(":");
                //String action = s.substring(0, s.indexOf(";"));
                Controller.getInstance().setPing(System.currentTimeMillis()-Integer.parseInt(splits[0]));
                switch(splits[1]){
                    case "GAMEUPDATED":
                        gameupdate(parameters);
                        break;
                    case "CHAT":
                        chat(parameters);
                        break;
                    case "NEEDVALIDATION":
                        needValidation(parameters);
                        break;
                    case "GAMELIST":
                        gameList(parameters);
                        break;
                    case "LOBBYUPDATE":
                        lobbyUpdate(parameters);
                        break;
                }
            }
            return null;
        }
    }

    private void gameupdate(String[] parameters){

    }

    private void chat(String[] parameters){
        ChatController.getInstance().newMessageReceived(parameters[0], Integer.parseInt(parameters[1]), parameters[2]);
    }

    private void needValidation(String[] parameters){

    }

    private void gameList(String[] parameters){

    }

    private void lobbyUpdate(String[] parameters){

    }
}

