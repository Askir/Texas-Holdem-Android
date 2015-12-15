package de.szut.dqi12.texasholdem.menu.options;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;

/**
 * Created by Jascha on 09.10.2015.
 */
public class Options {

    private int volume = 0; //Database
    private String email; // Serverside only
    private String username; // Database and Serverside (update on programm start)7
    private DatabaseConnection dbCon;

    public Options (){
        dbCon = new DatabaseConnection();

        volume = dbCon.getVolume();
        username = dbCon.getUsername();

    }

    public void onLogin(String email){
        this.email = email;
    }

    public void changePassword(String oldPassword, String newPassword){
        String[] params = {oldPassword,newPassword};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEPASSWORD, params );
        return;
    }
    /**
    public boolean forgotPassword(){
        //TODO fill function
        return true;
    }
    **/
    public void setVolume(int value){
        this.volume=value;
    }

    public void changeUsername(String username){
        this.username=username;
        String[] params = {username};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEUSERNAME, params);
        return;
    }

    public void changeEmail(String email){
        String[] params = {email};
        Controller.getInstance().getSend().sendAction(ClientAction.CHANGEEMAILADDRESS,params);
        return;
    }

    public void onClose(){
        dbCon.setVolume(volume);
        dbCon.setUsername(username);
    }

}
