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

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.Options;

/**
 * Created by Marcel on 06.11.2015.
 */
public class Settings extends Activity {
    private Options options;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        options = Controller.getInstance().getOptions();
        final Switch swMusic = (Switch)findViewById(R.id.switchSettingsMusicOnOff);
        final EditText newEmail = (EditText) findViewById(R.id.editTextSettingsNewEmail);
        final EditText newUsername = (EditText) findViewById(R.id.editTextSettingsNewName);
        Button changeEmail = (Button) findViewById(R.id.buttonChangeEmail);
        Button changeUsername = (Button) findViewById(R.id.buttonChangeName);
        Button changePassword = (Button) findViewById(R.id.buttonChangePassword);

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.changeEmail(newEmail.getText().toString());
            }
        });

        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.changeUsername(newUsername.getText().toString());
            }
        });
        
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
