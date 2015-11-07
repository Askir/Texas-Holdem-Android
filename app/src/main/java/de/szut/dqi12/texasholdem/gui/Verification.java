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
import de.szut.dqi12.texasholdem.connection.UserData;

/**
 * Created by Marcel on 03.11.2015.
 */
public class Verification extends Activity{

    // TODO: 07.11.2015 implement fragment layout
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);

//        switch (UserData.wantToChangeSomething){
//            case():
//                openFragmentWhatever();
//                break;
//
//        }


        Button btnOk = (Button)findViewById(R.id.buttonRegisterVerificationOk);

        final EditText etVeri = (EditText)findViewById(R.id.editTextRegisterVerificationCode);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etVeri.getText().equals(null)) {

                    Boolean codeCorrect = true;

                    /*ToDo
                    - Send Code to Server and check whether ok
                    */

                    if (codeCorrect) {

                        Intent restart = new Intent(Verification.this, MainActivity.class);

                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(restart);
                        finish();

                    } else {
                        Toast.makeText(getBaseContext(), "Wrong Code", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please insert code for verification", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
