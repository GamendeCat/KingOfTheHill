package me.gamendecat.kingofthehill.runnable;

import me.clip.placeholderapi.PlaceholderAPI;
import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.data.StartedGame;
import me.gamendecat.kingofthehill.events.PlayingListener;
import me.gamendecat.kingofthehill.util.LocationUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayingTask extends BukkitRunnable {

    private KingOfTheHill plugin;
    private int timeNeededInSec;
    private int timeNeededToStop;
    private Player winningPlayer;
    private StartedGame game;
    private PlayingListener event;

    public PlayingTask(KingOfTheHill plugin) {
        this.plugin = plugin;
        // * 60 for minutes and times 2 bc this task runs every 2 seconds
        timeNeededInSec = (plugin.getConfig().getInt("minutes") * 60 * 2);
        timeNeededToStop = (plugin.getConfig().getInt("stop") * 60 * 2);
        game = plugin.getStartedGame();
        event = new PlayingListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(event, plugin);
    }

    private int timerUntilStop = 0;
    private int timerPlayer = 0;

    @Override
    public void run() {
        timerUntilStop++;
        if(timerUntilStop >= timeNeededToStop) {
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(null,plugin.getConfigFile().getNotInTime()));
            cancel();
        }

        if(winningPlayer == null) {
            for(Player player : game.getPlayers()) {
                if(LocationUtility.playerIsBetween(player.getLocation(), game.getMap().getTopCorner(), game.getMap().getBottomCorner())) {
                    Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(null,plugin.getConfigFile().getCurrWinning().replaceAll("<player>", player.getName())));
                    winningPlayer = player;
                    return;
                }
            }
            return;
        }

        if(!LocationUtility.playerIsBetween(winningPlayer.getLocation(), game.getMap().getTopCorner(), game.getMap().getBottomCorner())) {
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(null, plugin.getConfigFile().getKnockedOf().replaceAll("<player>", winningPlayer.getName())));
            winningPlayer = null;
            timerPlayer = 0;
            return;
        }

        if(timerPlayer >= timeNeededInSec) {
            Bukkit.broadcastMessage(plugin.getConfigFile().getPlayerWon().replaceAll("<player>", winningPlayer.getName()));
            for(String command : plugin.getConfigFile().getCommands()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("<player>", winningPlayer.getName()));
            }
            for(Player player : plugin.getStartedGame().getPlayers()) {
                if(!player.isOnline()) continue;
                player.teleport(plugin.getConfigFile().getSpawnLocation());
            }
            cancel();
        }

        timerPlayer++;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        InventoryClickEvent.getHandlerList().unregister(event);
        PlayerQuitEvent.getHandlerList().unregister(event);
        PlayerCommandPreprocessEvent.getHandlerList().unregister(event);
        PlayerQuitEvent.getHandlerList().unregister(plugin.getStartedGame().getLeaveEvent());
        plugin.newTimerTask();
        super.cancel();
    }

    public Player getWinningPlayer() {
        return winningPlayer;
    }

    public int getTimerPlayer() {
        return timerPlayer;
    }
}
