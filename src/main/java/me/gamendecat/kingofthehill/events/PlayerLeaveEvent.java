package me.gamendecat.kingofthehill.events;

import me.gamendecat.kingofthehill.KingOfTheHill;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveEvent implements Listener {

    private KingOfTheHill plugin;

    public PlayerLeaveEvent(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerLeaveListener(PlayerQuitEvent e) {
        if(plugin.getTimerTask() != null || plugin.getStartKoth() != null) {
            plugin.getStartedGame().removePlayer(e.getPlayer());
            return;
        }

        if(plugin.getStartedGame().getPlayingTask() != null) {
            plugin.getConfigFile().addPlayer(e.getPlayer());
        }
    }
}
