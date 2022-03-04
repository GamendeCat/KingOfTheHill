package me.gamendecat.kingofthehill.data;

import lombok.Getter;
import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.util.ItemBuilder;
import me.gamendecat.kingofthehill.util.LocationUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Config {

    //Permissions
    private String helpPermission;
    private String setupPermission;
    private String startPermission;
    private String noMap;
    private String startGameMap;
    private String startGame;
    private String openMenu;
    private String noPermission;
    private String noCommands;
    private String alreadyStart;
    private String alreadyIn;
    private String addToEvent;
    private String notInTime;
    private String currWinning;
    private String knockedOf;
    private String playerWon;
    private String sixtySec;
    private String startNow;
    private String thirtySec;
    private String timerSec;
    private List<String> commands;

    public void messagePerm() {
        helpPermission = config.getString("help-permission").replaceAll("&", "§");
        setupPermission = config.getString("setup-permission").replaceAll("&", "§");
        startPermission = config.getString("start-permission").replaceAll("&", "§");
        noMap = config.getString("no-map").replaceAll("&", "§");
        startGameMap = config.getString("start-game-map").replaceAll("&", "§");
        startGame = config.getString("start-game").replaceAll("&", "§");
        openMenu = config.getString("open-menu").replaceAll("&", "§");
        noPermission = config.getString("no-permission").replaceAll("&", "§");
        noCommands = config.getString("no-commands").replaceAll("&", "§");
        alreadyStart = config.getString("already-started").replaceAll("&", "§");
        alreadyIn = config.getString("already-in").replaceAll("&", "§");
        addToEvent = config.getString("add-to-event").replaceAll("&", "§");
        notInTime = config.getString("not-in-time").replaceAll("&", "§");
        knockedOf = config.getString("knocked-off").replaceAll("&", "§");
        currWinning = config.getString("currently-winning").replaceAll("&", "§");
        playerWon = config.getString("player-won").replaceAll("&", "§");
        sixtySec = config.getString("sixty-seconds").replaceAll("&", "§");
        startNow = config.getString("starting-now").replaceAll("&", "§");
        thirtySec = config.getString("thirty-seconds").replaceAll("&", "§");
        timerSec = config.getString("timer-seconds").replaceAll("&", "§");
        commands = config.getStringList("command-rewards");
    }


    private static FileConfiguration config;
    private Location spawnLocation;


    public Config(KingOfTheHill plugin) {
        config = plugin.getConfig();
        this.plugin = plugin;
    }

    private static int timer;

    private KingOfTheHill plugin;

    private ItemStack menuActive;
    private ItemStack menuPrepare;
    private ItemStack menuNotActive;
    private List<String> lorePrepare;
    private List<String> loreNotActive;

    public void getValues() {
        playerQuit = config.getStringList("playerQuit");
        spawnLocation = LocationUtility.read(config.getConfigurationSection("spawn"));
        timer = config.getInt("time");
        ConfigurationSection section = config.getConfigurationSection("Active");
        menuActive = getItem(section);
        ConfigurationSection section1 = config.getConfigurationSection("Not-Active");
        menuNotActive = getItem(section1);
        ConfigurationSection section2 = config.getConfigurationSection("prepare");
        menuPrepare = getItem(section2);
        lorePrepare = getLore(section2.getStringList("lore"));
        loreNotActive = getLore(section1.getStringList("lore"));
    }

    public List<String> getLore(List<String> lines) {
        List<String> lore = new ArrayList<>();
        for(String line : lines) {
            lore.add(line.replaceAll("&", "§"));
        }
        return lore;
    }

    public ItemStack getItem(ConfigurationSection section) {
        String displayName = section.getString("displayName").replaceAll("&", "§");
        List<String> lore = getLore(section.getStringList("lore"));
        boolean glow = section.getBoolean("glow");
        String type = section.getString("type");


        return itemInmenu(Material.getMaterial(type), displayName, glow, lore);
    }

    public ItemStack itemInmenu(Material material, String displayName, boolean glow, List<String> lore) {
        if(glow) {
           return new ItemBuilder(material, 1).displayname(displayName).lore(lore).glow().build();
        }else {
            return new ItemBuilder(material, 1).displayname(displayName).lore(lore).build();
        }
    }

    public static int getTimer() {
        return timer;
    }

    public ItemStack getMenuActive() {
        return menuActive;
    }

    public ItemStack getMenuPrepare() {
        return getSomethingIdk(menuPrepare, true);
    }

    public ItemStack getMenuNotActive() {
        return getSomethingIdk(menuNotActive, false);
    }

    private ItemStack getSomethingIdk(ItemStack itemstack, boolean lore) {
        List<String> newLore = new ArrayList<>();
        for(String string : (lore ? lorePrepare : loreNotActive)) {
            if(plugin.getTimerTask() != null) {
                newLore.add(string.replaceAll("<time>", plugin.getTimerTask().getTimeUntil()));
            }else{
                newLore.add(string.replaceAll("<time>", plugin.getStartKoth().getTimer() + "s"));
            }
        }

        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.setLore(newLore);
        itemstack.setItemMeta(itemMeta);

        return itemstack;
    }

    private List<String> playerQuit;

    public void addPlayer(Player player) {
        playerQuit.add(String.valueOf(player.getUniqueId()));

        config.set("playerQuit", playerQuit);
        plugin.saveConfig();
    }

    public void removePlayer(Player player){
        for(String uuid : playerQuit) {
            if(uuid.equals(player.getUniqueId())) {
                playerQuit.remove(player.getUniqueId());

                config.set("playerQuit", playerQuit);
                plugin.saveConfig();
            }
        }
    }
}
