package de.szut.dqi12.texasholdem.gui;

import android.widget.Toast;

import de.szut.dqi12.texasholdem.Controller;

/**
 * Created by Jascha Beste on 12.01.2016.
 */
public class ChangePasswordVerification extends Verification{
    @Override
    public void inform(String status, String params) {
        if(status.equals("CORRECT")){
            Toast.makeText(this,"correct verification",Toast.LENGTH_SHORT);
            return;
        }
        else{
            return;
        }
    }
}
