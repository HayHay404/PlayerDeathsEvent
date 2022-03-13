package cc.hayhay.playerdeaths.listeners;

import cc.hayhay.playerdeaths.Globals;
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

        //noinspection deprecation
        String deathMsg = event.getDeathMessage();
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
                    p.sendMessage("§a§lGained " + (multiplier * .5) + " points!");
                    break;
                case 2:
                    Globals.playerEventList.put(p, val + (multiplier * .25));
                    p.sendMessage("§a§lGained " + (multiplier * .25) + " points!");
                    break;
                default:
                    Globals.playerEventList.put(p, val + (multiplier * .1));
                    p.sendMessage("§a§lGained " + (multiplier * .1) + " points!");
                    break;
            }
        }

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 29);
    }

    private boolean isUniqueDeath(String deathMsg, Player p) {
        AtomicBoolean isUnique = new AtomicBoolean(true);
        ArrayList<String> arr = new ArrayList<>();
        if (Globals.playerDeaths.get(p) != null) {
            arr = Globals.playerDeaths.get(p);
        }

        count = 0;

        for (String s : arr) {
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
