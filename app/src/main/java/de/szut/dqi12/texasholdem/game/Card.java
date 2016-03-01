package de.szut.dqi12.texasholdem.game;

/**
 * Created by Jascha on 02.02.2016.
 * container for card values
 */
public class Card {
    public Card(){
        this.color = CardColor.UNKNOWN;
        this.value = CardValue.UNKNOWN;
    }

    public CardColor color;
    public int value;

}
