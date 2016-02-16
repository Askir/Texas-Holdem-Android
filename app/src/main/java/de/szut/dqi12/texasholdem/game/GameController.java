package de.szut.dqi12.texasholdem.game;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

/**
 * Created by Jascha, Marcel on 02.02.2016.
 */
public class GameController {

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
        // TODO: 12.02.2016 required?
        this.game = game;

    }

    // TODO: 12.02.2016 required?
    public void informGame(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                game.setNor(player.size() - 1);
                //setPotMoney(potmoney);

//                setBlinds(smallBlindPlayer);

            }
        });
    }

    /**
     * Initiates new game.
     *
     * @param player Name of participating players.
     * @param blindDistribution Just numbers from 0 - 5 allowed. delivered number is the number of client that
     *               gets the small blind.0 = Player, 1 = Rival 1, 2 = Rival 2 etc.
     */
    public void gameStart(String[] player, int blindDistribution){
        setPlayers(player);
        setBlinds(blindDistribution);
    }


    /**
     *
     * @param playerMoney Available money for the player.
     * @param minBid New value that shows player what to bid as minimum.
     * @param turnPlayerNum Number which user has the turn. Just numbers between 1 and 5 allowed.
     * @param rivalLeft Which rival has left.
     */
    public void nextTurn(int playerMoney, int minBid, int turnPlayerNum, String rivalLeft){

        // displays the budget\, which is available for the player
        game.displayTvBudget(playerMoney);

        //shows the minimum bid for the user
        game.displayTvMinBet(minBid);

        // announces which user has the turn
        game.announcePlayingUser(player.get(turnPlayerNum).getName());

        //when a rival has left
        for (int rivalPos = 0; rivalPos <= player.size(); rivalPos++){
            if(rivalLeft.equals(player.get(rivalPos).getName()))
                game.rivalLeft(rivalLeft, rivalPos);
        }

    }

    public void nextRound(int potMoney){
        /**
         * boardcards <>string cardcolor; int cardnumber</>
         * potmoney <>int potmoney</>
         */
        game.displayPot(potMoney);


    }

    public void gameEnd(){
        /**
         * playercards <>cc1, cn1; cc2, cn2; ...</>
         * playerleft <>user</>
         */
    }

    /**
    Restarts the game.
     */
    // TODO: 12.02.2016 not sure whether it works
    public void restartGame(){
        for(Player p : player){
            if(p.getMoney()<=0){
                player.remove(p);
            }
        }
    }

    /**
     *
     * @param player name of participating players
     */
    private void setPlayers(String[] player){
        this.player = new ArrayList<Player>();

        for(String i : player){
            this.player.add(new Player(i));
        }
    }

    /**
     * Changes the Cards on gameboard.
     * @param type 'b' for boardcard, 'r' for card of rivals and 'p' for card of player.
     * @param position  At which position the card is. b{1-5}, r{1-5} and pal
     * @param firstCard Whether the first card of the pair is meant. Not required for boardcard. ('c' = cross, 's' = spades, 'h' = hearts, 'd' = diamonds)
     * @param color Which color the meant card shall become.
     * @param number the number of card ('2' = 2; '3' = 3 ... '11' = Jack,'12' = Queen, '13' = King, '14' = Ace).
     */
    private void changeCard(String type, int position, Boolean firstCard, String color, int number){
        if(type.equals("b"))
        {
            boardCards.add(position, color + "" + number + "");
            // TODO: 12.02.2016 add card to board<Card> ??
            game.setBoardCard(position, color, number);
        }
        else if(type.equals("r"))
        {
            game.setRivalCards(position, firstCard, color, number);
            game.setRivalCards(position, firstCard, color, number);
        }
        else if(type.equals("p"))
        {
                game.setPlayerCards(firstCard, color, number);
                game.setPlayerCards(firstCard, color, number);
        }
    }

    /**
     * Sets small game view, big blind will appear automatically.
     *
     * @param player Just numbers from 0 - 5 allowed. delivered number is the number of client that
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

