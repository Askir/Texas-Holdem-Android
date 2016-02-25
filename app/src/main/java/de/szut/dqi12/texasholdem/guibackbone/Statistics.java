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
 * /F0060/ Statistik
 */
public class Statistics implements Recallable {

    private de.szut.dqi12.texasholdem.gui.Statistics statsActivity; //The currently active statistics activity
    private String[] statistics = new String[8]; //The current array of statistics in the displayed order
    private long timeout = 5000; //The maximum time in milliseconds that this class will wait for a server response
    private long timestamp = 0;  //The timestamp used for the recallable interface (always update this before you call: addExpectation())
    private Handler mHandler; //The handler used to execute tasks on the ui thread

    /**
     * creates a new Statitics object
     * @param statsActivity the currently active statistics activity
     */
    public Statistics(de.szut.dqi12.texasholdem.gui.Statistics statsActivity) {
        mHandler = new Handler(Looper.getMainLooper());
        this.statsActivity = statsActivity;
    }

    /**
     * This method requests a statistics update
     */
    public void updateStatistics() {
        Controller.getInstance().getSend().sendAction(ClientAction.STATS, null);
        timestamp = System.currentTimeMillis();
        Controller.getInstance().getDecryption().addExpectation(this);
    }

    /**
     * @return the current Statistics array
     */
    public String[] getStatistics() {
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
        if (action.equals(ServerAction.STATS)) {
            //A correct answer from the server with all statistics -> are now parsed into the string array
            for (int i = 0; i < params.length; i++) {
                statistics[i] = params[i];
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //updating the GUI
                        statsActivity.writeStats();
                    }
                });
            }
            return;
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    statsActivity.serverTimeout();
                }
            });
        }
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
