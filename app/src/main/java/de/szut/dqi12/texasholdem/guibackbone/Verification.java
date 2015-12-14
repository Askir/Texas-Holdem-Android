package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha Beste on 14.12.2015.
 */
public class Verification implements Recallable{
    private de.szut.dqi12.texasholdem.gui.Verification verificationActivity;
    private Handler mHandler;

    public Verification(de.szut.dqi12.texasholdem.gui.Verification verificationActivity){
        this.verificationActivity = verificationActivity;
        //linking the Handler with the UI Thread
        mHandler = new Handler(Looper.getMainLooper());

    }
    public void sendVerification(String verificationCode){
        String[] params = {verificationCode};
        //sending the Validation information to the server
        Controller.getInstance().getSend().sendAction(ClientAction.VALIDATION,params);
        //adding this object to the callback loop
        Controller.getInstance().getDecryption().addExpectation(this);
    }
    @Override
    public void inform(String action, String[] params) {
        if(action.equals(ServerAction.VALIDATION)){
            //TODO: communicate with Marcel about the inform function and add corrected code
            if (params[0].equals("correct")){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //verificationActivity.inform(params[0],params[1]);
                    }
                });
            }
            if (params[0].equals("wrong")){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //verificationActivity.inform(params[0],params[1]);
                    }
                });
            }
            else{
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //verificationActivity.inform(params[0],params[1]);
                    }
                });

            }
        }
    }

    @Override
    public String Action() {
        return ServerAction.VALIDATION;
    }

    @Override
    public String[] Params() {
        return null;
    }
}
