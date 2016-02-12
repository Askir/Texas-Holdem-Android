package de.szut.dqi12.texasholdem.game;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import de.szut.dqi12.texasholdem.action.GameAction;

/**
 * Created by Jascha, Marcel on 02.02.2016.
 */
public class GameController {

    private int turn;
    private int potmoney;
    private int smallBlindPlayer;

    private Player user;
    private ArrayList<Player> player;
    private ArrayList<Card> board;
    private ArrayList<String> boardCards;

    private de.szut.dqi12.texasholdem.gui.Game game;
    private Handler mHandler;

    private static GameController instance;

    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }


    private GameController(){

        mHandler = new Handler(Looper.getMainLooper());
        this.game = game;

    }

    public void informGame(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                game.setNor(player.size()-1);
                setPotmoney(potmoney);

                setBlinds(smallBlindPlayer);

            }
        });
    }

    /**
    Restarts the game.
     */
    // TODO: 12.02.2016 not sure whether it works
    public void restartGame(){
        turn = 0;
        for(Player p : player){
            if(p.getMoney()<=0){
                player.remove(p);
            }
        }
    }

    /**
     *
     * @param player name of players
     */
    private void setPlayers(String[] player){
        for(String i : player){
            this.player.add(new Player(i));
        }
    }

    /**
     *Sets the Boardcards.
     * @param boardCard which card shall be changed. 1st-5th.
     * @param color which color the boardcard shall become. ('c' = cross, 's' = spades, 'h' = hearts, 'd' = diamonds)
     * @param number the number or picture ('2' = 2; '3' = 3 ... '11' = Jack, \n
     *                  '12' = Queen, '13' = King, '14' = Ace).
     */
    private void setBoardCard(int boardCard, String color, int number){
        boardCards.add(boardCard-1, color + "" + number + "");
        game.setBoardCard(boardCard, color, number);
    }

    /**
    Sets the Potview in Game.
     @param pm new value for potview.
     */
    private void setPotmoney(int pm){
        game.displayTvBudget(pm);
    }


    /**
     * sets small game view, big blind will appear automatically.
     *
     * @param player just numbers from 0 - 5 allowed. delivered number is the number of client that
     *               gets the small blind.0 = Player, 1 = Rival 1, 2 = Rival 2 etc.
     */
    public void setBlinds(int player){
        if(player <= 5){
            switch (player){
                case 0:
                    game.setBlinds("P");
                    break;
                case 1:
                    game.setBlinds("R1");
                    break;
                case 2:
                    game.setBlinds("R2");
                    break;
                case 3:
                    game.setBlinds("R3");
                    break;
                case 4:
                    game.setBlinds("R4");
                    break;
                case 5:
                    game.setBlinds("R5");
                    break;
            }
        }
    }


}

