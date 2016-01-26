package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.R;

/**
 * Created by Jascha on 19.01.2016.
 */
public class ChangePassword extends Activity {

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        final EditText oldPassword = (EditText) findViewById(R.id.editTextSettingsOldPassword);
        final EditText newPassword = (EditText) findViewById(R.id.editTextSettingsNewPassword);
        Button confirm = (Button) findViewById(R.id.buttonChangePasswordConfirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.getInstance().getOptions().changePassword(oldPassword.getText().toString(),newPassword.getText().toString());
            }
        });
    }
}
