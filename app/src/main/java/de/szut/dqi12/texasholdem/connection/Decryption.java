package de.szut.dqi12.texasholdem.connection;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

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

    private boolean stop = false;
    private List<String> newMessages;
    private List<Recallable> callObjects;
    private Handler mHandler;
    public Decryption() {
        newMessages = Collections.synchronizedList(new ArrayList<String>());
        callObjects = Collections.synchronizedList(new ArrayList<Recallable>());
        mHandler = new Handler(Looper.getMainLooper());
    }
    //complex threading stuff probably will clean this up later on
    public void stopDecryption(){
        stop=true;
    }

    public void startDecryption(){

        Thread decryptionThread = new Thread(){
            @Override
            public void run(){
                decryptProgress();
            }
        };
        decryptionThread.start();
        /**final Decrypt dec = new Decrypt();
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
         **/


    }
    public void decryptProgress(){
        while(!stop){
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
                case ServerAction.CHANGE:


            }
            newMessages.remove(s);
            }
        }
        return;
    }

    public void addExpectation(Recallable callObj){
        callObjects.add(callObj);
    }



    public void addNewMessage(String newMessage){
        newMessages.add(newMessage);
    }


    private void gameupdate(String[] parameters){
        switch(parameters[0]){
            case "ALLPLAYERS":
                break;
            case "BLINDS":
                break;
            case "MONEYUPDATE":
                break;
            case "BIDUPDATE":
                break;
            case "CURRENTPLAYER":
                break;
            case "BOARDCARDS":
                break;
            case "POTMONEY":
                break;
            case "PLAYERCARDS":
                break;
            case "PLAYERLEFT":
                break;
            case "HANDCARDS":
                break;
            case "WINNNER" :
                break;
            default:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Controller.getInstance().getActiveActivity(),"If this toast is shown Frederick was drunk",Toast.LENGTH_LONG);
                    }
                });
                break;
        }

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

