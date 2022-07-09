package com.matthew.plugin.main.apis;

import com.matthew.plugin.main.GameState;
import com.matthew.plugin.main.apis.arena.GameArena;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static ArrayList<GameArena> gameArenas;

    /**
     * Construct the game manager that holds all of the current game arenas. (amount of arenas to add is specified in the config)
     */
    public GameManager() {

        gameArenas = new ArrayList<>();

        for(int i = 0; i <= (Config.getArenaAmount() - 1); i++) {
            gameArenas.add(new GameArena(i));
        }
    }

    /**
     * Get the list of game arenas currently added to the arraylist
     *
     * @return the gameArenas arraylist
     */
    public static List<GameArena> getArenas() { return gameArenas; }

    /**
     * Check if a player is currently playing or in any game arena
     *
     * @param player - player to be checked for if they are in a game or not
     * @return true if the player is currently in a game, else return false
     */
    public static boolean isPlaying(Player player) {
        for(GameArena gameArena : gameArenas) {
            if(gameArena.getPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the arena that a player is currently in
     *
     * @param player - player that is being checked for what arena they are currently in
     * @return the GameArena that the player is currently in
     */
    public static GameArena getArena(Player player) {
        for(GameArena gameArena : gameArenas) {
            if(gameArena.getPlayers().contains(player.getUniqueId())) {
                return gameArena;
            }
        }
        return null;
    }

    /**
     * Get a GameArena based on the arenas id (list of ids can be found in the config.yml)
     *
     * @param id - id for the gamearena
     * @return the gamearena that corresponds to the arena id
     */
    public static GameArena getArena(int id) {
        for(GameArena gameArena : gameArenas) {
            if(gameArena.getID() == id) {
                return gameArena;
            }
        }
        return null;
    }


    /**
     * Check if a arena is currently looking for players
     *
     * @param id - id of the arena to be checked (list of ids can be found in the config.yml)
     * @return true if the arena is currently recruiting players, else return false
     */
    public static boolean isRecruiting(int id) { return getArena(id).getState() == GameState.RECRUITING; }

}
