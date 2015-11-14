package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.MainActivity;
import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 04.11.2015.
 */
public class MainMenu extends Activity{

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_menu);

            ImageButton btnStartGame = (ImageButton)findViewById(R.id.imageButtonMainMenueStartGame);
            ImageButton btnStatistics = (ImageButton)findViewById(R.id.imageButtonMainMenueStatistics);
            ImageButton btnSettings = (ImageButton)findViewById(R.id.imageButtonMainMenueSettings);
            ImageButton btnLogout = (ImageButton)findViewById(R.id.imageButtonMainMenueLogout);

//            String user = getIntent().getStringExtra("username");
//            String email = getIntent().getStringExtra("email");
//            String password = getIntent().getStringExtra("password");

            Toast.makeText(getBaseContext(), "Hello 'Username'", Toast.LENGTH_SHORT).show();

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

                    startActivity(new Intent(MainMenu.this, Settings.class));

                }
            });

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent restart = new Intent(MainMenu.this, MainActivity.class);

                    restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(restart);
                    Toast.makeText(getBaseContext(), "Logging out.", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

        }
}