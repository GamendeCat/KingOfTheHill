package me.gamendecat.kingofthehill.runnable;

import me.gamendecat.kingofthehill.KingOfTheHill;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartingKoth extends BukkitRunnable {

    private KingOfTheHill plugin;
    private int timer = 60;

    public StartingKoth(KingOfTheHill plugin) {
        this.plugin = plugin;
        Bukkit.broadcastMessage(plugin.getConfigFile().getSixtySec());
    }

    @Override
    public void run() {
        timer--;
        if(timer == 0) {
            Bukkit.broadcastMessage(plugin.getConfigFile().getStartNow());
            PlayingTask task = new PlayingTask(plugin);
            task.runTaskTimer(plugin, 0, 10);
            plugin.getStartedGame().setPlayingTask(task);
            for(Player player : plugin.getStartedGame().getPlayers()) {
                player.teleport(plugin.getStartedGame().getMap().getMapSpawnLocation());
            }
            plugin.setStartKoth(null);
            cancel();
            return;
        }

        if(timer == 30) {
            Bukkit.broadcastMessage(plugin.getConfigFile().getThirtySec());
            return;
        }

        if(timer <= 5) {
            Bukkit.broadcastMessage(plugin.getConfigFile().getTimerSec().replaceAll("<timer>", String.valueOf(timer)));
        }
    }

    public int getTimer() {
        return timer;
    }
}
