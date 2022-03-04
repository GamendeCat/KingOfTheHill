package me.gamendecat.kingofthehill.GUI;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class JoinMenu implements GUI {

    private KingOfTheHill plugin;

    public JoinMenu(KingOfTheHill plugin) {
        this.plugin = plugin;

        inv = Bukkit.createInventory(null, 27, "§cKoth");
        setupInventory();
    }

    public void setupInventory() {
        ItemStack itemStack = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).displayname("§8").build();
        for(int i = 0; i < inv.getSize(); i++) {
            if(i == 13) {
                if(plugin.getStartedGame() == null) {
                    inv.setItem(13, plugin.getConfigFile().getMenuNotActive());
                }else{
                    if(plugin.getStartedGame().getPlayingTask() != null) {
                        inv.setItem(13, plugin.getConfigFile().getMenuActive());
                        return;
                    }
                    inv.setItem(13, plugin.getConfigFile().getMenuPrepare());
                }
                continue;
            }
            inv.setItem(i, itemStack);
        }
    }

    private Inventory inv;

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public GUI handleClick(Player player, ItemStack itemstack, InventoryView view) {
        if(itemstack == null) return null;
        if(itemstack.getType() == Material.BARRIER) {
            player.closeInventory();
            return null;
        }

        if(itemstack.getType() == Material.BOOK) {
            if(plugin.getStartedGame() == null) {
                //timer check + tell him how long till it starts
            }else{
                if(plugin.getStartedGame().getPlayingTask() != null) {
                    if(plugin.getStartedGame().getPlayers().contains(player)) {
                        player.teleport(plugin.getStartedGame().getMap().getMapSpawnLocation());
                        return null;
                    }else{
                        player.sendMessage(plugin.getConfigFile().getAlreadyStart());
                        plugin.getGui().setGUI(player, null);
                    }
                }
                if(plugin.getStartedGame().getPlayers().contains(player)) {
                    player.sendMessage(plugin.getConfigFile().getAlreadyIn());
                    return null;
                }

                plugin.getStartedGame().addPlayer(player);
                player.sendMessage(plugin.getConfigFile().getAddToEvent());
            }
        }
        return null;
    }

    @Override
    public boolean isInventory(InventoryView view) {
        return view.getTitle().equals(getName());
    }
}
