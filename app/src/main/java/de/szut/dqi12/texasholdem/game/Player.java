package de.szut.dqi12.texasholdem.game;

import java.security.BasicPermission;

/**
 * Created by Jascha on 02.02.2016.
 */
public class Player {

    private String name;
    private int money;
    private Boolean smallB = false;
    private Boolean bigB = false;
    private Boolean isCurrentPlayer = false;
    private Boolean isPlaying = true;

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

    public void setSmallB(Boolean smallB) {
        this.smallB = smallB;
    }

    public void setBigB(Boolean bigB) {
        this.bigB = bigB;
    }

    public void setIsCurrentPlayer(Boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public void hasLeft(){
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

    public Boolean getIsPlaying(){return isPlaying;}
}
