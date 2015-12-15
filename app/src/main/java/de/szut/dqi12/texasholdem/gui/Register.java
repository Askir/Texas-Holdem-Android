package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 03.11.2015.
 */
public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button btnOk = (Button)findViewById(R.id.buttonRegisterOk);

        final EditText etUsername = (EditText)findViewById(R.id.editTextRegisterUsername);
        final EditText etPassword = (EditText)findViewById(R.id.editTextRegisterPassword);
        final EditText etReEnterPassword = (EditText)findViewById(R.id.editTextRegisterReEnterPassword);
        final EditText etEmail = (EditText)findViewById(R.id.editTextRegisterEmail);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etUsername.getText().toString().equals("") && !etEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("") && etReEnterPassword.getText().toString().equals(etPassword.getText().toString())) {

                    Toast.makeText(getBaseContext(), "Need to send Data to Server", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, Verification.class));

                    // ToDo Send Username and Password to Server

                }
                else{
                    Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // TODO: 11.12.2015 write function for register response from server, server sends boolean success and string with errormassage

    }
}
