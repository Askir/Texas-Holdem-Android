package de.szut.dqi12.texasholdem.chat;

import android.os.SystemClock;

import java.util.ArrayList;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.GameAction;

/**
 * Created by Jascha Beste on 06.10.2015.
 * This class is supposed to be used for all chat operations
 * It needs to work with the Send and Decryption class in de.szut.dqi12.texasholdem.connection
 */
public class ChatController {
    private ArrayList<String> messages;

    private static ChatController instance;


    private ChatController(){
        messages = new ArrayList<String>();
    }
    public static ChatController getInstance(){
        if (instance == null)
            instance = new ChatController();
        return instance;

    }

    public void newMessageReceived(String nameSender, long time, String message){
        messages.add(nameSender + ": " + message + "    " + time);
    }

    public void sendNewMessage(String message){
        newMessageReceived("USERNAME", SystemClock.currentThreadTimeMillis(), message);
        String[] params = {"USERNAME",message};
        //IF MODE == CLIENT
        Controller.getInstance().getSend().sendAction(ClientAction.CHAT,params);
        //IF MODE == INGAME
        Controller.getInstance().getSend().sendAction(ClientAction.CHAT,params);
        //TODO where do we safe information like username etc.? should I just put that in the Controller class aswell?

    }

    public ArrayList<String> getChat(){
        return messages;
    }
}
