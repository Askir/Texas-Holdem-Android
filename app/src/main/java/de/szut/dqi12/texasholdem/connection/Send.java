package de.szut.dqi12.texasholdem.connection;

import java.sql.Timestamp;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Send {

    public String message;

    public boolean sendAction(GameAction action, String params[]){
        message = String.valueOf(System.currentTimeMillis()) + ";" + action.name();
        if(params == null) {
            return true;
        } else {
            message += ";";
            for(String param : params) {
                message += param + ":";
            }
            message = message.substring(0, message.length() - 2);
            return true;
        }
    }

    public boolean sendAction(ClientAction action, String params[]) {
        message = String.valueOf(System.currentTimeMillis()) + ";" + action.name();
        if(params == null) {
            return true;
        } else {
            message += ";";
            for(String param : params) {
                message += param + ":";
            }
            message = message.substring(0, message.length() - 2);
            return true;
        }
    }

    public boolean sendAction(ServerAction action, String params[]) {
        message = String.valueOf(System.currentTimeMillis()) + ";" + action.name();
        if(params == null) {
            return true;
        } else {
            message += ";";
            for(String param : params) {
                message += param + ":";
            }
            message = message.substring(0, message.length() - 2);
            return true;
        }
    }




}
