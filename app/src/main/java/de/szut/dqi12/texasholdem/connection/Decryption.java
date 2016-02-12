package de.szut.dqi12.texasholdem.connection;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.chat.ChatController;
import de.szut.dqi12.texasholdem.game.GameController;
import de.szut.dqi12.texasholdem.guibackbone.Lobby;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Decryption {

    private boolean stop = false;
    private List<String> newMessages;
    private List<Recallable> callObjects;
    private Handler mHandler;
    private String TAG = "decryption";

    public Decryption() {
        newMessages = Collections.synchronizedList(new ArrayList<String>());
        callObjects = Collections.synchronizedList(new ArrayList<Recallable>());
        mHandler = new Handler(Looper.getMainLooper());
    }

    //complex threading stuff probably will clean this up later on
    public void stopDecryption() {
        stop = true;
    }

    public void startDecryption() {
        stop = false;
        Thread decryptionThread = new Thread() {
            @Override
            public void run() {
                decryptProgress();
            }
        };
        decryptionThread.start();
        /**final Decrypt dec = new Decrypt();
         Thread decryptionThread = new Thread () {
        @Override public void run(){
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

    public void decryptProgress() {
        while (!stop) {
            for (String s : newMessages) {
                Log.d(TAG, "analyzing message: " + s);

                //splitting the messages into useful information
                String[] splits = s.split(";");
                String[] parameters = splits[2].split(":");
                //String action = s.substring(0, s.indexOf(";"));
                //updating ping info
                Controller.getInstance().setPing(System.currentTimeMillis() - Long.parseLong(splits[0]));
                Log.d(TAG, "action is:" + splits[1]);
                Log.d(TAG, "first param is:" + parameters[0]);

                //calling the callObjects that expect a specific action
                ArrayList<Recallable> callobjectsiterabble = new ArrayList<>(callObjects);
                for (Recallable i : callobjectsiterabble) {
                    Log.d(TAG, "checking object:" + i.getClass().getName() + "with action: " + i.Action() + " and params: " + i.Params());
                    if (i.Action().equals(splits[1])) {
                        if (i.Params() == null) {
                            Log.d(TAG, "informing");
                            i.inform(splits[1], parameters);
                            callObjects.remove(i);
                        } else if (i.Params().equals(parameters[0])) {
                            Log.d(TAG, "informing with params");
                            i.inform(splits[1], parameters);
                            callObjects.remove(i);
                        }
                    }
                }
                //Calling the correct function for every executed command
                switch (splits[1]) {
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
                        Log.d(TAG,"Lobbyupdate called");
                        lobbyUpdate(parameters);
                        break;
                    case ServerAction.CHANGE:


                }
                newMessages.remove(s);
            }
            for (Recallable i : callObjects) {
                if (i.getTimeStamp() + i.getMaxWaitTIme() < System.currentTimeMillis()) { //good intent but wrong implementation need to rewrite
                    i.inform(ServerAction.NORESPONSE, null);
                    callObjects.remove(i);
                }
            }
        }
        return;
    }

    public void addExpectation(Recallable callObj) {
        callObjects.add(callObj);
    }


    public void addNewMessage(String newMessage) {
        newMessages.add(newMessage);
    }


    private void gameupdate(String[] parameters) {
        GameController gc = GameController.getInstance();
        switch (parameters[0]) {
            case "ALLPLAYERS":
                break;
            case "BLINDS":
                gc.setSmallBlind(23);
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
            case "WINNNER":
                break;
            default:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Controller.getInstance().getActiveActivity(), "If this toast is shown Frederick was drunk", Toast.LENGTH_LONG);
                    }
                });
                break;
        }

    }

    private void chat(String[] parameters) {
        ChatController.getInstance().newMessageReceived(parameters[0], Integer.parseInt(parameters[1]), parameters[2]);
    }

    private void needValidation(String[] parameters) {

    }

    private void gameList(String[] parameters) {

    }

    private void lobbyUpdate(String[] parameters) {
        switch (parameters[0]) {
            case "userstates":
                for (int i = 1; i < parameters.length; i++) {
                    String[] user = parameters[i].split("#");
                    Lobby.getInstance().changeUserState(Integer.parseInt(user[0]), Boolean.parseBoolean(user[1]));
                }
                break;
            case "init":
                for (int i = 1; i < parameters.length; i++) {
                    String[] user = parameters[i].split("#");
                    Log.d(TAG,"init called and user added");
                    Lobby.getInstance().changeUser(Integer.parseInt(user[0]), user[1], Boolean.parseBoolean(user[2]));
                }
                break;
            case "update":
                for (int i = 1; i < parameters.length; i++) {
                    String[] user = parameters[i].split("#");
                    Lobby.getInstance().changeUser(Integer.parseInt(user[0]), user[1], Boolean.parseBoolean(user[2]));
                }
                break;
            case "gamestart":
                Lobby.getInstance().gameStart();
                break;
            default:
                Log.d(TAG, "something unexpected happened with the Lobbyupdate" + parameters[0]);
        }

    }
}

