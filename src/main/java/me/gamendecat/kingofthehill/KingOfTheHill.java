package me.gamendecat.kingofthehill;

import me.clip.placeholderapi.PlaceholderAPI;
import me.gamendecat.kingofthehill.GUI.GUIManager;
import me.gamendecat.kingofthehill.Managers.SetupWizardManager;
import me.gamendecat.kingofthehill.commands.Koth;
import me.gamendecat.kingofthehill.data.Config;
import me.gamendecat.kingofthehill.data.Map;
import me.gamendecat.kingofthehill.data.MapConfiguration;
import me.gamendecat.kingofthehill.data.StartedGame;
import me.gamendecat.kingofthehill.events.InventoryClickListener;
import me.gamendecat.kingofthehill.events.PlayerItemInteractListener;
import me.gamendecat.kingofthehill.events.PlayerJoinListener;
import me.gamendecat.kingofthehill.placeholderapi.KothExpansion;
import me.gamendecat.kingofthehill.runnable.StartingKoth;
import me.gamendecat.kingofthehill.runnable.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KingOfTheHill extends JavaPlugin {

    private StartedGame startedGame;
    private Config configFile;
    private GUIManager gui;
    private SetupWizardManager setupWizard;
    private MapConfiguration mapConfig;
    private TimerTask timerTask;
    private StartingKoth startKoth;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new KothExpansion(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        // Configuration Loading
        saveDefaultConfig();

        mapConfig = new MapConfiguration(this);
        configFile = new Config(this);
        configFile.getValues();
        configFile.messagePerm();
        gui = new GUIManager();
        setupWizard = new SetupWizardManager(this);

        //Startup Message
        Bukkit.getLogger().info("King Of The Hill started up!");

        // Events
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerItemInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        // Commands
        getCommand("koth").setExecutor(new Koth(this));

        if(mapConfig.getMaps().size() != 0) {
            newTimerTask();
        }
    }

    @Override
    public void onDisable() {
        // Shutdown Message
        Bukkit.getLogger().info("King Of The Hill shut down!");
    }

    public StartedGame getStartedGame() {
        return startedGame;
    }

    public void setStartedGame(StartedGame startedGame) {
        this.startedGame = startedGame;
    }

    public Config getConfigFile() {
        return configFile;
    }

    public GUIManager getGui() {
        return gui;
    }

    public SetupWizardManager getSetupWizard() {
        return setupWizard;
    }

    public MapConfiguration getMapConfig() {
        return mapConfig;
    }

    public void newTimerTask() {
       int amountOfSeconds = configFile.getTimer() * 60;
       this.timerTask = new TimerTask(this, amountOfSeconds);
       this.timerTask.runTaskTimer(this, 20 * 30, 20 * 30);
    }

    public void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public void newTimerTask(Map map) {
        this.timerTask.cancel();
        this.timerTask = null;
        if(map != null) {
            this.startedGame = new StartedGame(map, this);
            return;
        }
        this.startedGame = new StartedGame(this);
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public void setStartKoth(StartingKoth startKoth) {
        this.startKoth = startKoth;
    }

    public StartingKoth getStartKoth() {
        return startKoth;
    }
}
