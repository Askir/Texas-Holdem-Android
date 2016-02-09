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
 */
public class GameList implements Recallable {
    private JoinGameLobby JGL;
    private JoinGamePassword JGP;
    private ArrayList<Game> games;
    private static GameList instance;
    private long timeout = 5000;
    private long timestamp = 0;
    public Game selectedLobby;
    private String expectedAction;
    private Handler mHandler;

    private GameList() {
        games = new ArrayList<Game>();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static GameList getInstance() {
        if (instance == null) {
            instance = new GameList();
        }
        return instance;
    }

    public void registerJGL(JoinGameLobby jgl) {
        this.JGL = jgl;
    }

    public void registerJGP(JoinGamePassword jgp) {
        this.JGP = jgp;
    }

    //TODO: add retrieving the gamelist from the server
    public ArrayList<Game> getGames() {
        return games;
    }

    public void updateList(String[] games) {
        for (String i : games) {
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
            this.games.add(game);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                JGL.reloadListView();
            }
        });

    }

    public void retrieveGameList() {
        String[] params = {};
        expectedAction = ServerAction.GAMELIST;
        timestamp = System.currentTimeMillis();
        Controller.getInstance().getDecryption().addExpectation(this);
        Controller.getInstance().getSend().sendAction(ClientAction.SEARCHGAME, params);
    }

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

    /**
     * @param action the called Action
     * @param params
     */
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
        }
        else if(action.equals(ServerAction.GAMELIST)){
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
