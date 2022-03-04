package me.gamendecat.kingofthehill.Managers;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.data.Map;
import me.gamendecat.kingofthehill.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetupWizardManager {

    public HashMap<Player, Map> playerToGameMap = new HashMap<>();

    private KingOfTheHill plugin;

    public SetupWizardManager(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    public boolean isInWizard(Player player) {
        return playerToGameMap.containsKey(player);
    }

    public void activateSetupWizard(Player player, Map map) {
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        worldSetupWizard(player, map);
    }

    public void worldSetupWizard(Player player, Map map) {
        player.getInventory().clear();

        player.getInventory().addItem(
                new ItemBuilder(Material.DIAMOND, 1, "&aSet Teleport Location").build()
        );

        player.getInventory().addItem(
                new ItemBuilder(Material.EMERALD, 1, "&aSet Bottom Corner").build()
        );
        player.getInventory().addItem(
                new ItemBuilder(Material.STICK, 1, "&aSet Top Corner").build()
        );
        player.getInventory().addItem(
                new ItemBuilder(Material.RED_MUSHROOM, 1,"&aSave Map").build()
        );

        player.setGameMode(GameMode.CREATIVE);
        playerToGameMap.put(player, map);
    }

    public void removeFromWizard(Player player) {
        player.teleport(new Location(Bukkit.getWorld("world"),0, 256, 0, -90, 3));
        playerToGameMap.remove(player);
        player.getInventory().clear();
    }
}
