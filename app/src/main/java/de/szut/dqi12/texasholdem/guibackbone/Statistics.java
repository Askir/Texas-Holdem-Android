package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;
import de.szut.dqi12.texasholdem.gui.StartGame;

/**
 * Created by Jascha on 11.12.2015.
 */
public class Statistics implements Recallable{

    //TODO: update inform function with timeout content and revisit class to update this comment
    private de.szut.dqi12.texasholdem.gui.Statistics statsActivity;
    private String[] statistics = new String[5];
    private long timeout = 5000;
    private long timestamp = 0;
    private Handler mHandler;

    public Statistics(de.szut.dqi12.texasholdem.gui.Statistics statsActivity){
        mHandler = new Handler(Looper.getMainLooper());
        this.statsActivity = statsActivity;
    }

    public void updateStatistics(){
        Controller.getInstance().getSend().sendAction(ClientAction.STATS, null);
        timestamp = System.currentTimeMillis();
        Controller.getInstance().getDecryption().addExpectation(this);

    }

    public String[] getStatistics(){
        return statistics;
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
    public void inform(String action, String[] params) {
        for(int i = 0; i < params.length; i++){
            statistics[i] = params[i];
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    statsActivity.writeStats();
                }
            });
        }
        return;
    }

    @Override
    public String Action() {
        return ServerAction.STATS;
    }

    @Override
    public String Params() {
        return null;
    }
}
