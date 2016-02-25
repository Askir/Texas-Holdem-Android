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

    private boolean stop = false; //private variable to stop the decryption process
    private List<String> newMessages; //List of new messages from the server
    private List<Recallable> callObjects; //List of all objects that want to be notified if specific server actions occur
    private Handler mHandler; //The handler to execute tasks on the ui thread
    private String TAG = "decryption"; //The TAG used in LOG outputs (mainly debug)

    public Decryption() {
        //synchronized lists because these need to be threadsafe
        newMessages = Collections.synchronizedList(new ArrayList<String>());
        callObjects = Collections.synchronizedList(new ArrayList<Recallable>());
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * stops the decryption progress
     */
    public void stopDecryption() {
        stop = true;
    }

    /**
     * starts the "decryption" progress
     */
    public void startDecryption() {
        stop = false;
        Thread decryptionThread = new Thread() {
            @Override
            public void run() {
                decryptProgress();
            }
        };
        decryptionThread.start();

    }

    /**
     * The decryption progress itself
     */
    public void decryptProgress() {
        //keeps running until manually stopped
        while (!stop) {
            //analyzing each message for itself
            for (String s : newMessages) {
                Log.d(TAG, "analyzing message: " + s);

                //splitting the messages into useful information
                String[] splits = s.split(";");
                String[] parameters = splits[2].split(":");
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
                    case "LOBBYUPDATE":
                        Log.d(TAG,"Lobbyupdate called");
                        lobbyUpdate(parameters);
                        break;
                    case ServerAction.CHANGE:
                        break;
                    default:
                        break;


                }
                newMessages.remove(s);
            }
            //checking if any of the callObjects timed out
            for (Recallable i : callObjects) {
                if (i.getTimeStamp() + i.getMaxWaitTIme() < System.currentTimeMillis()) { //good intent but wrong implementation need to rewrite
                    i.inform(ServerAction.NORESPONSE, null);
                    callObjects.remove(i);
                }
            }
        }
        return;
    }

    /**
     * adds an object to the callObject list which gets notified when specific server action occur
     * @param callObj the object to be called (see the Recallable interface for further information)
     */
    public void addExpectation(Recallable callObj) {
        callObjects.add(callObj);
    }

    /**
     * adds a new server message to the message list
     * @param newMessage the new server message
     */
    public void addNewMessage(String newMessage) {
        newMessages.add(newMessage);
    }

    /**
     * any gameupdate should be handled via this method
     * @param parameters the parameters of the server message
     */
    private void gameupdate(String[] parameters) {
        GameController gc = GameController.getInstance();
        switch (parameters[0]) {
            case "ALLPLAYERS":
                break;
            case "BLINDS":
                // TODO: 12.02.2016 23 out of range, have to deliver the player number which is getting the small blind.
                // todo just numbers from 0 - 5 allowed. delivered number is the number of client thatgets the small blind.0 = Player, 1 = Rival 1, 2 = Rival 2 etc.
//                gc.setBlinds(23);
                gc.setBlinds(3); // TODO: 12.02.2016 <- change
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

    /**
     * any chat update should be handled via this method (not implemented)
     * @param parameters the server parameters
     */
    private void chat(String[] parameters) {
        ChatController.getInstance().newMessageReceived(parameters[0], Integer.parseInt(parameters[1]), parameters[2]);
    }

    /**
     * any lobby updated should be handled via this method
     * @param parameters the parameters of the lobby updated
     */
    private void lobbyUpdate(String[] parameters) {
        switch (parameters[0]) {
            //a userstate update -> parsing everything to the Lobby
            case "userstates":
                for (int i = 1; i < parameters.length; i++) {
                    String[] user = parameters[i].split("#");
                    Lobby.getInstance().changeUserState(Integer.parseInt(user[0]), Boolean.parseBoolean(user[1]));
                }
                break;
            //A initial update -> parsing everything to the lobby
            case "init":
                for (int i = 1; i < parameters.length; i++) {
                    String[] user = parameters[i].split("#");
                    Log.d(TAG,"init called and user added");
                    Lobby.getInstance().changeUser(Integer.parseInt(user[0]), user[1], Boolean.parseBoolean(user[2]));
                }
                break;
            //A general update -> parsing everything to the Lobby
            case "update":
                for (int i = 1; i < parameters.length; i++) {
                    String[] user = parameters[i].split("#");
                    Lobby.getInstance().changeUser(Integer.parseInt(user[0]), user[1], Boolean.parseBoolean(user[2]));
                }
                break;
            //The game starts -> executing the correct function
            case "gamestart":
                Lobby.getInstance().gameStart();
                break;
            //unexpected server parameters:
            default:
                Log.d(TAG, "something unexpected happened with the Lobbyupdate" + parameters[0]);
                break;
        }

    }
}

