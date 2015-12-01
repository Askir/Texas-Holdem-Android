package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class JoinGameLobby extends Activity{

    Integer gamePositioner = 0;
    ArrayAdapter adapter;
    ArrayList<String> gamesList = new ArrayList<>();
    LinkedHashMap<String, Boolean> gamesListContext = new LinkedHashMap<>();
    ListView lvJoinGame;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_game_lobby);

        lvJoinGame = (ListView)findViewById(R.id.listViewJoinGame);

        // TODO: 17.11.2015 remove default listview fill
        fillGamesList("DefaultGame", false, "4");
        fillGamesList("DefaultGame2", true, "2");
        fillGamesList("DefaultGame3", true, "3");

        // TODO: 20.11.2015 change color of list children


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gamesList);
        lvJoinGame.setAdapter(adapter);


        lvJoinGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: 17.11.2015 send which lobby was selected

                String listPos = "" + position + "";

                // query whether selected lobby requires password and open corresponding activity
                if(gamesListContext.get(listPos).compareTo(true) == 0){
                    // password is required
                    // TODO: 17.11.2015 open activity that querys the password of joining game
                    Toast.makeText(JoinGameLobby.this, "Open Verification", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(JoinGameLobby.this, Game.class));

                }else{
                    //password is not required

                    /*
                     .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                     finish();
                     deletes the stack, so user cant go back after opening the game
                     */
                    // TODO: 01.12.2015 look for a better solution , better with no addflags() and finsih()
                    startActivity(new Intent(JoinGameLobby.this, Game.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }

            }
        });

    }

    public void fillGamesList(String Gamename, Boolean passwordSet, String numberOfPlayer){

        gamesList.add(gamePositioner, "Gamename: " + Gamename + ", Password required: " + passwordSet.toString() + ", Number of Player: " + numberOfPlayer);
        gamesListContext.put(gamePositioner.toString(), passwordSet);
        gamePositioner++;

        //adapter.notifyDataSetChanged();
    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }
}
