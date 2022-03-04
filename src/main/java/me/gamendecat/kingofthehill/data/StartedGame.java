package me.gamendecat.kingofthehill.data;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.events.PlayerLeaveEvent;
import me.gamendecat.kingofthehill.runnable.PlayingTask;
import me.gamendecat.kingofthehill.runnable.StartingKoth;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartedGame {

    private Map map;
    private List<Player> players;
    private PlayingTask playingTask;
    private PlayerLeaveEvent leaveEvent;

    public StartedGame(Map map, KingOfTheHill plugin) {
        this.map = map;
        this.players = new ArrayList<>();
        StartingKoth start = new StartingKoth(plugin);

        plugin.setStartKoth(start);

        start.runTaskTimer(plugin, 0, 20 );

        this.leaveEvent = new PlayerLeaveEvent(plugin);
        plugin.getServer().getPluginManager().registerEvents(leaveEvent, plugin);
    }

    public StartedGame(KingOfTheHill plugin) {
        if(plugin.getMapConfig().getMaps().size() == 0) {
            Bukkit.getLogger().warning("No maps!");
            return;
        }
        this.players = new ArrayList<>();
        Random random = new Random();
        int i = random.nextInt(plugin.getMapConfig().getMaps().size());
        if(i != 0) {
            i--;
        }
        this.map = plugin.getMapConfig().getMaps().get(i);
        StartingKoth start = new StartingKoth(plugin);

        plugin.setStartKoth(start);

        start.runTaskTimer(plugin, 0, 20);
    }

    public PlayingTask getPlayingTask() {
        return playingTask;
    }

    public void setPlayingTask(PlayingTask playingTask) {
        this.playingTask = playingTask;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Map getMap() {
        return map;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public PlayerLeaveEvent getLeaveEvent() {
        return leaveEvent;
    }
}
