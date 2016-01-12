package de.szut.dqi12.texasholdem.connection;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.chat.ChatController;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Decryption {

    private List<String> newMessages;
    private List<Recallable> callObjects;
    public Decryption() {
        newMessages = Collections.synchronizedList(new ArrayList<String>());
        callObjects = Collections.synchronizedList(new ArrayList<Recallable>());


    }
    //complex threading stuff probably will clean this up later on
    public void startDecryption(){
        final Decrypt dec = new Decrypt();
        Thread decryptionThread = new Thread () {
            @Override
            public void run(){
                while(true){
                    if(dec.getStatus() == AsyncTask.Status.FINISHED){
                    dec.execute();}
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        decryptionThread.start();


    }

    public void addExpectation(Recallable callObj){
        callObjects.add(callObj);
    }



    public void addNewMessage(String newMessage){
        newMessages.add(newMessage);
    }

    private class Decrypt extends AsyncTask<List<String>, Void, Void> {

        @Override
        protected Void doInBackground(List<String>... params) {

            for(String s : newMessages){
                //splitting the messages into useful information
                String[] splits  = s.split(";");
                String[] parameters = splits[2].split(":");
                //String action = s.substring(0, s.indexOf(";"));
                //updating ping info
                Controller.getInstance().setPing(System.currentTimeMillis()-Integer.parseInt(splits[0]));

                //calling the callObjects that expect a specific action
                for(Recallable i:callObjects){
                    if(i.Action()==splits[0]){
                        if(i.Params()==null){
                            i.inform(splits[0],parameters);
                        }
                        if(i.Params()==parameters){
                            i.inform(splits[0],parameters);
                        }
                    }
                    else if(i.getTimeStamp()+i.getMaxWaitTIme()>System.currentTimeMillis()){
                        i.inform(ServerAction.NORESPONSE,null);
                    }
                }
                //Calling the correct function for every executed command
                switch(splits[0]){
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
                newMessages.remove(s);
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

