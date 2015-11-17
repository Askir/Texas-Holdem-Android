package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 06.11.2015.
 */
public class Settings extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final EditText etNewUsername = (EditText)findViewById(R.id.editTextSettingsCUsername);
        final EditText etNewPassword = (EditText)findViewById(R.id.editTextSettingsCPassword);
        final EditText etNewEmail = (EditText)findViewById(R.id.editTextSettingsCEmail);
        final Switch swMusic = (Switch)findViewById(R.id.switchSettingsMusicOnOff);
        Button btnOk = (Button)findViewById(R.id.buttonSettingsOk);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etNewUsername.getText().toString().equals("")){
                    // TODO: 07.11.2015 get username from database and edit it
                    Toast.makeText(getBaseContext(), "Username changed", Toast.LENGTH_SHORT).show();

                }

                // TODO: 07.11.2015 open verification with information about what must be changed
                if(!etNewPassword.getText().toString().equals("") && etNewEmail.getText().toString().equals("")){

                    Toast.makeText(getBaseContext(), "Open Verification to change password.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Settings.this, Verification.class));

                    //email
                }else if (etNewPassword.getText().toString().equals("") && !etNewEmail.getText().toString().equals("")){

                    Toast.makeText(getBaseContext(), "Open Verification to change email.", Toast.LENGTH_SHORT).show();


                    //both
                }else if(!etNewPassword.getText().toString().equals("") && !etNewEmail.getText().toString().equals("")){

                    Toast.makeText(getBaseContext(), "Open Verification to change passwordand email.", Toast.LENGTH_SHORT).show();

                }

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
}
