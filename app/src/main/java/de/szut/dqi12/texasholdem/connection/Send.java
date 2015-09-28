package de.szut.dqi12.texasholdem.connection;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.GameAction;
import de.szut.dqi12.texasholdem.action.ServerAction;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Send {

    private String message;
    private Controller controller;


    public Send() {
        controller = Controller.getInstance();
    }

    public boolean sendAction(String action, String params[]) {
        message = String.valueOf(System.currentTimeMillis()) + ";" + action;
        if(params != null) {
            message += ";";
            for(String param : params) {
                message += param + ":";
            }
            message = message.substring(0, message.length() - 2);
        }
        controller.getConnection().getWriter().println(message);
        return true;
    }}
