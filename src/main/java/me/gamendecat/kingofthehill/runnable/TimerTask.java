package me.gamendecat.kingofthehill.runnable;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.data.StartedGame;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerTask extends BukkitRunnable {

    private KingOfTheHill plugin;

    private int timeUntil;

    public TimerTask(KingOfTheHill plugin, int timeUntil) {
        this.plugin = plugin;
        this.timeUntil = timeUntil;
    }

    @Override
    public void run() {
        timeUntil = timeUntil - 30;

        if(timeUntil == 60) {
            plugin.setTimerTask(null);

            StartedGame game = new StartedGame(plugin);
            plugin.setStartedGame(game);
            cancel();
        }
    }

    public String getTimeUntil() {
        int hours = timeUntil / 3600;
        int minutes = (timeUntil % 3600) / 60;

        String format = (hours != 0 ? hours + "h " : "") + (minutes != 0 ? minutes + "m" : "");
        return format;
    }
}
