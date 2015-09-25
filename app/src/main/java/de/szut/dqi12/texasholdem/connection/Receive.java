package de.szut.dqi12.texasholdem.connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Receive {



    public Receive(){

    }

    public String getMessage(){
        BufferedReader reader = Connection.getInstance().getReader();
        InputStreamReader input = Connection.getInstance().getInput();
        String message = null;
        try {
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;

    }
}
