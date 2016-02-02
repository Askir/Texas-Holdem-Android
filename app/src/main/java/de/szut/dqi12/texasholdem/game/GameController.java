package de.szut.dqi12.texasholdem.game;

import java.util.ArrayList;

import de.szut.dqi12.texasholdem.action.GameAction;

/**
 * Created by Jascha on 02.02.2016.
 */
public class GameController {
    private int playerCount;
    private int turn;
    private Player user;
    private ArrayList<Player> player;
    private ArrayList<Card> board;
    public GameController(){

    }
    public void refreshGameState(){
        turn = 0;
        for(Player p : player){
            if(p.getMoney()<=0){
                player.remove(p);
            }
        }
    }

    private void setPlayers(String[] player){
        for(String i : player){
            this.player.add(new Player(i));
        }
    }

    public void update(GameAction action, String[] params){

    }



}

