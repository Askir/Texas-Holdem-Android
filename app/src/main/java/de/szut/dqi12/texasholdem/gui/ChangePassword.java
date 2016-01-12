package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.Options;

/**
 * Created by Jascha Beste on 12.01.2016.
 */
public class ChangePassword extends Activity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button confirmButton = (Button) findViewById(R.id.buttonChangePasswordConfirm);
        final EditText oldPassword = (EditText) findViewById(R.id.editTextSettingsOldPassword);
        final EditText newPassword = (EditText) findViewById(R.id.editTextSettingsNewPassword);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Options.changePassword(oldPassword.getText().toString(),newPassword.getText().toString());
            }
        });

    }

}
