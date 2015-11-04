package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        Button btnVerifi = (Button)findViewById(R.id.buttonRegisterOk);

        final EditText etUsername = (EditText)findViewById(R.id.editTextRegisterUsername);
        final EditText etPassword = (EditText)findViewById(R.id.editTextRegisterPassword);
        final EditText etEmail = (EditText)findViewById(R.id.editTextRegisterEmail);

        btnVerifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!etUsername.getText().equals(null)) && !etEmail.getText().equals(null) && !etPassword.getText().equals(null)) {

                    /*ToDo
                    - Send Username and Password to Server
                    */

                    Toast.makeText(getBaseContext(), "Need to send Data to Server", Toast.LENGTH_LONG);
                    startActivity(new Intent(Register.this, Verification.class));
                }
                else{
                    Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT);
                }

            }
        });


    }
}
