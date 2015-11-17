package de.szut.dqi12.texasholdem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.szut.dqi12.texasholdem.gui.Login;
import de.szut.dqi12.texasholdem.gui.Register;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        Button btnLogin = (Button) findViewById(R.id.buttonStartupLogin);
        Button btnRegister = (Button) findViewById(R.id.buttonStartupRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }

        });
    }
}
