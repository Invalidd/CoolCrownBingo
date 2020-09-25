package com.invalid.CCCBingo;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;
    public static ConsoleCommandSender console;
    public static Server server;

    /**
     * Runs during the initialization of a server
     */
    public void onEnable() {
        plugin = this; //Saving the plugin class
        server = Bukkit.getServer(); //Gets the server
        console = Bukkit.getServer().getConsoleSender(); //Gets the console sender

        //Sending plugin enabled message to console.
        console.sendMessage(ChatColor.GREEN + "Cool Crown Bingo plugin enabled!");

        //Load/create config
        handleConfig();
        //Setting up listeners
        setupPlugin();
    }

    /**
     * Sets up the plugin by registering the commands and handlers
     */
    private void setupPlugin(){
        //Registering listeners
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
    }

    /**
     * Loads the config and saves it
    */
    private void handleConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
