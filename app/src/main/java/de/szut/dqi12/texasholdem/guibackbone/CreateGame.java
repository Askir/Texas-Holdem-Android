package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha on 15.12.2015.
 * This Class manages the Creation of games and opening the Lobby after the Server has confirmed the creation
 */
public class CreateGame implements Recallable {

    private de.szut.dqi12.texasholdem.gui.CreateGame createGameActivity; //The active CreateGameActivity to inform about Server Messages
    private Handler mHandler; //The Handler that is used to executed Code on the GUI Thread
    private long timeout = 5000; //The maximum time in milliseconds that this class will wait for a server response
    private long timestamp = 0; //The timestamp used for the recallable interface (always update this before you call: addExpectation())

    /**
     *
     * @param createGameActivity The current CreateGame activity
     */
    public CreateGame(de.szut.dqi12.texasholdem.gui.CreateGame createGameActivity) {
        this.mHandler = new Handler(Looper.getMainLooper()); //Creating a Handler linked to the UI thread
        this.createGameActivity = createGameActivity; //saving the reference to the CreateGame activity
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


        if (action.equals(ServerAction.CREATEGAMEACK)) {
            //information about a CreateGameRequest
            switch (params[0]) {

                case "true":
                    //Creation acknowledged and accpeted
                    //informing the GUI which handles everything else
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            createGameActivity.gameCreationSuccessful();
                        }
                    });
                    break;
                case "false":
                    //Creation acknowledged and rejected
                    //informing the GUI which handles everything else with the specific error parameters
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            createGameActivity.gameCreationFailed(params[1]);
                        }
                    });
                    break;
                default:
                    //If this is called, a Serverside error occured, do nothing for now
                    break;
            }
        }
    }

    /**
     *
     * @param gameName The requested GameName as String
     * @param maxPlayers The maximum amount of players that should be able to participate in the game
     * @param password The password of the lobby as String, null if no password is required
     */
    public void requestGameCreation(String gameName, int maxPlayers, String password) {
        String[] params = {gameName, Integer.toString(maxPlayers), password};
        timestamp = System.currentTimeMillis();
        Controller.getInstance().getSend().sendAction(ClientAction.CREATEGAME, params);
        Controller.getInstance().getDecryption().addExpectation(this);
        Lobby.getInstance().newLobby(maxPlayers, false, gameName);
    }

    @Override
    public String Action() {
        return ServerAction.CREATEGAMEACK;
    }

    @Override
    public String Params() {
        return null;
    }
}
