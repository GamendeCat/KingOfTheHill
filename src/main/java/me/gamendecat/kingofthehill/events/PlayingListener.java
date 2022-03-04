package me.gamendecat.kingofthehill.events;

import me.gamendecat.kingofthehill.KingOfTheHill;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayingListener implements Listener {

    private KingOfTheHill plugin;

    public PlayingListener(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        for(Player player : plugin.getStartedGame().getPlayers()) {
            if(player == e.getPlayer()) {
                e.setCancelled(true);
                player.sendMessage(plugin.getConfigFile().getNoCommands());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        for(Player player : plugin.getStartedGame().getPlayers()) {
            if(player == e.getPlayer()) {
                plugin.getStartedGame().removePlayer(player);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;

        if(e.getCurrentItem().getType() == Material.BOOK) {
            for(Player player : plugin.getStartedGame().getPlayers()) {
                if(player == e.getWhoClicked()) {
                    player.teleport(plugin.getStartedGame().getMap().getMapSpawnLocation());
                }
            }
        }
    }
}
