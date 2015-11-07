package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 03.11.2015.
 */
public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button btnOk = (Button)findViewById(R.id.buttonLoginOk);

        final EditText etUsername = (EditText)findViewById(R.id.editTextLoginUsername);
        final EditText etPassword = (EditText)findViewById(R.id.editTextLoginPassword);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getBaseContext(), "Button clicked", Toast.LENGTH_SHORT).show();

                /*ToDo
                - Send Username and Password to Server
                 */

                if(etUsername.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0){

                    Toast.makeText(getBaseContext(), "Need to send Data to Server", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this, MainMenu.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }else{

                    Toast.makeText(getBaseContext(), "Please type your Username and Password.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
