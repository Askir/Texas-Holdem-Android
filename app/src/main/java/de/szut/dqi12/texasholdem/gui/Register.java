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
public class Register extends Activity {

    private de.szut.dqi12.texasholdem.guibackbone.Register register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register = new de.szut.dqi12.texasholdem.guibackbone.Register(this);
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
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

                    Toast.makeText(getBaseContext(), "Sending register attempt", Toast.LENGTH_SHORT).show();
                    register.executeRegister(etUsername.getText().toString(),etPassword.getText().toString(),etReEnterPassword.getText().toString(),etEmail.getText().toString());


                }
                else{
                    Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void inform(String result){
        if(result.equals("accepted")){
            startActivity(new Intent(Register.this, RegisterVerification.class));
            Toast.makeText(getBaseContext(),"successful register attempt please verify your email", Toast.LENGTH_LONG).show();
        }
        else if(result.equals("denied")){
            Toast.makeText(getBaseContext(),"Something is wrong with your data",Toast.LENGTH_SHORT).show();
        }
        else if(result.equals("timeout")){
            Toast.makeText(getBaseContext(),"Server timeout",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getBaseContext(),"serverside error" + result ,Toast.LENGTH_SHORT).show();
        }

    }
}
