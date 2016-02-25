package de.szut.dqi12.texasholdem.connection;

import android.util.Log;

import de.szut.dqi12.texasholdem.Controller;

/**
 * Created by Jascha on 22.09.2015.
 * This class manages sending messages to the server
 */
public class Send {

    private String message; //The message that will be send

    public Send() {

    }

    /**
     * Sends a message to the server
     * @param action the specified action that should be send
     * @param params the parameters that should be send to the server
     * @return returns true if no error has occured
     */
    public boolean sendAction(String action, String params[]) {
        SendMessage sendMessage = new SendMessage();
        Log.d("send", "sending message action: " + action);
        message = String.valueOf(System.currentTimeMillis()) + ";" + action;
        if (params != null) {
            message += ";";
            for (String param : params) {
                message += param + ":";
            }
            message = message.substring(0, message.length() - 1);
        }
        sendMessage.execute(message);
        return true;
    }
}
