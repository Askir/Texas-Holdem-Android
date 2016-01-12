package de.szut.dqi12.texasholdem.guibackbone;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;

/**
 * Created by Jascha on 09.10.2015.
 */
public class Options {

    private static int volume = 0; //Database
    private static String email; // Serverside only
    private static String username; // Database and Serverside (update on programm start)7
    private static DatabaseConnection dbCon;

    static {
        dbCon = new DatabaseConnection();

        volume = dbCon.getVolume();
        username = dbCon.getUsername();

    }

    public static void onLogin(String email){
        Options.email = email;
    }

    public static void changePassword(String oldPassword, String newPassword){
        String[] params = {oldPassword,newPassword};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEPASSWORD, params );
        return;
    }

    public static boolean forgotPassword(){
        //TODO fill function
        return true;
    }

    public static void setVolume(int value){
        Options.volume=value;
    }

    public static void changeUsername(String username){
        Options.username = username;
        String[] params = {username};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEUSERNAME, params);
        return;
    }

    public static void changeEmail(String email){
        String[] params = {email};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEEMAILADDRESS,params);
        return;
    }

    public static void onClose(){
        dbCon.setVolume(volume);
        dbCon.setUsername(username);
    }

}
