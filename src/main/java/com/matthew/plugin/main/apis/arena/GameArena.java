package com.matthew.plugin.main.apis.arena;

import com.matthew.plugin.main.GameState;
import com.matthew.plugin.main.apis.Config;
import com.matthew.plugin.main.apis.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameArena {

    /**
     Class attributes & un-instantiated objects
     */
    private int id;
    private ArrayList<UUID> players;
    private Location spawn;
    private GameState state;
    private GameCountdown gameCountdown;
    private Game game;

    /**
     initialize the attributes & instantiate the objects above. Also set the state of the game to
     recruiting once the constructor is called
     */
    public GameArena(int id) {
        this.id = id;
        players = new ArrayList<>();
        spawn = Config.getArenaSpawn(id);
        state = GameState.RECRUITING;
        gameCountdown = new GameCountdown(this);
        game = new Game(this);
    }

    /*
     @return id
     */
    public int getID() { return id; }

    /*
     @return all players currently in the game
     */
    public List<UUID> getPlayers() { return players; }

    /*
     basic logic to start the game by having an arena method to run the method start in game class
     */
    public void start() {
        game.start();
    }

    /*
     arena method used to run the gameForceStart method in the game class.
     */
    public void forceStart() { game.gameForceStart(); }

    /*
     basic logic to reset the arena by
      - Teleporting all players in the players list to the lobbyspawn
      - resetting the gamestate for the arena to recruiting so players can join again
      - clearing the arraylist (which should have already been cleared before this point)
      - recalling the constructors of countdown and game to reset as well
     */
    public void reset() {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).teleport(Config.getLobbySpawn());
        }

        state = GameState.RECRUITING;
        players.clear();
        gameCountdown = new GameCountdown(this);
        game = new Game(this);
    }

    /*
     Method that will reinitialize the game countdown and game class to set the game back to recruiting without
     errors
     */
    public void setRecruiting() {
        state = GameState.RECRUITING;
        gameCountdown = new GameCountdown(this);
        game = new Game(this);
    }

    /*
     send a message to all players in the players arraylist based on their uuid
     */
    public void sendMessage(String message) {
        for(UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    /*
     - add a player to the players arraylist > teleport them to the arena spawn > send a message
       to the other players in the arraylist that the specified player has joined
     - run the countdown begin method if the minimum player count to begin the countdown has
       been reached
     */
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);
        sendMessage(ChatColor.DARK_GRAY + "Join> " + ChatColor.GRAY + player.getName());

        if(players.size() >= Config.getRequiredPlayers()) {
            gameCountdown.begin();
        }
    }

    /*
     - remove a player from the players arraylist > teleport them back to the lobby spawn > announce to
       the other players in the arraylist that the specified player has quit
     - if the player size ends up becoming less than the required players amount for a countdown to start
       then run the reset method to set the game back to recruiting and look for more players
     - if the game is live and all of the players decide to quit reset the game back to recruiting
       and look for more players
     */
    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(Config.getLobbySpawn());
        sendMessage(ChatColor.DARK_GRAY + "Quit> " + ChatColor.GRAY + player.getName());

        if(players.size() == 0 && state.equals(GameState.LIVE)) {
            reset();
        }
    }

    /*
     @return the enum [recruiting, live, countdown] that refers to the state the game is currently in
     */
    public GameState getState() { return state; }

    /*
     set the state of a game to the specified enum [recruiting, live, countdown]
     */
    public void setState(GameState state) { this.state = state; }

    /*
     @return the game object for accessing the data types & subroutines/methods inside
     */
    public Game getGame() { return game; }
}
