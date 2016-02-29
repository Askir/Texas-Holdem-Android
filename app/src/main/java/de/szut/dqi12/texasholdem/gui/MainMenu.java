package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.MainActivity;
import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.guibackbone.*;
import de.szut.dqi12.texasholdem.guibackbone.Login;

/**
 * Created by Marcel on 04.11.2015.
 */
public class MainMenu extends Activity{

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            Controller.getInstance().setActiveActivity(this);
            setContentView(R.layout.main_menu);

            ImageButton btnStartGame = (ImageButton)findViewById(R.id.imageButtonMainMenueStartGame);
            ImageButton btnStatistics = (ImageButton)findViewById(R.id.imageButtonMainMenueStatistics);
            ImageButton btnSettings = (ImageButton)findViewById(R.id.imageButtonMainMenueSettings);
            ImageButton btnLogout = (ImageButton)findViewById(R.id.imageButtonMainMenueLogout);

            final Intent iUsername = getIntent();

            Toast.makeText(getBaseContext(), "Hello " + iUsername.getExtras().getString("username"), Toast.LENGTH_SHORT).show();

            btnStartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainMenu.this, StartGame.class));

                }
            });

            btnStatistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainMenu.this, Statistics.class));

                }
            });

            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainMenu.this, Settings.class).putExtra("Username", iUsername.getExtras().getString("username")));

                }
            });

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainMenu.this, MainActivity.class));
                    Toast.makeText(getBaseContext(), "Logging out.", Toast.LENGTH_SHORT).show();
                    Controller.getInstance().getSend().sendAction(ClientAction.LOGOUT,null);
                    finish();

                }
            });

        }
    @Override
    public void onBackPressed() {
    }
}