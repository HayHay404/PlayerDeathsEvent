package cc.hayhay.playerdeaths.listeners;

import cc.hayhay.playerdeaths.Globals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Globals.isEventRunning) {
            if (!Globals.playerEventList.containsKey(event.getPlayer()))
                Globals.playerEventList.put(event.getPlayer(), 0.0);
        }
    }
}
