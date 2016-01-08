package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha on 15.12.2015.
 */
public class CreateGame implements Recallable{
    private de.szut.dqi12.texasholdem.gui.CreateGame createGameActivity;
    private Handler mHandler;
    public CreateGame(de.szut.dqi12.texasholdem.gui.CreateGame createGameActivity){
        this.mHandler=new Handler(Looper.getMainLooper());
        this.createGameActivity = createGameActivity;
    }
    @Override
    public void inform(String action, final String[] params) {
        if(action.equals(ServerAction.CREATEGAMEACK)){
            switch(params[0]){
                case "confirmed":
                    final int lobbyID = Integer.parseInt(params[1]);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //createGameActivity.gameCreationSuccessful();
                        }
                    });

                    //inform GUI and Create Lobby
                    break;
                case "failed":
                    //inform GUI with error parameters params[1]
                    break;
                default:
                    //inform GUI
                    break;
            }
        }
    }

    public void requestGameCreation(String gameName, int maxPlayers, String password){
        String[] params = {gameName,Integer.toString(maxPlayers),password};
        Controller.getInstance().getSend().sendAction(ClientAction.CREATEGAME, params);
        Controller.getInstance().getDecryption().addExpectation(this);
    }
    @Override
    public String Action() {
        return ServerAction.CREATEGAMEACK;
    }

    @Override
    public String[] Params() {
        return null;
    }
}