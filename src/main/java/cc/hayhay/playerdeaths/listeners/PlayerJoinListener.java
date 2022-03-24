package cc.hayhay.playerdeaths.listeners;

import cc.hayhay.playerdeaths.Globals;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // If player gets disconnected or leaves, then they'll come back as spectator.
        if (Globals.isEventRunning) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}
