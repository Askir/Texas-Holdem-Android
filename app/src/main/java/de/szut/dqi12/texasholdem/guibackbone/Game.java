package de.szut.dqi12.texasholdem.guibackbone;

/**
 * Created by Jascha on 15.12.2015.
 * Just a container for some game specific data
 * We are not using getters and setters in this case because it simply isn't necessary
 */
public class Game {
    public String name; //The name of the game
    public boolean password; //The passwordstatus of the game
    public String host; //The hostname of the game creator
    public int lobbyID; //The LobbyID of this game
    public int maxPlayers; //The maxmimum amount of participants
    public int currentPlayers; //The current amount of paticipants
}
