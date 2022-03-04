package me.gamendecat.kingofthehill.commands.Subcommands;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.commands.SubCommand;
import me.gamendecat.kingofthehill.data.Map;
import me.gamendecat.kingofthehill.data.StartedGame;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class StartCommand extends SubCommand {

    private KingOfTheHill plugin;

    public StartCommand(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getSub() {
        return "start";
    }

    @Override
    public String getPermission() {
        return plugin.getConfigFile().getSetupPermission();
    }

    @Override
    public String getDescription() {
        return "Start the koth!";
    }

    @Override
    public String getSyntax() {
        return "/koth start <map> (map is optional)";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(plugin.getMapConfig().getMaps().size() == 0) {
            sender.sendMessage(plugin.getConfigFile().getNoMap());
        }

        if(args.length != 0) {
            for(Map map : plugin.getMapConfig().getMaps()) {
                if(map.getName().equalsIgnoreCase(args[0])) {
                    plugin.newTimerTask(map);
                    Bukkit.broadcastMessage(plugin.getConfigFile().getStartGameMap().replaceAll("<map>", map.getName()));
                    return;
                }
                break;
            }
        }else {
            plugin.newTimerTask(null);
            Bukkit.broadcastMessage(plugin.getConfigFile().getStartGame());
        }
    }
}
