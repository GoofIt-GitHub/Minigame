package com.matthew.plugin.main.game.listeners;

import com.matthew.plugin.main.GameState;
import com.matthew.plugin.main.apis.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {

    /**
     * Game functionality begins here:
     * Code in this class & package is custom to the specific game
     * - Remove the placeholder game below before continuing
     */

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if(GameManager.isPlaying(player) && GameManager.getArena(player).getState().equals(GameState.LIVE)) {
            player.sendMessage(ChatColor.GREEN + "+1 Point");

            GameManager.getArena(player).getGame().addPoint(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if(GameManager.isPlaying(player)) {
            GameManager.getArena(player).removePlayer(player);
        }
    }

}
