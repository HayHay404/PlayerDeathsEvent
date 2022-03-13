package cc.hayhay.playerdeaths.listeners;

import cc.hayhay.playerdeaths.Globals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFreezeListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Globals.isEventStarting) {
            event.setCancelled(true);
        }
    }

}
