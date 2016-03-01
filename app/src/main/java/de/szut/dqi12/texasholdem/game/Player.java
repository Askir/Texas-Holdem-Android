package de.szut.dqi12.texasholdem.game;

/**
 * Created by Jascha on 02.02.2016.
 * manages a single player
 */
public class Player {

    private String name;
    private int money;
    private int currentBet;
    private Boolean smallB = false;
    private Boolean bigB = false;
    private Boolean isCurrentPlayer = false;
    private Boolean isPlaying = true;
    private Card[] handCards;

    Player(String name) {
        this.name = name;
        this.money = 5000;
        handCards = new Card[2];
    }

    public Card[] getHand() {
        return handCards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBet(int bet) {
        currentBet = bet;
    }

    public int getBet() {
        return currentBet;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setSmallB(Boolean smallB) {
        this.smallB = smallB;
    }

    public void setBigB(Boolean bigB) {
        this.bigB = bigB;
    }

    public void setIsCurrentPlayer(Boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public void hasLeft() {
        this.isPlaying = false;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Boolean getBigB() {
        return bigB;
    }

    public Boolean getSmallB() {

        return smallB;
    }

    public Boolean getIsCurrentPlayer() {
        return isCurrentPlayer;
    }

    public Boolean getIsPlaying() {
        return isPlaying;
    }
}
