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

    // TODO: 12.02.2016 required? to Jascha
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

        boardCards = new ArrayList<>();
//        user = new Player(); todo initialize user required? to Jascha

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
        user.setMoney(playerMoney);

        //shows the minimum bid for the user
        game.displayTvMinBet(minBid);

        // announces which user has the turn
        String playingUser = player.get(turnPlayerNum).getName();
        game.announcePlayingUser(playingUser);
        if(playingUser.equals(user.getName()))
            user.setIsCurrentPlayer(true);

        //when a rival has left
        for (int rivalPos = 0; rivalPos <= player.size(); rivalPos++){
            if(rivalLeft.equals(player.get(rivalPos).getName()))
                game.rivalLeft(rivalLeft, rivalPos);
        }

    }

    /**
     * Initiates next round.
     * @param potMoney Current value of pot.
     * @param bcColor Color of boardcard that will appear.
     * @param bcNumber Number/Value of boardcard that will appear.
     */
    public void nextRound(int potMoney, String bcColor, int bcNumber){

        game.displayPot(potMoney);
        changeCard("b", boardCards.size() + 1, false, bcColor, bcNumber);

    }

    /**
     * Initiates the end of the game.
     * @param rivalsCardsColors String-Array in which all card colors of the rivals are. starting at
     *                          the first rival from top left and ending with the last rival from top right.
     * @param rivalsCardsNumbers int-Array in which all card numbers of the rivals are. same principle
     *                           like in rivalsVCardsColors.
     * @param rivalLeft String-Array in which alle rivals are that left the game.
     */
    public void gameEnd(String[] rivalsCardsColors, int[] rivalsCardsNumbers, String[] rivalLeft){

        // show all rival cards
        for(int p = 0; p <= player.size(); p++){
            if(player.get(p).getIsPlaying()){
                switch (p){
                    case 0:
                        changeCard("r", 1, true, rivalsCardsColors[0],rivalsCardsNumbers[0]);
                        changeCard("r", 1, false, rivalsCardsColors[1],rivalsCardsNumbers[1]);
                        break;
                    case 1:
                        changeCard("r", 1, true, rivalsCardsColors[2],rivalsCardsNumbers[2]);
                        changeCard("r", 1, false, rivalsCardsColors[3],rivalsCardsNumbers[3]);
                        break;
                    case 2:
                        changeCard("r", 1, true, rivalsCardsColors[4],rivalsCardsNumbers[4]);
                        changeCard("r", 1, false, rivalsCardsColors[5],rivalsCardsNumbers[5]);
                        break;
                    case 3:
                        changeCard("r", 1, true, rivalsCardsColors[6],rivalsCardsNumbers[6]);
                        changeCard("r", 1, false, rivalsCardsColors[7],rivalsCardsNumbers[7]);
                        break;
                    case 4:
                        changeCard("r", 1, true, rivalsCardsColors[8],rivalsCardsNumbers[8]);
                        changeCard("r", 1, false, rivalsCardsColors[9],rivalsCardsNumbers[9]);
                        break;
                    case 5:
                        changeCard("r", 1, true, rivalsCardsColors[10],rivalsCardsNumbers[10]);
                        changeCard("r", 1, false, rivalsCardsColors[11],rivalsCardsNumbers[11]);
                        break;
                }}}

        //when a rival has left
        for(int i = 0; i <= rivalLeft.length; i++)
        {
            for (int rivalPos = 0; rivalPos <= player.size(); rivalPos++)
            {
                if(rivalLeft[i].equals(player.get(rivalPos).getName()))
                    game.rivalLeft(rivalLeft[i], rivalPos);
            }
        }

        game.endGameQuery(); // TODO: 16.02.2016 end game or not???!?!??
        // invoke restart game???
        // ed
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

