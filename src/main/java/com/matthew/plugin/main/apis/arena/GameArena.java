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

    private int id;
    private ArrayList<UUID> players;
    private Location spawn;
    private GameState state;
    private GameCountdown gameCountdown;
    private Game game;

    /**
     * Construct a new GameArena with an id specific to the arena
     * Once a game arena is constructed it is to be set to recruiting by default
     *
     * @param id - id referring to the arena
     */
    public GameArena(int id) {
        this.id = id;
        players = new ArrayList<>();
        spawn = Config.getArenaSpawn(id);
        state = GameState.RECRUITING;
        gameCountdown = new GameCountdown(this);
        game = new Game(this);
    }


    /**
     * Get the id of the game arena
     *
     * @return the id of the game arena
     */
    public int getID() { return id; }

    /**
     * Get the players that are currently in the game's arena
     *
     * @return the list of players in the game arena
     */
    public List<UUID> getPlayers() { return players; }

    /**
     * Start the game by calling the start method located in the Game class
     */
    public void start() {
        game.start();
    }

    /**
     * Start the game before the countdown ends by using the gameForceStart method located in the Game class
     */
    public void forceStart() { game.gameForceStart(); }

    /**
     * basic logic to reset the arena by
     * - Teleporting all players in the players list to the lobbyspawn
     * - resetting the gamestate for the arena to recruiting so players can join again
     * - clearing the arraylist (which should have already been cleared before this point)
     * - recalling the constructors of countdown and game to reset as well
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

    /**
      * Set the arena back to recruiting by reinitializing the game countdown and game class to set the game back to recruiting without
      * errors
     */
    public void setRecruiting() {
        state = GameState.RECRUITING;
        gameCountdown = new GameCountdown(this);
        game = new Game(this);
    }

    /**
     * Send a message to everyone in the game arena
     * @param message - message to display in chat to the players
     */
    public void sendMessage(String message) {
        for(UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    /**
     * Add a player to the arena by adding a player to the players arraylist > teleport them to the arena spawn > send a message
     * to the other players in the arraylist that the specified player has joined >
     * run the countdown begin method if the minimum player count to begin the countdown has
     * been reached
     *
     * @param player - player that is being added to the game arena
     */
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);
        sendMessage(ChatColor.DARK_GRAY + "Join> " + ChatColor.GRAY + player.getName());

        if(players.size() >= Config.getRequiredPlayers()) {
            gameCountdown.begin();
        }
    }

    /**
     * Remove a player from the game arena:
     * - remove a player from the players arraylist > teleport them back to the lobby spawn > announce to
     *   the other players in the arraylist that the specified player has quit
     * - if the player size ends up becoming less than the required players amount for a countdown to start
     *   then run the reset method to set the game back to recruiting and look for more players
     * - if the game is live and all of the players decide to quit reset the game back to recruiting
     *   and look for more players
     *
     * @param player - player that is being removed from the arena
     */
    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(Config.getLobbySpawn());
        sendMessage(ChatColor.DARK_GRAY + "Quit> " + ChatColor.GRAY + player.getName());

        if(players.size() == 0 && state.equals(GameState.LIVE)) {
            reset();
        }
    }

    /**
     * Get the state the game is currently in [Live, countdown, recruiting]
     *
     * @return the gamestate of the arena
     */
    public GameState getState() { return state; }

    /**
     * Set the game state to Live, countdown, or recruiting
     *
     * @param state - state the game arena is to be set to
     */
    public void setState(GameState state) { this.state = state; }

    /**
     * Get the game specific to the arena
     *
     * @return the game
     */
    public Game getGame() { return game; }
}
