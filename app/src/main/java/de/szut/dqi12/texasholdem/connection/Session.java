package de.szut.dqi12.texasholdem.connection;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ServerAction;

/**
 * Created by Jascha Beste on 16.10.2015.
 */
public class Session implements Recallable {

    private String expectedAction;
    private String[] expectedParams;
    private Controller con;

    public Session(){
        con = Controller.getInstance();
        expectedAction = null;
        expectedParams = null;
    }

    public void login(){
        expectedAction = ServerAction.LOGGEDIN;
        con.getDecryption().addExpectation(this);

    }

    @Override
    public void inform(String action, String[] params) {
        switch(action) {
            case ServerAction.LOGGEDIN:
                //TODO inform GUI and send necessary info to the server etc.
        }
    }

    @Override
    public String Action() {
        return expectedAction;
    }

    @Override
    public String[] Params() {
        return expectedParams;
    }

}
