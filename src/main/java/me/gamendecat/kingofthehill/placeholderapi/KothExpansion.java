package me.gamendecat.kingofthehill.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.gamendecat.kingofthehill.KingOfTheHill;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KothExpansion extends PlaceholderExpansion {

    private KingOfTheHill plugin;
    private int timeNeededInSec;

    public KothExpansion(KingOfTheHill plugin) {
        this.plugin = plugin;
        timeNeededInSec = (plugin.getConfig().getInt("minutes") * 60 * 2);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "koth";
    }

    @Override
    public @NotNull String getAuthor() {
        return "GamendeCat";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("winning_player")){
            return plugin.getConfig().getString(plugin.getStartedGame().getPlayingTask().getWinningPlayer().getName(), "");
        }

        if(params.equalsIgnoreCase("time_left")) {
            int time = timeNeededInSec - plugin.getStartedGame().getPlayingTask().getTimerPlayer();
            return plugin.getConfig().getString(String.valueOf(time), "");
        }

        return null;
    }
}
