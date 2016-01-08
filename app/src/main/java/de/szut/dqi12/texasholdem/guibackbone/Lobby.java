package de.szut.dqi12.texasholdem.guibackbone;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;
import de.szut.dqi12.texasholdem.gui.CreateGameLobby;

/**
 * Created by Jascha on 15.12.2015.
 */
public class Lobby implements Recallable{
    //userstate = ready/not ready
    private int maxPlayers;
    private int ID;
    private boolean userState;
    private String[] userNames;
    private boolean[] states;
    private String lobbyName;
    private long timeout = 5000;
    private long timestamp = 0;
    private static Lobby instance;
    private CreateGameLobby lobbyActivity;

    //TODO: implement server ACK and a lot of other stuff please revisit and uptade this comment
    public static Lobby getInstance(){
        if (instance== null){
            instance = new Lobby();
        }
        return instance;
    }
    private Lobby(){

    }
    public int getMaxPlayers(){
        return maxPlayers;
    }
    public boolean[] getStates(){
        return states;
    }
    public void registerActivity(CreateGameLobby lobbyActivity){
        this.lobbyActivity = lobbyActivity;
    }

    public void newLobby(int maxPlayers, boolean userState, String lobbyName, String password){
        String[] params = {Integer.toString(maxPlayers),Integer.toString(ID),lobbyName, password};
        Controller.getInstance().getSend().sendAction(ClientAction.LOBBY,params);
        this.maxPlayers=maxPlayers;
        this.ID = 0;
        this.userState = userState;
        states = new boolean[maxPlayers];
        this.userNames = new String[maxPlayers];

    }

    public void setState(boolean userState){
        this.userState=userState;
        String[] params = {Boolean.toString(userState)};
        Controller.getInstance().getSend().sendAction(ClientAction.LOBBY,params);
    }

    public boolean getState(){
        return userState;
    }

    public String[] getPlayernames(){
        return userNames;
    }

    public void addUser(int nr, String userName, boolean state){
        userNames[nr] = userName;
        states[nr] = state;
        return;
    }

    public void gameStart(){
        //inform GUI
        //and Create new Game after 5 seconds
        return;
    }

    public void changeUserState(int nr, boolean state){
        states[nr] = state;
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
        //inform GUI
        //finalize Lobby with ID etc.
        return;
    }

    @Override
    public String Action() {
        return ServerAction.LOBBYACK;
    }

    @Override
    public String[] Params() {
        return null;
    }
}
