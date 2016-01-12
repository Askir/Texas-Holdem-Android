package de.szut.dqi12.texasholdem.guibackbone;

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
public class GameList implements Recallable{
    private JoinGameLobby JGL;
    private JoinGamePassword JGP;
    private ArrayList<Game> games;
    private static GameList instance;
    private long timeout=5000;
    private long timestamp=0;
    public int selectedLobbyID;

    private GameList(){
        games = new ArrayList<Game>();
    }

    public static GameList getInstance(){
        if(instance==null){
            instance = new GameList();
        }
        return instance;
    }
    public void registerJGL(JoinGameLobby jgl){
        this.JGL = jgl;
    }
    public void registerJGP(JoinGamePassword jgp){
        this.JGP =jgp;
    }
    //TODO: add retrieving the gamelist from the server
    public ArrayList<Game> getGames(){
        return games;
    }
    public void updateList(String[] games){
        for(String i:games){
            Game game = new Game();
            String[] params=i.split("#");
            game.host = params[0];
            game.name = params[1];
            if(params[2].equals("true")){
                game.password = true;
            }
            else{
                game.password = false;
            }
            game.lobbyID = Integer.parseInt(params[3]);
            game.maxPlayers = Integer.parseInt(params[4]);
            game.currentPlayers = Integer.parseInt(params[5]);
            this.games.add(game);
        }
    }

   public void joinGame(int gameID,String password) {
       String[] params = {Integer.toString(gameID),password};
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
    public void inform(String action, String[] params) {
        if(params[0].equals("success")){
            //Lobby.getInstance().newLobby(); TODO change selected lobbyID to gameobject so you can create the real lobby here!
            if(games.get(selectedLobbyID).password==true){
                JGP.joinGameSuccessfull();
            }
            else{
                JGL.joinGameSuccessfull();
            }
        }
        if(params[0].equals("denied")){
            if(games.get(selectedLobbyID).password==true){
                JGP.joinGameFailed(params[1]);
            }
            else{
                JGL.joinGameFailed(params[1]);
            }
        }
        else{
            if(games.get(selectedLobbyID).password==true){
                JGP.joinGameFailed("unknown error");
            }
            else{
                JGL.joinGameFailed("unknown error");
            }
        }
    }

    @Override
    public String Action() {
        return ServerAction.JOINGAMEACK;
    }

    @Override
    public String[] Params() {
        return new String[0];
    }
}
