package me.gamendecat.kingofthehill.events;

import me.gamendecat.kingofthehill.KingOfTheHill;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    private KingOfTheHill plugin;

    public PlayerJoinListener(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerLeaveListener(PlayerQuitEvent e) {
        plugin.getConfigFile().removePlayer(e.getPlayer());
    }
}
