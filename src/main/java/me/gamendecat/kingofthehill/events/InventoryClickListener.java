package me.gamendecat.kingofthehill.events;

import me.gamendecat.kingofthehill.GUI.GUI;
import me.gamendecat.kingofthehill.KingOfTheHill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private KingOfTheHill plugin;

    public InventoryClickListener(KingOfTheHill plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
        if(!e.getCurrentItem().hasItemMeta()) return;
        Player player = (Player) e.getWhoClicked();

        GUI gui = plugin.getGui().getOpenGUI(player);

        if(gui == null) return;
        e.setCancelled(true);

        GUI newGUI = gui.handleClick(player, e.getCurrentItem(), e.getView());

        if(newGUI != null) {
            plugin.getGui().setGUI(player, newGUI);
        }
    }
}
