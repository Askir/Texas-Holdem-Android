package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.Options;

/**
 * Created by Jascha Beste on 12.01.2016.
 */
public class ChangePassword extends Activity {
    private Button confirmButton;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
        setContentView(R.layout.change_password);

        confirmButton = (Button) findViewById(R.id.buttonChangePasswordConfirm);
        final EditText oldPassword = (EditText) findViewById(R.id.editTextSettingsOldPassword);
        final EditText newPassword = (EditText) findViewById(R.id.editTextSettingsNewPassword);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton.setClickable(false);
                Options.changePassword(oldPassword.getText().toString(),newPassword.getText().toString());
            }
        });

    }

    public void passwordChange(boolean valid){
        if(valid){
            Intent verificication = new Intent(ChangePassword.this,ChangePasswordVerification.class);
            startActivity(verificication);
        }
        else{
            Toast.makeText(getBaseContext(),"unsuccesful Password Change", Toast.LENGTH_SHORT);
        }
        confirmButton.setClickable(true);
    }

}
