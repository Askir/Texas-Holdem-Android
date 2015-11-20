package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class CreateGameLobby extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game_lobby);

        final Boolean enoughPlayer = true;

        Button btnEnough = (Button)findViewById(R.id.buttonGameLobbyEnough);

        btnEnough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 09.11.2015 check listViewGameLobbyPlayer whether player are available. when no player there send toast
                if(enoughPlayer){
                    startActivity(new Intent(CreateGameLobby.this, Game.class));
                }else{
                    Toast.makeText(getBaseContext(),"Wait for more Player", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
