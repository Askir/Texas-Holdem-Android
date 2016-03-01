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
 * Created by Marcel on 09.11.2015.
 */
public class CreateGame extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
        setContentView(R.layout.create_game);

        Button btnOk = (Button)findViewById(R.id.buttonCreateGameOK);
        final EditText etLobbyname = (EditText)findViewById(R.id.editTextCreateGameLobbyname);
        final EditText etPassword = (EditText)findViewById(R.id.editTextCreateGamePassword);
        final EditText etSlots = (EditText)findViewById(R.id.editTextCreateGameSlots);
        final de.szut.dqi12.texasholdem.guibackbone.CreateGame cG = new de.szut.dqi12.texasholdem.guibackbone.CreateGame(this);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(2<Integer.parseInt(etSlots.getText().toString()) && Integer.parseInt(etSlots.getText().toString())<7){

                Toast.makeText(getBaseContext(), "Requesting GameCreation...", Toast.LENGTH_SHORT).show();
                cG.requestGameCreation(etLobbyname.getText().toString(),Integer.parseInt(etSlots.getText().toString()),etPassword.getText().toString());
                }
                else{
                    Toast.makeText(getBaseContext(),"Slots must be between 2 and 6",Toast.LENGTH_SHORT);
                }

            }
        });

    }

    public void gameCreationSuccessful(){
        startActivity(new Intent(CreateGame.this, CreateGameLobby.class));
    }

    public void gameCreationFailed(String error){
        Toast.makeText(getBaseContext(),error,Toast.LENGTH_SHORT).show();
    }

}