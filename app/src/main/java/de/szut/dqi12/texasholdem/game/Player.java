package de.szut.dqi12.texasholdem.game;

/**
 * Created by Jascha on 02.02.2016.
 */
public class Player {

    private String name;
    private int money;

    Player(String name){
        this.name = name;
        this.money = 5000;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }
}
