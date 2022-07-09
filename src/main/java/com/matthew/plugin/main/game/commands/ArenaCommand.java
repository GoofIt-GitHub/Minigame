package com.matthew.plugin.main.game.commands;

import com.matthew.plugin.main.apis.Config;
import com.matthew.plugin.main.apis.GameManager;
import com.matthew.plugin.main.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length != 0) {
                switch (args[0]) {
                    case "list":
                        if (args.length == 1) {
                            MessageUtils.availableArenas(player);
                        } else {
                            MessageUtils.incorrectUsage(player);
                        }
                        break;
                    case "leave":
                        if (args.length == 1) {
                            if (GameManager.isPlaying(player)) {
                                GameManager.getArena(player).removePlayer(player);

                                player.sendMessage(ChatColor.AQUA + "You have left the arena.");
                            } else {
                                player.sendMessage(ChatColor.RED + "You are not currently in an arena.");
                            }
                        } else {
                            MessageUtils.incorrectUsage(player);
                        }
                        break;
                    case "join":
                        if (args.length == 2) {
                            try {
                                int id = Integer.parseInt(args[1]);

                                if (id >= 0 && id <= Config.getArenaAmount() - 1) {
                                    if (GameManager.isRecruiting(id)) {
                                        if (!GameManager.isPlaying(player)) {
                                            GameManager.getArena(id).addPlayer(player);
                                            player.sendMessage(ChatColor.GREEN + "You have joined arena: " + id);
                                        } else {
                                            player.sendMessage(ChatColor.RED + "You are currently in an arena.");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Game has already started, please wait.");
                                    }
                                } else {
                                    MessageUtils.invalidArena(player);
                                }
                            } catch (NumberFormatException e) {
                                MessageUtils.invalidArena(player);
                            }
                        } else {
                            MessageUtils.incorrectUsage(player);
                        }
                        break;
                    case "start":
                        if (args.length == 1) {
                            if (GameManager.isPlaying(player)) {
                                GameManager.getArena(player).sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + player.getName() + " has started the game");
                                GameManager.getArena(player).forceStart();
                            } else {
                                MessageUtils.notInAGame(player);
                            }
                        } else {
                            MessageUtils.incorrectUsage(player);
                        }
                        break;
                    case "stop":
                        if (args.length == 1) {
                            if (GameManager.isPlaying(player)) {
                                GameManager.getArena(player).sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + player.getName() + " has stopped the game");
                                GameManager.getArena(player).reset();
                            } else {
                                MessageUtils.notInAGame(player);
                            }
                            break;
                        } else {
                            MessageUtils.incorrectUsage(player);
                        }
                }
            } else {
                MessageUtils.incorrectUsage(player);
            }

        } else {
            System.out.println("This command may not be used from the console");
        }

        return false;
    }
}
