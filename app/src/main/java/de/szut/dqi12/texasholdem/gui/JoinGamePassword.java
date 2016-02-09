package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.GameList;

/**
 * Created by Jascha Beste on 12.01.2016.
 */
public class JoinGamePassword extends Activity {



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
        setContentView(R.layout.join_game_password);

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

    }
    public void joinGameFailed(String params){

    }

}
