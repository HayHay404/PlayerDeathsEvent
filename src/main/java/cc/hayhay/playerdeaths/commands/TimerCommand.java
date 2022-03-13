package cc.hayhay.playerdeaths.commands;

import cc.hayhay.playerdeaths.Globals;
import cc.hayhay.playerdeaths.PlayerDeaths;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class TimerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player
                && (sender.isOp() || sender.hasPermission("playerDeaths.start"))
                && args.length == 1
                && !Globals.isEventRunning) {
            startTimer(Integer.parseInt(args[0]));

            return true;
        } else return false;
    }

    private void threadSleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startTimer(int length) {
        int tickLength = (length * 20 * 60);
        int halfTimeTickLength = tickLength / 2;

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {

                    Globals.isEventStarting = true;

                    p.sendTitle("§2§l3!", "");
                    threadSleep();
                    p.sendTitle("§e§l2!", "");
                    threadSleep();
                    p.sendTitle("§4§l1!", "");
                    threadSleep();
                    p.sendTitle("§lGO!", "");

                    Globals.isEventStarting = false;

                    eventStartPlayerInitialize();
                });
            }
        }.runTaskAsynchronously(PlayerDeaths.getInstance());

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> p.sendTitle("§lHalf time!",
                        "You have " + Globals.playerEventList.get(p) + " points!"));
            }
        }.runTaskLaterAsynchronously(PlayerDeaths.getInstance(), halfTimeTickLength + 60);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PlayerDeaths.getInstance(), () -> {
            Globals.isEventRunning = false;
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.sendTitle("§6Event now over!", "§aTeleporting to spawn");
                p.sendMessage("§6§lYou got " + Globals.playerEventList.get(p) + " points!");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 29);
                p.teleport(p.getWorld().getSpawnLocation());
            });
        }, tickLength + 60);
    }

    public void eventStartPlayerInitialize() {
        Globals.isEventRunning = true;

        // Clear all the old arrays and maps
        Globals.playerDeaths.clear();
        Globals.playerEventList.clear();
        Globals.deathTypes.clear();

        Bukkit.getOnlinePlayers().forEach(player -> Globals.playerEventList.put(player, 0.0));

    }
}
