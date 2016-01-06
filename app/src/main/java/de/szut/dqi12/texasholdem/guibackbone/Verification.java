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

    //TODO: add timeout inform content
    private de.szut.dqi12.texasholdem.gui.Verification verificationActivity;
    private Handler mHandler;
    private long timeout =  5000;
    private long timestamp = 0;

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
        //updating the timestamp first
        timestamp = System.currentTimeMillis();
        Controller.getInstance().getDecryption().addExpectation(this);
    }

    @Override
    public long getMaxWaitTIme() {
        return timeout;
    }

    @Override
    public long getTimeStamp() {
        return timestamp;
    }

    @Override
    public void inform(String action, final String[] params) {
        if(action.equals(ServerAction.VALIDATION)){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                verificationActivity.inform(params[0],params[1]);
            }
        });}
        else if(action.equals(ServerAction.NORESPONSE)){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    verificationActivity.servertimeout();
                }
            });}
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
