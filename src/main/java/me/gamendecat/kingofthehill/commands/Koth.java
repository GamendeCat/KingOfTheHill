package me.gamendecat.kingofthehill.commands;

import me.gamendecat.kingofthehill.GUI.JoinMenu;
import me.gamendecat.kingofthehill.KingOfTheHill;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Koth implements CommandExecutor, TabCompleter {

    public static List<String> commands = new ArrayList<>();
    private KingOfTheHill plugin;

    public Koth(KingOfTheHill plugin) {
        SubCommand.loadCommands(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(args.length == 0) {
            plugin.getGui().setGUI(player,  new JoinMenu(plugin));
            player.sendMessage(plugin.getConfigFile().getOpenMenu());
            return true;
        }

        String command = args[0];
        if (commands.contains(command)) {
            SubCommand subCommand = SubCommand.getCommands().get(command);
            if (subCommand.getPermission() != null) {
                if (!sender.hasPermission(subCommand.getPermission())) {
                    plugin.getCommand(plugin.getConfigFile().getNoPermission());
                    return true;
                }
            }
            subCommand.onCommand(sender, Arrays.copyOfRange(args,1, args.length));
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
