package com.matthew.plugin.main.apis;

import com.matthew.plugin.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Config {

    /**
     * Check for the config and check the default & non default values
     *
     * @param main - instance of the main class that extends javaplugin
     */
    public Config(Main main) {
        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    /**
     * Get the amount of players required for the game to start
     *
     * @return required player amount for the game to begin- located under path in the config "required-players:"
     */
    public static int getRequiredPlayers() { return Main.getInstance().getConfig().getInt("required-players"); }

    /**
     * Get the amount of seconds the countdown starts at
     *
     * @return get the integer the countdown starts at- located under path in the config "countdown-seconds:"
     */
    public static int getCountdownSeconds() { return Main.getInstance().getConfig().getInt("countdown-seconds"); }

    /**
     * Get the location the lobby spawnpoint is in the config
     *
     * @return the location the player is sent to once they join the queue.
     */
    public static Location getLobbySpawn() {
        return new Location(
                Bukkit.getWorld(Main.getInstance().getConfig().getString("lobby-spawn.world")),
                Main.getInstance().getConfig().getDouble("lobby-spawn.x"),
                Main.getInstance().getConfig().getDouble("lobby-spawn.y"),
                Main.getInstance().getConfig().getDouble("lobby-spawn.z"),
                Main.getInstance().getConfig().getInt("lobby-spawn.yaw"),
                Main.getInstance().getConfig().getInt("lobby-spawn.pitch")
                );

    }

    /**
     * Get the spawnpoint for the specified arena
     *
     * @param id - id of a specific arena (Ids can be found in the config.yml)
     * @return the location the player is sent to once the game has started
     */
    public static Location getArenaSpawn(int id) {
        return new Location(
                Bukkit.getWorld(Main.getInstance().getConfig().getString("arenas." + id + ".world")),
                Main.getInstance().getConfig().getDouble("arenas." + id + ".x"),
                Main.getInstance().getConfig().getDouble("arenas." + id + ".y"),
                Main.getInstance().getConfig().getDouble("arenas." + id + ".z"),
                Main.getInstance().getConfig().getInt("arenas." + id + ".yaw"),
                Main.getInstance().getConfig().getInt("arenas." + id + ".pitch")
        );

    }

    /**
     * Get how many arenas there are in the config
     *
     * @return an integer referring to the amount of arenas specified in the config
     */
    public static int getArenaAmount() { return Main.getInstance().getConfig().getConfigurationSection("arenas.").getKeys(false).size(); }
}
