package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.MainActivity;
import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 03.11.2015.
 */
public abstract class Verification extends Activity{

    private de.szut.dqi12.texasholdem.guibackbone.Verification veri;
    // TODO: 07.11.2015 implement fragment layout
    // TODO: add other Verification types for password reset etc.
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);
        veri = new de.szut.dqi12.texasholdem.guibackbone.Verification(this);



        Button btnOk = (Button)findViewById(R.id.buttonRegisterVerificationOk);

        final EditText etVeri = (EditText)findViewById(R.id.editTextRegisterVerificationCode);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etVeri.getText().equals(null)) {

                    Boolean codeCorrect = true;
                    veri.sendVerification(etVeri.getText().toString());


                } else {
                    Toast.makeText(getBaseContext(), "Please insert code for verification", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void servertimeout(){
        Toast.makeText(getBaseContext(),"Server did not response please check your internet connection", Toast.LENGTH_LONG).show();
    }
    public abstract void inform(String status, String params);

    /** public void inform(String status, String params){
        Toast.makeText(getBaseContext(), params,Toast.LENGTH_LONG);
        if(status.equals("correct")){
            Intent restart = new Intent(Verification.this, MainActivity.class);

            restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(restart);
            finish();
        }
        else if(status.equals("wrong")){

        }
        return;
    } **/
}
