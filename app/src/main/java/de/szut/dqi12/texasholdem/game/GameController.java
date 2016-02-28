package de.szut.dqi12.texasholdem.game;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

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
//        this.game = game;

    }

    /**
     * Initiates new game.
     *
     * @param player Name of participating players.
     * @param blindDistribution Just numbers from 0 - 5 allowed. delivered number is the number of client that
     *               gets the small blind.0 = Player, 1 = Rival 1, 2 = Rival 2 etc.
     */
    public void gameStart(final String[] player,final int blindDistribution){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                boardCards = new ArrayList<>();

                setPlayers(player);
                setBlinds(blindDistribution);
            }
        });


    }

    /**
     *
     * @param playerMoney Available money for the player.
     * @param minBid New value that shows player what to bid as minimum.
     * @param turnPlayerNum Number which user has the turn. Just numbers between 1 and 5 allowed.
     * @param fold whethter the player folded or not
     * @param bet the amount the player bid (if he did)
     */
    public void nextTurn(final int playerMoney,final int minBid,final int turnPlayerNum,final boolean fold,final int bet){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // displays the budget\, which is available for the player
                game.displayTvBudget(playerMoney);
                user.setMoney(playerMoney);

                //shows the minimum bid for the user
                game.displayTvMinBet(minBid);

                // announces which user has the turn
                String playingUser = player.get(turnPlayerNum+2).getName();
                game.announcePlayingUser(playingUser);
                if(playingUser.equals(user.getName())){
                    user.setIsCurrentPlayer(true);}

                if(fold){
                    game.setLabelText(turnPlayerNum+1,"FOLD");
                }
                else{
                    game.setLabelText(turnPlayerNum+1,"Bid: "+bet+"©");
                }

            }
        });


    }

    /**
     * Initiates next round.
     * @param potMoney Current value of pot.
     * @param cards The board cards
     */
    public void nextRound(final int potMoney,final String[]cards){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                game.displayPot(potMoney);
                for(int i=0;i<cards.length;i+=2){
                    game.setBoardCard(i, Character.toString(cards[i].toLowerCase().charAt(0)),Integer.parseInt(cards[i+1]));
                }
            }
        });



    }

    /**
     * ends a gmae
     * @param cardColors all card colors
     * @param cardNumbers all card numbers
     */
    public void gameEnd(final String[] cardColors,final int[] cardNumbers){
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                int thisPlayer = 0;
                for(int i = 0; i < cardColors.length; i++){
                    if(cardColors[i].toLowerCase().charAt(0) == user.getHand()[0].color.charAt(0)){
                        thisPlayer = i;
                    }
                }
                int i = 0;
                if(thisPlayer + 2 == cardColors.length){
                    i = 0;
                }
                else
                    i = thisPlayer + 2;

                while(i != thisPlayer){
                    changeCard("r", 1, true, cardColors[i], cardNumbers[i]);
                    changeCard("r", 1, false, cardColors[i+1], cardNumbers[i+1]);
                    if(i+2 == player.size())
                        i = 0;
                    else
                        i += 2;
                }

                game.endGameQuery(); // TODO: 16.02.2016 end game or not???!?!??
                // invoke restart game???
                // ed
            }
        });
    }


    public void restartGame(){


        // reset money of players
        for(Player p : player){
            if(p.getMoney()<=0){
                player.remove(p);
            }
        }
    }

    /**
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
     * @param position  At which position the card is. b{1-5}, r{1-5} and player(not required)
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
            Card c = new Card();
            c.color = color;
            c.value = number;
            if(firstCard)
                user.getHand()[0] = c;
            else
                user.getHand()[1] = c;
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

