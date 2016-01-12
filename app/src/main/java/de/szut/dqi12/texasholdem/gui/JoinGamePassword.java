package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.GameList;

/**
 * Created by Jascha on 12.01.2016.
 */
public class JoinGamePassword extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EditText password = (EditText) findViewById(R.id.editTextJoinGamePassword);
        Button ok = (Button) findViewById(R.id.buttonJoinGameOk);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameList.getInstance().joinGame(GameList.getInstance().selectedLobby.lobbyID, password.getText().toString());
            }
        });
    }


    public void joinGameSuccessfull(){
        Intent lobby = new Intent(JoinGamePassword.this, CreateGameLobby.class);
        startActivity(lobby);

    }
    public void joinGameFailed(String params){
        Toast.makeText(getBaseContext(), params, Toast.LENGTH_SHORT).show();
    }
}
