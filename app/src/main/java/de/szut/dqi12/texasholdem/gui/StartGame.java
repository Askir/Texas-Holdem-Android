package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 06.11.2015.
 */
public class StartGame extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);

        Button btnJoin = (Button)findViewById(R.id.buttonStartGameJoin);
        Button btnCreate = (Button)findViewById(R.id.buttonStartGameCreate);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartGame.this, JoinGameLobby.class));
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartGame.this, CreateGame.class));
            }
        });

    }
}
