package de.szut.dqi12.texasholdem.gui;

/**
 * Created by Jascha Beste on 05.01.2016.
 * /F0010/ Registrierung
 */
import android.widget.Toast;
import android.content.Intent;
import de.szut.dqi12.texasholdem.MainActivity;

public class RegisterVerification extends Verification {
    @Override
    public void inform(String[] params) {
        Toast.makeText(this, params[0],Toast.LENGTH_LONG);
        if(params[0].equals("true")){
            Toast.makeText(getBaseContext(),"correct code, registration verified",Toast.LENGTH_SHORT);
            Intent restart = new Intent(RegisterVerification.this, MainActivity.class);

            restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(restart);
            finish();
        }
        else if(params[0].equals("false")){
            Toast.makeText(getBaseContext(),"wrong verification code",Toast.LENGTH_SHORT);
        }
        return;
    }
}
