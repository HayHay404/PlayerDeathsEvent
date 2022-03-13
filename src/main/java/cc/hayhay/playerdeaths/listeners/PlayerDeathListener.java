package cc.hayhay.playerdeaths.listeners;

import cc.hayhay.playerdeaths.Globals;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerDeathListener implements Listener {
    int count = 0;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (!Globals.isEventRunning) return;

        Component deathMsg = event.deathMessage();
        Player p = event.getPlayer();
        Double val = Globals.playerEventList.get(p);
        double multiplier = 5.0;

        if (isUniqueDeath(deathMsg, p)) {
            Globals.playerEventList.put(p, val + multiplier);
            p.sendMessage("§6§lGained 5 points!");
        } else {
            System.out.println(count);
            switch (count) {
                case 1:
                    Globals.playerEventList.put(p, val + (multiplier * .5));
                    p.sendMessage(Component.text("Gained " + (multiplier * .5) + " points!", NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true));
                    break;
                case 2:
                    Globals.playerEventList.put(p, val + (multiplier * .25));
                    p.sendMessage(Component.text("Gained " + (multiplier * .25) + " points!", NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true));
                    break;
                default:
                    Globals.playerEventList.put(p, val + (multiplier * .1));
                    p.sendMessage(Component.text("Gained " + (multiplier * .1) + " points!", NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true));
                    break;
            }
        }

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 29);
    }

    private boolean isUniqueDeath(Component deathMsg, Player p) {
        AtomicBoolean isUnique = new AtomicBoolean(true);
        ArrayList<Component> arr = new ArrayList<>();
        if (Globals.playerDeaths.get(p) != null) {
            arr = Globals.playerDeaths.get(p);
        }

        count = 0;

        for (Component s : arr) {
            if (s.equals(deathMsg)) {
                count += 1;
                isUnique.set(false);
            }
        }

        if (count < 3) arr.add(deathMsg);

        Globals.playerDeaths.put(p, arr);
        return isUnique.get();
    }
}
