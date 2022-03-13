package cc.hayhay.playerdeaths;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TopCmdListener implements Listener {
    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(TopCmd.inv)) return;
        event.setCancelled(true);
    }
}
