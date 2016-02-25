package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.Options;

/**
 * Created by Marcel on 06.11.2015.
 * /F0070/ Einstellungen
 */
public class Settings extends Activity {
    private Options options;
    private String TAG = "Settings activity";
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
        setContentView(R.layout.settings);
        final Switch swMusic = (Switch)findViewById(R.id.switchSettingsMusicOnOff);
        final EditText newEmail = (EditText) findViewById(R.id.editTextSettingsNewEmail);
        final EditText newUsername = (EditText) findViewById(R.id.editTextSettingsNewName);
        Button changeEmail = (Button) findViewById(R.id.buttonChangeEmail);
        Button changeUsername = (Button) findViewById(R.id.buttonChangeName);
        Button changePassword = (Button) findViewById(R.id.buttonChangePassword);

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.changeEmail(Settings.this,newEmail.getText().toString());
            }
        });

        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.changeUsername(Settings.this,newUsername.getText().toString());
            }
        });
        
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "change password started");
                Intent changePassword = new Intent(Settings.this, ChangePassword.class);

                startActivity(changePassword);
            }
        });


        swMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swMusic.isChecked()) {
                    Toast.makeText(getBaseContext(), "Music on.", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getBaseContext(), "Music off.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void emailChange(String correct){
        if(correct.equals("true")){
            Toast.makeText(this,"correct emailaddress please confirm",Toast.LENGTH_SHORT).show();
            Intent emailVerification = new Intent(Settings.this, ChangeEmailVerification.class);
            startActivity(emailVerification);
        }
        else{
            Toast.makeText(this,"something is wrong",Toast.LENGTH_SHORT).show();
        }
    }

    public void usernameChange(String param){
        if(param.equals("true")){
            Toast.makeText(getBaseContext(),"Successful username change to:" + Options.getUsername(),Toast.LENGTH_SHORT ).show();
        }
        else if(param.equals("false")){
            Toast.makeText(getBaseContext(), "Unsuccessful change", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getBaseContext(), "Server timeout", Toast.LENGTH_SHORT).show();
        }
    }
}

