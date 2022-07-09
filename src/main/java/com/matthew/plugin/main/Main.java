package com.matthew.plugin.main;

import com.matthew.plugin.main.apis.Config;
import com.matthew.plugin.main.apis.GameManager;
import com.matthew.plugin.main.game.commands.ArenaCommand;
import com.matthew.plugin.main.game.listeners.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        Main.instance = this;

        // Must run first
        runConstructors();

        registerListeners();
        registerCommands();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
    }

    private void registerCommands() {
        getCommand("game").setExecutor(new ArenaCommand());
    }

    private void runConstructors() {
        new Config(this);
        new GameManager();
    }

    public static Main getInstance() { return instance; }

}
