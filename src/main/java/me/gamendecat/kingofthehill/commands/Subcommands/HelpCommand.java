package me.gamendecat.kingofthehill.commands.Subcommands;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    private KingOfTheHill plugin;

    public HelpCommand(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getSub() {
        return "help";
    }

    @Override
    public String getPermission() {
        return plugin.getConfigFile().getHelpPermission();
    }

    @Override
    public String getDescription() {
        return "List all subcommands and it's descriptions";
    }

    @Override
    public String getSyntax() {
        return "usage: /koth help";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append("§cKoth Commands: \n");
        SubCommand.getCommands().forEach((sub, command) -> {
            if(command != null) {
                if(sender.hasPermission(command.getPermission())) {
                    String commandHelp = plugin.getConfig().getString("commandHelpList")
                            .replace("<command>", command.getSub())
                            .replace("<permission>", command.getPermission())
                            .replace("<description>", command.getDescription())
                            .replace("<usage>", command.getSyntax());
                    builder.append("§7" +  commandHelp + "\n");
                }
            }
        });

        sender.sendMessage(builder.toString());
    }
}
