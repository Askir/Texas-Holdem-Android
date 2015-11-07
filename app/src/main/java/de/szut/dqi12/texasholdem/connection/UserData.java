package de.szut.dqi12.texasholdem.connection;

/**
 * Created by Marcel on 07.11.2015.
 */
public class UserData {

    // TODO: 07.11.2015 fill data while logging in -> dataanswer shall be from server 
    
    public String username = "";
    public String email = "";
    public String password = "";

    public String wantToChangeSomething = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWantToChangeSomething() {
        return email;
    }

    public void setWantToChangeSomething(String wantToChangeSomething) {
        this.wantToChangeSomething = wantToChangeSomething;
    }

}
