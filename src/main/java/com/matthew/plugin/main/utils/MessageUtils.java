package com.matthew.plugin.main.utils;

import com.matthew.plugin.main.apis.arena.GameArena;
import com.matthew.plugin.main.apis.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void incorrectUsage(Player player) {
        player.sendMessage(ChatColor.RED + "Please use: ");
        player.sendMessage(ChatColor.RED + "- /game list");
        player.sendMessage(ChatColor.RED + "- /game leave");
        player.sendMessage(ChatColor.RED + "- /game join (arenaID)");
        player.sendMessage(ChatColor.DARK_RED + "- /game start");
        player.sendMessage(ChatColor.DARK_RED + "- /game stop");
    }

    public static void availableArenas(Player player) {
        player.sendMessage(ChatColor.AQUA + "Available arenas:");
        for (GameArena gameArena : GameManager.getArenas()) {
            player.sendMessage(ChatColor.AQUA + "-" + gameArena.getID());
        }
    }

    public static void notInAGame(Player player) {
        player.sendMessage(ChatColor.RED + "You are currently not in a game.");
    }

    public static void invalidArena(Player player) {
        player.sendMessage(ChatColor.RED + "Invalid game arena.");
    }
}
