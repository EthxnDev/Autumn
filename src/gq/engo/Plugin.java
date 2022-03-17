package gq.engo;

import gq.engo.commands.*;
import gq.engo.listeners.Listeners;
import gq.engo.listeners.prevention.*;
import gq.engo.utils.DiscordWebhook;
import gq.engo.utils.TPS;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    public static Plugin Instance;
    public DiscordWebhook discordWebhook = new DiscordWebhook(this, getConfig().getString("AlertSystem.WebhookURL"));


    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Autumn has Enabled!");
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getPluginManager().registerEvents(new Antillegal(), this);
        Bukkit.getPluginManager().registerEvents(new Redstone(), this);
        Bukkit.getPluginManager().registerEvents(new SandLag(), this);
        Bukkit.getPluginManager().registerEvents(new BookBan(), this);
        Bukkit.getPluginManager().registerEvents(new Elytra(), this);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPS(), 100L, 1L);

        Instance = this;

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.getCommand("autumn").setExecutor((CommandExecutor) new AutumnCommand());
        this.getCommand("kill").setExecutor((CommandExecutor) new KillCommand());
        this.getCommand("discord").setExecutor((CommandExecutor) new DiscordCommand());
        this.getCommand("getTPS").setExecutor((CommandExecutor) new TPSCommand());
        this.getCommand("help").setExecutor((CommandExecutor) new HelpCommand());
        this.getCommand("uniquejoins").setExecutor((CommandExecutor) new UniqueCommand());
        this.getCommand("say").setExecutor((CommandExecutor) new SayCommand());
        //this.getCommand("crash").setExecutor((CommandExecutor) new CrashCommand());

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Autumn has Disabled...");
    }
}
