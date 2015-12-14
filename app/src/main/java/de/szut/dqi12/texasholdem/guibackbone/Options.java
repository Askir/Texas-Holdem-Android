package de.szut.dqi12.texasholdem.guibackbone;

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

    public boolean changePassword(String oldPassword, String newPassword){
        //TODO fill function
        return true;
    }

    public boolean forgotPassword(){
        //TODO fill function
        return true;
    }

    public void setVolume(int value){
        this.volume=value;
    }

    public boolean changeUsername(String username){
        this.username=username;
        //TODO fill function
        return true;
    }

    public boolean changeEmail(String email){
        //TODO fill function
        return true;
    }

    public void onClose(){
        dbCon.setVolume(volume);
        dbCon.setUsername(username);
    }

}
