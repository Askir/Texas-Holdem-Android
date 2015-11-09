package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class CreateGameLobby extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game_lobby);

        Button btnEnough = (Button)findViewById(R.id.buttonGameLobbyEnough);

        btnEnough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
