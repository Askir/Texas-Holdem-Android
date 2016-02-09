package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha Beste on 11.11.2015.
 */
public class Register implements Recallable {

    //TODO: add to inform() what happens if you don't get any server response (timeout)
    //Handler to call functions in the register activity on the UI thread
    private Handler mHandler;
    //reference to the register activity to callback the function through the Handler thread after the Registration has been completed
    private de.szut.dqi12.texasholdem.gui.Register registerActivity;
    private long timeout = 5000;
    private long timestamp = 0;

    public Register(de.szut.dqi12.texasholdem.gui.Register registerActivity){
        //linking the Handler with the UI Thread
        mHandler = new Handler(Looper.getMainLooper());
        this.registerActivity = registerActivity;
    }
    /*
    username = username
    password = password
    repassword = retyped password
    email = email Address
    return value is true if the request has been executed; false if the passwords do not match

     */
    public boolean executeRegister(String username, String password, String repassword, String email){

        if(password.equals(repassword)){
            String[] registerContent = {username,password,email};
            Controller.getInstance().getSend().sendAction(ClientAction.REGISTER, registerContent);
            Controller.getInstance().getDecryption().addExpectation(this);
            timestamp = System.currentTimeMillis();

            return true;
        }
        else{
            return false;
        }
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
        if (action.equals(ServerAction.REGISTERACK)) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    registerActivity.inform(params[0]);
                }
            });
        }
    }

    @Override
    public String Action() {
        return ServerAction.REGISTERACK;
    }

    @Override
    public String Params() {
        return null;
    }
}
