package de.szut.dqi12.texasholdem.guibackbone;

import java.util.ArrayList;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;

/**
 * Created by Jascha on 15.12.2015.
 */
public class GameList implements Recallable{
    private ArrayList<Game> games;
    public GameList(){
        games = new ArrayList<Game>();
    }
    private long timeout=5000;
    private long timestamp=0;

    //TODO: add retrieving the gamelist from the server

    public void updateList(String[] games){
        for(String i:games){
            Game game = new Game();
            String[]params=i.split("#");
            game.host = params[0];
            game.name = params[1];
            if(params[2].equals("true")){
                game.password = true;
            }
            else{
                game.password = false;
            }
            game.lobbyID = Integer.parseInt(params[3]);
            this.games.add(game);
        }
        return;
    }

   public void joinGame(int gameID,String password) {
       String[] params = {password};
       Controller.getInstance().getSend().sendAction(ClientAction.JOINGAME, params);
       return;
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
            //inform GUI and create Lobby
        }
        if(params[0].equals("denied")){
            //inform GUI with params (probably wrong password)
        }
        else{
            //inform gui with params[]
        }
    }

    @Override
    public String Action() {
        return ServerAction.JOINGGAMEACK;
    }

    @Override
    public String[] Params() {
        return new String[0];
    }
}
