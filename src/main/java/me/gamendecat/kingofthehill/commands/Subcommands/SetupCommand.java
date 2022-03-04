package me.gamendecat.kingofthehill.commands.Subcommands;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.commands.SubCommand;
import me.gamendecat.kingofthehill.data.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand extends SubCommand {

    private KingOfTheHill plugin;

    public SetupCommand(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getSub() {
        return "setup";
    }

    @Override
    public String getPermission() {
        return plugin.getConfigFile().getSetupPermission();
    }

    @Override
    public String getDescription() {
        return "Setup koth!";
    }

    @Override
    public String getSyntax() {
        return "/koth setup <name>";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You need to be a player!");
            return;
        }
        if(args.length == 0) {
            sender.sendMessage(getSyntax());
            return;
        }

        Map map = new Map(null, null, args[0], null);

        plugin.getSetupWizard().activateSetupWizard((Player) sender, map);
        sender.sendMessage("§aSetup map: " + args[0]);
    }
}
