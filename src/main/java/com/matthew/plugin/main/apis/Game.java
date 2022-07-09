package com.matthew.plugin.main.apis;

import com.matthew.plugin.main.GameState;
import com.matthew.plugin.main.apis.arena.GameArena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * This Class should be custom to your game
 */

public class Game {

    private GameArena gameArena;
    private HashMap<UUID, Integer> points;
    private Location gameSpawn;

    /**
     * Construct the minigame/game that is going to be played
     *
     * @param gameArena - the arena that will be used for the game (each game contains multiple arenas)
     */
    public Game(GameArena gameArena) {
        this.gameSpawn = Config.getArenaSpawn(gameArena.getID());
        this.gameArena = gameArena;
        this.points = new HashMap<>();
    }

    /**
     * Start the game
     */
    public void start() {
        gameArena.setState(GameState.LIVE);

        gameArena.sendMessage(ChatColor.YELLOW + "Game has started");

        for(UUID uuid: gameArena.getPlayers()) {
            points.put(uuid, 0);
            Bukkit.getPlayer(uuid).teleport(gameSpawn);

        }
    }

    /**
     * Start the game before the countdown ends
     */
    public void gameForceStart() {
        gameArena.setState(GameState.LIVE);

        for(UUID uuid: gameArena.getPlayers()) {
            points.put(uuid, 0);
            Bukkit.getPlayer(uuid).teleport(gameSpawn);

        }
    }

    /**
     * Add a point to the player that is currently playing the game. After adding a point check if the player
     * is at 20 points, and if they are then end the game by resetting the game arena.
     *
     * @param player - player to add the point to
     */
    public void addPoint(Player player) {
        int p = points.get(player.getUniqueId()) + 1;

        if(p == 20) {
            gameArena.sendMessage(ChatColor.GOLD + player.getName() + " has won!");

            gameArena.reset();
            return;
        }
        points.replace(player.getUniqueId(), p);
    }

}
