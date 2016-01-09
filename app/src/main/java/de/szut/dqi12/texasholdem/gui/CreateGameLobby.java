package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.Lobby;

/**
 * Created by Marcel on 09.11.2015.
 */
public class CreateGameLobby extends Activity {

    private PlayerListAdaptertemp plA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game_lobby);
        Lobby.getInstance().registerActivity(this);

        ListView playerView = (ListView) findViewById(R.id.listViewGameLobbyPlayer);
        plA = new PlayerListAdaptertemp(this);
        playerView.setAdapter(plA);
        final Button btnReady = (Button) findViewById(R.id.buttonGameLobbyReady);

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnReady.getText()=="not Ready"){
                    Lobby.getInstance().setState(false);
                    btnReady.setText("Ready");
                }
                else if(btnReady.getText()=="Ready"){
                    Lobby.getInstance().setState(true);
                    btnReady.setText("not Ready");
                }

            }
        });
    }


    public void gameStart() {
        startActivity(new Intent(CreateGameLobby.this, Game.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
    public void playerChanged(){
        plA.notifyDataSetChanged();
    }
}