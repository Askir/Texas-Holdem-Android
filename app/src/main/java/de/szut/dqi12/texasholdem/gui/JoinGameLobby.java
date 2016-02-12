package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.GameList;

/**
 * Created by Marcel on 09.11.2015.
 */
public class JoinGameLobby extends Activity{

    GameListAdapter adapter;
    ListView lvJoinGame;
    private String TAG = "Join Game Lobby";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
        GameList.getInstance().registerJGL(this);
        GameList.getInstance().retrieveGameList();
        setContentView(R.layout.join_game_lobby);

        lvJoinGame = (ListView)findViewById(R.id.listViewJoinGame);


        adapter = new GameListAdapter(this);
        lvJoinGame.setAdapter(adapter);


        lvJoinGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Log.d(TAG,"Position selected:"+position);
                de.szut.dqi12.texasholdem.guibackbone.Game selectedGame = GameList.getInstance().getGames().get(position);
                GameList.getInstance().selectedLobby = selectedGame;
                if(!selectedGame.password){
                GameList.getInstance().joinGame(selectedGame.lobbyID, null);}
                else{
                    Intent passwordQuery = new Intent(JoinGameLobby.this,JoinGamePassword.class).putExtra("lobbyID",selectedGame.lobbyID);
                    startActivity(passwordQuery);
                }

            }
        });

    }
    public void joinGameSuccessfull(){
        Intent lobby = new Intent(JoinGameLobby.this, CreateGameLobby.class);
        startActivity(lobby);

    }
    public void joinGameFailed(String params){
        Toast.makeText(getBaseContext(),params,Toast.LENGTH_SHORT).show();
    }
    public void reloadListView(){
        adapter.notifyDataSetChanged();
    }
}
