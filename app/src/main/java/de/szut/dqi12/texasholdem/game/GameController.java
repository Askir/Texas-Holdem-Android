package de.szut.dqi12.texasholdem.game;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.gui.Game;

/**
 * Created by Jascha, Marcel on 02.02.2016.
 */
public class GameController {

    private int playerCount;
    private int turn;
    private ArrayList<Player> players;
    private int user;
    private ArrayList<Card> boardcards;
    private final static String TAG = "GameController";
    private Game gameActivity;
    private Handler mHandler;

    public GameController(){
        turn = 1;
        boardcards = new ArrayList<Card>();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean isUserTurn(){
        return turn == user;
    }

    public void nextTurn(){
        turn += 1;
        if(turn>playerCount){
            turn = 1;
        }
    }

    public Card getBoardCard(int index){
        return boardcards.get(index);
    }

    public Player getPlayer(int playernumber){
        if(playernumber>playerCount-1){
            return null;
        }
        else{
            return players.get(playernumber);
        }
    }

    public Player getUser(){
        return players.get(user);
    }

    public void addPlayer(Player player){
        players.add(player);
    }
    public void addBoardCard(Card card){
        boardcards.add(card);
    }
    public void setPlayerCount(int count){
        this.playerCount = count;
    }
    private void calculateUser(){
        for(int i = 0; i<players.size();i++){
            if(players.get(i).getName().equals(Controller.getInstance().getUsername())){
                user = i;
            }
            else{
                Log.d(TAG, "User not found in PlayerList error!");
            }
        }
    }

    public void refreshGUI(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                gameActivity.refresh();
            }
        });
    }

    public void registerGUI(Game gameActivity){
        this.gameActivity = gameActivity;
    }



}

