package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;
import de.szut.dqi12.texasholdem.gui.JoinGameLobby;
import de.szut.dqi12.texasholdem.gui.JoinGamePassword;

/**
 * Created by Jascha on 15.12.2015.
 * This class manages the retrieving and handling of all GameList information
 * /F0050/ Spiel beitreten
 */
public class GameList implements Recallable {

    private JoinGameLobby JGL; //The reference to the current GameListActivity(JoinGameLobby)
    private JoinGamePassword JGP; //The reference to the current PasswordActivity(JoinGamePassword)
    private ArrayList<Game> games; //A list of all active lobbies
    private static GameList instance; //This class is a singleton
    private long timeout = 5000; //The maximum time in milliseconds that this class will wait for a server response
    private long timestamp = 0; //The timestamp used for the recallable interface (always update this before you call: addExpectation())
    public Game selectedLobby; //The currently by the user selected lobby
    private String expectedAction; //The action this class is waiting for (always update this before you call: addExpectation())
    private Handler mHandler; //The Handler to call functions on the UI thread

    /**
     * private function since this is a singleton
     */
    private GameList() {
        games = new ArrayList<Game>();
        mHandler = new Handler(Looper.getMainLooper()); //Creating a Handler linked to the UI thread
    }

    /**
     * @return The only instance of this class
     */
    public static GameList getInstance() {
        if (instance == null) {
            instance = new GameList();
        }
        return instance;
    }

    /**
     * This funtion should only be called by the JoinGameLobby activity
     * @param jgl The currently active JoinGameLobby activity
     */
    public void registerJGL(JoinGameLobby jgl) {
        this.JGL = jgl;
    }

    /**
     * This funtion should only be called by the JoinGamePassword activity
     * @param jgp The currently active JoinGamePassword activity
     * */
    public void registerJGP(JoinGamePassword jgp) {
        this.JGP = jgp;
    }

    /**
     *
     * @return The current List of all games
     */
    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     * This method updates the currentGame List with new data
     * @param games A list of all games in this format: host#name#passwordboolean##lobbyID#maxPlayers#currentPlayers
     */
    public void updateList(String[] games) {
        for (String i : games) {
            if(i!=null){
            Game game = new Game();
            String[] params = i.split("#");
            game.host = params[0];
            game.name = params[1];
            if (params[2].equals("true")) {
                game.password = true;
            } else {
                game.password = false;
            }
            game.lobbyID = Integer.parseInt(params[3]);
            game.maxPlayers = Integer.parseInt(params[4]);
            game.currentPlayers = Integer.parseInt(params[5]);
            this.games.add(game);}
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                JGL.reloadListView();
            }
        });

    }

    /**
     * This method requests a new set of GameListData from the server
     */
    public void retrieveGameList() {
        String[] params = {};
        expectedAction = ServerAction.GAMELIST;
        timestamp = System.currentTimeMillis();
        Controller.getInstance().getDecryption().addExpectation(this);
        Controller.getInstance().getSend().sendAction(ClientAction.SEARCHGAME, params);
    }

    /**
     * This method requests to joing a specific game
     * @param gameID The LobbyID of the game
     * @param password The password of the requested game (null if no password is needed)
     */
    public void joinGame(int gameID, String password) {
        String[] params = {Integer.toString(gameID), password};
        timestamp = System.currentTimeMillis();
        expectedAction = ServerAction.JOINGAMEACK;
        Controller.getInstance().getDecryption().addExpectation(this);
        Controller.getInstance().getSend().sendAction(ClientAction.JOINGAME, params);
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
        if (action.equals(ServerAction.JOINGAMEACK)) {
            if (params[0].equals("true")) {
                Lobby.getInstance().newLobby(selectedLobby.maxPlayers, false, selectedLobby.name);
                Lobby.getInstance().setID(selectedLobby.lobbyID);
                if (selectedLobby.password == true) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            JGP.joinGameSuccessfull();
                        }
                    });

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            JGL.joinGameSuccessfull();
                        }
                    });

                }
            }
            if (params[0].equals("false")) {
                if (selectedLobby.password == true) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            JGP.joinGameFailed(params[1]);
                        }
                    });

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            JGL.joinGameFailed(params[1]);
                        }
                    });

                }
            } else {
                if (selectedLobby.password == true) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            JGP.joinGameFailed("unknown error");
                        }
                    });

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            JGL.joinGameFailed("unknown error");
                        }
                    });

                }
            }
        } else if (action.equals(ServerAction.GAMELIST)) {
            updateList(params);
        }
    }

    @Override
    public String Action() {
        return expectedAction;
    }

    @Override
    public String Params() {
        return null;
    }
}
