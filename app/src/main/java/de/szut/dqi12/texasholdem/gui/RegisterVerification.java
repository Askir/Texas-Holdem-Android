package de.szut.dqi12.texasholdem.gui;

/**
 * Created by Jascha Beste on 05.01.2016.
 */
import android.widget.Toast;
import android.content.Intent;
import de.szut.dqi12.texasholdem.MainActivity;

public class RegisterVerification extends Verification {
    @Override
    public void inform(String status, String params) {
        Toast.makeText(getBaseContext(), params,Toast.LENGTH_LONG);
        if(status.equals("correct")){
            Intent restart = new Intent(RegisterVerification.this, MainActivity.class);

            restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(restart);
            finish();
        }
        else if(status.equals("wrong")){

        }
        return;
    }
}
