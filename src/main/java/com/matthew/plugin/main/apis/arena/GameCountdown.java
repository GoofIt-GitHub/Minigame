package com.matthew.plugin.main.apis.arena;

import com.matthew.plugin.main.GameState;
import com.matthew.plugin.main.Main;
import com.matthew.plugin.main.apis.Config;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdown extends BukkitRunnable {


    private GameArena gameArena;
    private int seconds;

    /**
     * Construct the countdown for the specified game arena
     *
     * @param gameArena - game arena that is going to begin the countdown
     */
    public GameCountdown(GameArena gameArena) {

        this.gameArena = gameArena;
        this.seconds = Config.getCountdownSeconds();
    }

    /**
     * Begin the countdown
     */
    public void begin() {

        gameArena.setState(GameState.COUNTDOWN);
        this.runTaskTimer(Main.getInstance(), 0, 20);
    }

    /**
     * bukkitrunnable that runs every 20 ticks (one second). at 30 seconds and all numbers <=10 seconds
     * announce the time in chat. Once seconds hits 0 cancel the runnable and run the arena start method
     * If the size during the countdown goes lower than the required players count, then cancel the
     * countdown and set the gamestate back to recruiting.
     *
     * Additionally, if at any point the gamestate changes during the countdown the runnable will end
     */
    @Override
    public void run() {
        if(seconds == 0 && gameArena.getState().equals(GameState.COUNTDOWN)) {
            cancel();
            gameArena.start();
            return;
        }

        if(gameArena.getState().equals(GameState.COUNTDOWN)) {
            if (seconds % 30 == 0 || seconds <= 10) {
                if (seconds == 1) {
                    gameArena.sendMessage(ChatColor.AQUA + "Game will start in 1 second.");
                } else {
                    gameArena.sendMessage(ChatColor.AQUA + "Game will start in " + seconds + " seconds.");
                }
            }
        } else {
            cancel();
            return;
        }

        if(gameArena.getPlayers().size() < Config.getRequiredPlayers()) {
            cancel();
            gameArena.setRecruiting();
            gameArena.sendMessage(ChatColor.RED + "There are not enough players to begin.");
            return;
        }

        seconds--;
    }
}
