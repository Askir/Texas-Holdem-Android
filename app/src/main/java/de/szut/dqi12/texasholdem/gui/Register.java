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

    private de.szut.dqi12.texasholdem.guibackbone.Register register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register = new de.szut.dqi12.texasholdem.guibackbone.Register(this);
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
                    register.executeRegister(etUsername.getText().toString(),etPassword.getText().toString(),etReEnterPassword.getText().toString(),etEmail.getText().toString());


                }
                else{
                    Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void inform(String result, String message){
        if(result.equals("accpeted")){
            startActivity(new Intent(Register.this, Verification.class));
            Toast.makeText(getBaseContext(),"successful register attempt please verify your email", Toast.LENGTH_LONG).show();
        }
        else if(result.equals("denied")){
            Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT);
        }
        else{
            Toast.makeText(getBaseContext(),"serverside error: " + message,Toast.LENGTH_SHORT);
        }

    }
}
