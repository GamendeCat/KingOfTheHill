package me.gamendecat.kingofthehill.commands;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.commands.Subcommands.HelpCommand;
import me.gamendecat.kingofthehill.commands.Subcommands.SetupCommand;
import me.gamendecat.kingofthehill.commands.Subcommands.StartCommand;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public abstract class SubCommand {

    private static HashMap<String, SubCommand> commands;

    public abstract String getSub();
    public abstract String getPermission();
    public abstract String getDescription();
    public abstract String getSyntax();

    public abstract void onCommand(CommandSender sender, String[] args);

    public static void loadCommands(KingOfTheHill plugin) {
        commands = new HashMap<>();

        loadCommand(new HelpCommand(plugin), new SetupCommand(plugin), new StartCommand(plugin));
    }

    private static void loadCommand(SubCommand ... subs) {
        for (SubCommand sub: subs) {
            commands.put(sub.getSub(), sub);
            Koth.commands.add(sub.getSub());
        }
    }

    public static HashMap<String, SubCommand> getCommands() {
        return commands;
    }
}
