package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class CreateGame extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);

        Button btnOk = (Button)findViewById(R.id.buttonCreateGameOK);
        final EditText etLobbyname = (EditText)findViewById(R.id.editTextCreateGameLobbyname);
        final EditText etPassword = (EditText)findViewById(R.id.editTextCreateGamePassword);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startLobby = new Intent(CreateGame.this, CreateGameLobby.class);

                // TODO: 09.11.2015 send lobbyname to server, so game searching user can see lobbyname

                // TODO: 09.11.2015 if pw set status, send to server
                if(etPassword.getText().toString().length() > 1){
                     //true
                }else{
                    //false
                }

            }
        });

    }
}