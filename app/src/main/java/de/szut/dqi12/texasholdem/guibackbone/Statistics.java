package de.szut.dqi12.texasholdem.guibackbone;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha on 11.12.2015.
 */
public class Statistics implements Recallable{

    //TODO: update inform function with timeout content and revisit class to update this comment
    private String[] statistics = new String[5];
    private long timeout = 5000;
    private long timestamp = 0;

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
            //maybe update GUI tbd with Marcel
        }
        return;
    }

    @Override
    public String Action() {
        return ServerAction.STATS;
    }

    @Override
    public String[] Params() {
        return null;
    }
}
