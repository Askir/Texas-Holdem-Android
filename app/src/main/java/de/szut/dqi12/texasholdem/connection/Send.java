package de.szut.dqi12.texasholdem.connection;

import android.util.Log;

import de.szut.dqi12.texasholdem.Controller;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Send {

    private String message;
    private SendMessage sendMessage;

    public Send() {
        sendMessage = new SendMessage();
    }


    public boolean sendAction(String action, String params[]) {
        Log.d("send","sending message action: "+action);
        message = String.valueOf(System.currentTimeMillis()) + ";" + action;
        if(params != null) {
            message += ";";
            for(String param : params) {
                message += param + ":";
            }
            message = message.substring(0, message.length() - 2);
        }
        sendMessage.execute(message);
        return true;
    }}
