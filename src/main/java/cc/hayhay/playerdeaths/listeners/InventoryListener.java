package cc.hayhay.playerdeaths.listeners;

import cc.hayhay.playerdeaths.commands.TopCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(TopCommand.inv)) return;
        event.setCancelled(true);
    }

}
