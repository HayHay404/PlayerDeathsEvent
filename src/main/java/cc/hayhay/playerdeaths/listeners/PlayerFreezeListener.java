package cc.hayhay.playerdeaths.listeners;

import cc.hayhay.playerdeaths.Globals;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFreezeListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Globals.isEventStarting && !event.getPlayer().hasPermission("playerDeaths.bypassMove")) {
            event.setCancelled(true);
            event.getPlayer().sendActionBar(Component.text("Event is starting, you can't move!", NamedTextColor.RED));
        }
    }

}
