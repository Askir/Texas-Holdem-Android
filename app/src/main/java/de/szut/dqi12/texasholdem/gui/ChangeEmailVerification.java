package de.szut.dqi12.texasholdem.gui;

import android.widget.Toast;

/**
 * Created by Jascha on 12.01.2016.
 */
public class ChangeEmailVerification extends Verification {
    @Override
    public void inform(String status, String params) {
        if(status.equals("CORRECT")){
            Toast.makeText(this, "EmailChange confimed", Toast.LENGTH_SHORT);
        }
        else{
            Toast.makeText(this, "EmailChange declined", Toast.LENGTH_SHORT);
        }
    }
}
