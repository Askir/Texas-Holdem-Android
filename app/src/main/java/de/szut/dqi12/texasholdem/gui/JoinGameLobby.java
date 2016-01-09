package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class JoinGameLobby extends Activity{

    Integer gamePositioner = 0;
    GameListAdapter adapter;
    ArrayList<String> gamesList = new ArrayList<>();
    LinkedHashMap<String, Boolean> gamesListContext = new LinkedHashMap<>();
    ListView lvJoinGame;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_game_lobby);

        lvJoinGame = (ListView)findViewById(R.id.listViewJoinGame);

        adapter = new GameListAdapter(this);
        lvJoinGame.setAdapter(adapter);


        lvJoinGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: 17.11.2015 send which lobby was selected

                String listPos = "" + position + "";

                // query whether selected lobby requires password and open corresponding activity
                if (gamesListContext.get(listPos).compareTo(true) == 0) {
                    // password is required
                    // TODO: 17.11.2015 open activity that querys the password of joining game
                    Toast.makeText(JoinGameLobby.this, "Open Verification", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(JoinGameLobby.this, Game.class));

                } else {
                    startActivity(new Intent(JoinGameLobby.this, Game.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }

            }
        });

    }
}
