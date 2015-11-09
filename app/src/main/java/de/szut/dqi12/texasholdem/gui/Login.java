package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.connection.UserData;

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

                /*ToDo
                - Send Username and Password to Server
                 */

                if(etUsername.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0){

                    Intent login = new Intent(Login.this, MainMenu.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    Toast.makeText(getBaseContext(), "Need to send Data to Server", Toast.LENGTH_LONG).show();

                    /*
                         TODO: 07.11.2015 check login data whether email or username are correct
                         ToDo: get username for welcome massage
                     */

                    // key then value
                    //login.putExtra("username", "name");
                    //login.putExtra("email", "address");
                    //login.putExtra("password", "password");

                    startActivity(login);
                    finish();
                }else{

                    Toast.makeText(getBaseContext(), "Please type your Username and Password.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
