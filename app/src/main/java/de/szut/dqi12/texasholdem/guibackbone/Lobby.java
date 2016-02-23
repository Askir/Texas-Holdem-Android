package de.szut.dqi12.texasholdem.guibackbone;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.action.ClientAction;
import de.szut.dqi12.texasholdem.action.ServerAction;
import de.szut.dqi12.texasholdem.connection.Recallable;
import de.szut.dqi12.texasholdem.gui.CreateGameLobby;

/**
 * Created by Jascha on 15.12.2015.
 * This class manages everything related to the lobby before the game starts
 */
public class Lobby implements Recallable {
    //userstate = ready/not ready
    private int maxPlayers; //The maximum amout of players in the lobby
    private int ID; //The ID of this lobby (not needed atm but saved for the future)
    private boolean userState; //The state this user has currently selected (false = not ready, true = ready)
    private String[] userNames; //All usernames in the displayed order
    private boolean[] states; //All states of these users in the displayed order
    private String lobbyName; //The name of this lobby (not needed atm but saved for the future)
    private long timeout = 5000; //The maximum time in milliseconds that this class will wait for a server response
    private long timestamp = 0; //The timestamp used for the recallable interface (always update this before you call: addExpectation())
    private static Lobby instance; //This class is a singleton
    private CreateGameLobby lobbyActivity; //The currently active Lobby activity (CreateGame Activity)
    private String TAG = "Lobby"; //The TAG for Log outputs (mostly debug)
    private Handler mHandler; //The handler to execute tasks on the ui thread

    /**
     * This class is a singleton
     * @return the only instance of this class
     */
    public static Lobby getInstance() {
        if (instance == null) {
            instance = new Lobby();
        }
        return instance;
    }

    /**
     * Since this class is a singleton the contsructor is protected
     */
    protected Lobby() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     *
     * @return the maxmimum amount of players in this lobby
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     *
     * @return The states of all players in the displayed order
     */
    public boolean[] getStates() {
        return states;
    }

    /**
     * Used to register the currently active CreateGameLobby activity this funtion should only be called by the CreateGameLobby activity
     * @param lobbyActivity The currently active createGameLobby activity
     */
    public void registerActivity(CreateGameLobby lobbyActivity) {
        this.lobbyActivity = lobbyActivity;
    }

    /**
     * Used to instantiate a new lobby with new values
     * @param maxPlayers The maximum amount of players in the new lobby
     * @param userState The current state of the user (should be false most of the time)
     * @param lobbyName The name of the lobby
     */
    public void newLobby(int maxPlayers, boolean userState, String lobbyName) {
        Log.d(TAG,"new Lobby created with maxPlayers:"+maxPlayers);
        this.maxPlayers = maxPlayers;
        this.ID = 0;
        this.userState = userState;
        states = new boolean[maxPlayers];
        this.userNames = new String[maxPlayers];

    }

    /**
     * Setter of the lobbyID
     * @param ID The ID of the active lobby
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * setting the users state and informing the server and gui about the change
     * @param userState the new userstate (true = ready, false = not ready)
     */
    public void setState(boolean userState) {
        this.userState = userState;
        String[] params = {Boolean.toString(userState)};
        Controller.getInstance().getSend().sendAction(ClientAction.LOBBY, params);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                lobbyActivity.playerChanged();
            }
        });

    }

    /**
     *
     * @return the current user state
     */
    public boolean getState() {
        return userState;
    }

    /**
     *
     * @return all playernames in the displayed order
     */
    public String[] getPlayernames() {
        return userNames;
    }

    /**
     * Changes a user in the user List
     * @param nr Number of the user
     * @param userName Name of the specific user
     * @param state State of the user
     */
    public void changeUser(int nr, String userName, boolean state) {
        Log.d(TAG, "user changed" + userName);
        userNames[nr] = userName;
        states[nr] = state;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                lobbyActivity.playerChanged();
            }
        });

    }

    /**
     * Starts the game (Will be executed when all players are ready)
     */
    public void gameStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                lobbyActivity.gameStart();
            }
        });
    }

    /**
     * Changes the state of a user
     * @param nr Number of the user
     * @param state New State of the user
     */
    public void changeUserState(int nr, boolean state) {
        states[nr] = state;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                lobbyActivity.playerChanged();
            }
        });

    }

    //the interface is probably not needed anymore since CreateGame and joinGame overtake these actions
    //code stays here for clarity but will be removed once the gui is finalized TODO: remove this code once done with the GUI
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
    public String Params() {
        return null;
    }
}
