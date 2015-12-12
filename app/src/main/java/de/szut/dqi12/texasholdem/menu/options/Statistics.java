package de.szut.dqi12.texasholdem.menu.options;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha on 11.12.2015.
 */
public class Statistics implements Recallable{


    private String[] statistics = new String[5];

    public void updateStatistics(){
        Controller.getInstance().getSend().sendAction(ClientAction.STATS, null);
        Controller.getInstance().getDecryption().addExpectation(this);
    }

    public String[] getStatistics(){
        return statistics;
    }

    @Override
    public void inform(String action, String[] params) {
        for(int i = 0; i < params.length; i++){
            statistics[i] = params[i];
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
