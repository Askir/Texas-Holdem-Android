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

/**
 * Created by Marcel on 03.11.2015.
 */
public class Login extends Activity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnOk;

    private de.szut.dqi12.texasholdem.guibackbone.Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //starting the controller we need to rewrite this later on
        Controller con = Controller.getInstance();
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
        setContentView(R.layout.login);

        btnOk = (Button)findViewById(R.id.buttonLoginOk);

        etUsername = (EditText)findViewById(R.id.editTextLoginUsername);
        etPassword = (EditText)findViewById(R.id.editTextLoginPassword);
        login = new de.szut.dqi12.texasholdem.guibackbone.Login(this);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnOk.setClickable(false);

                if(etUsername.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0){
                    Toast.makeText(getBaseContext(), "Looging in...", Toast.LENGTH_SHORT).show();
                    login.sendLoginRequest(etUsername.getText().toString(), etPassword.getText().toString());
                }
                else{

                    Toast.makeText(getBaseContext(), "Please type your Username and Password.", Toast.LENGTH_LONG).show();
                    btnOk.setClickable(true);
                }
            }
        });
    }

    public void loginresult(int i){
        switch(i){
            case 0: //userdata correct
                Intent login = new Intent(Login.this, MainMenu.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtra("username", etUsername.getText().toString());
                startActivity(login);
                finish();
                break;
            case 1: //userdata incorrect
                Toast.makeText(Login.this, "Userdata incorrect.", Toast.LENGTH_SHORT).show();
                btnOk.setClickable(true);
                break;
            case 2: //timeout
                Toast.makeText(Login.this, "Login Timeout", Toast.LENGTH_SHORT).show();
                btnOk.setClickable(true);
                break;
        }
    }

}
