package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class JoinGameLobby extends Activity{

    Integer gamePositioner = 0;
    ArrayAdapter adapter;
    ArrayList<String> gamesList = new ArrayList<String>();
    HashMap<String, Boolean> gamesListContext=new HashMap<>();
    ListView lvJoinGame;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_game_lobby);

        // TODO: 17.11.2015 remove default listview fill
        fillGamesList("DefaultLobby", false, "4");

        lvJoinGame = (ListView)findViewById(R.id.listViewJoinGame);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gamesList);
        lvJoinGame.setAdapter(adapter);


        lvJoinGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: 17.11.2015 send which lobby was selected


                // TODO: 09.11.2015 must implement password query

                if(gamesListContext.get(gamePositioner -1).toString().equals("true")){
                    // TODO: 17.11.2015 open activity that querys the password of joining game

                    // not right!!
                    startActivity(new Intent(JoinGameLobby.this, Game.class));
                }else{
                    startActivity(new Intent(JoinGameLobby.this, Game.class));
                }

            }
        });

    }

    public void fillGamesList(String Lobbyname, Boolean passwordSet, String numberOfPlayer){

        gamesList.add(gamePositioner, "Lobbyname: " + Lobbyname + ", Password required: " + passwordSet.toString() + ", Number of Player" + numberOfPlayer);
        gamesListContext.put(gamePositioner.toString(), passwordSet);
        gamePositioner++;

        lvJoinGame.refreshDrawableState();
    }
}
