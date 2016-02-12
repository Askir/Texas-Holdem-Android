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

    /**
     * String username      -
     * String usermoney     -
     * String currentbid    -
     *
     * bool iscurrentplayer -
     * bool hassmallblind   -
     * bool hasbigblind     -
     *
     * String boardcard1-5  -
     *
     * int potmoney         -
     */

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

    public void restartGame(){
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

    /**
     *
     * @param boardCard 1-5
     * @param color
     * @param number
     */
    private void setBoardCard(int boardCard, String color, int number){
        boardCards.add(boardCard-1, color + "" + number + "");
        game.setBoardCard(boardCard, color, number);
    }

    private void setPotmoney(int pm){
        game.displayTvBudget(pm);
    }

    public void setSmallBlindPlayer(int smallBlindPlayer) {
        this.smallBlindPlayer = smallBlindPlayer;
    }

    public void update(GameAction action, String[] params){
        // TODO: 04.02.2016 dono for what needed
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

