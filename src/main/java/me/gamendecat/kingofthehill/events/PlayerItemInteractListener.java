package me.gamendecat.kingofthehill.events;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.data.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerItemInteractListener implements Listener {

    private KingOfTheHill plugin;

    public PlayerItemInteractListener(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!e.hasItem()) return;
        if(!plugin.getSetupWizard().isInWizard(e.getPlayer())) return;
        if(e.getItem() == null) return;
        if(!e.getItem().hasItemMeta()) return;

        Player player = e.getPlayer();

        ItemStack itemStack = e.getItem();

        if(e.getClickedBlock() == null) return;

        Location clicked = e.getClickedBlock().getLocation();

        Map map = plugin.getSetupWizard().playerToGameMap.get(player);

        e.setCancelled(true);

        switch(Objects.requireNonNull(itemStack).getType()) {
            case DIAMOND:
                map.setMapSpawnLocation(clicked);
                player.sendMessage("§aSet Teleport Location");
                break;
            case EMERALD:
                map.setBottomCorner(clicked);
                player.sendMessage("§aSet Bottom Corner");
                break;
            case STICK:
                map.setTopCorner(clicked);
                player.sendMessage("§aSet Top Corner");
                break;
            case RED_MUSHROOM:
                plugin.getMapConfig().addMap(map);
                player.sendMessage("§aSaved Map");
                plugin.getSetupWizard().removeFromWizard(player);
                break;
        }
    }
}
