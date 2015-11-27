package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.connection.session.LoginProcess;
import de.szut.dqi12.texasholdem.connection.LoginResult;

/**
 * Created by Marcel on 03.11.2015.
 */
public class Login extends Activity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnOk;

    private LoginProcess lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnOk = (Button)findViewById(R.id.buttonLoginOk);

        etUsername = (EditText)findViewById(R.id.editTextLoginUsername);
        etPassword = (EditText)findViewById(R.id.editTextLoginPassword);

        lp = new LoginProcess(this);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(etUsername.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0){
                    String[] userdata = {etUsername.getText().toString(), etPassword.getText().toString()};
                    Toast.makeText(getBaseContext(), "Looging in...", Toast.LENGTH_SHORT).show();
                    // TODO: 27.11.2015 still no real login, implement login
                    lp.execute(userdata);

                }else{

                    Toast.makeText(getBaseContext(), "Please type your Username and Password.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void loginstatus(LoginResult lr){
        switch(lr){
            case USERDATACORRECT:
                // falgs without finish unnecessary
                Intent login = new Intent(Login.this, MainMenu.class).putExtra("username", etUsername.getText().toString());
                startActivity(login);
                // finish not good
                // finish();
                break;
            case USERDATAINCORRECT:
                Toast.makeText(Login.this, "Userdata incorrect.", Toast.LENGTH_SHORT).show();
                etUsername.setText("");
                break;
            case TIMEOUT:
                Toast.makeText(Login.this, "Login Timeout.", Toast.LENGTH_SHORT).show();
                etUsername.setText("");
                break;
        }
    }

}
