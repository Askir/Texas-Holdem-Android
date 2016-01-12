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
import de.szut.dqi12.texasholdem.guibackbone.GameList;

/**
 * Created by Marcel on 09.11.2015.
 */
public class JoinGameLobby extends Activity{

    GameListAdapter adapter;
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
                de.szut.dqi12.texasholdem.guibackbone.Game selectedGame = GameList.getInstance().getGames().get(position);
                if(selectedGame.password == true){
                    GameList.getInstance().selectedLobbyID = selectedGame.lobbyID;


                }
                else{
                    GameList.getInstance().joinGame(selectedGame.lobbyID,null);
                }

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
    public void joinGameSuccessfull(){

    }
    public void joinGameFailed(String params){

    }
}
