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

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO: 09.11.2015 send lobbyname to server, so for game searching user can see lobbyname


                // TODO: 09.11.2015 if pw set status, send to server
                Toast.makeText(getBaseContext(), "Need to send lobbyname and pw status.", Toast.LENGTH_SHORT).show();

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