package cc.hayhay.playerdeaths.commands;

import cc.hayhay.playerdeaths.Globals;
import cc.hayhay.playerdeaths.PlayerDeaths;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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

    /**
     * Overload methods of {@link #sendTitle(Player, Component, Component)}; without the subtitle
     * @param player The player to send the title to
     * @param title The title to send
     */
    private void sendTitle(Player player, Component title) {
        sendTitle(player, title, Component.empty());
    }

    /**
     * Sends a title to the player using {@link Player#sendTitlePart(TitlePart, Object)}
     * @param player The player to send the title to
     * @param title The title to send
     * @param subtitle The subtitle to send
     */
    private void sendTitle(Player player, Component title, Component subtitle) {
        player.sendTitlePart(
                TitlePart.TITLE,
                title
        );

        player.sendTitlePart(
                TitlePart.SUBTITLE,
                subtitle
        );

        player.sendTitlePart(
                TitlePart.TIMES,
                Title.Times.of(Duration.ZERO, Duration.ofSeconds(1), Duration.ZERO)
        );
    }

    private void startTimer(int length) {
        int tickLength = (length * 20 * 60);
        int halfTimeTickLength = tickLength / 2;

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {

                    Globals.isEventStarting = true;

                    sendTitle(p, Component.text("3!", NamedTextColor.DARK_GREEN).decoration(TextDecoration.BOLD, true));
                    threadSleep();
                    sendTitle(p, Component.text("2!", NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, true));
                    threadSleep();
                    sendTitle(p, Component.text("1!", NamedTextColor.DARK_RED).decoration(TextDecoration.BOLD, true));
                    threadSleep();
                    sendTitle(p, Component.text("GO!", NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true));

                    Globals.isEventStarting = false;

                    eventStartPlayerInitialize();
                });
            }
        }.runTaskAsynchronously(PlayerDeaths.getInstance());

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (Globals.playerEventList.containsKey(p)) {
                        sendTitle(
                                p,
                                Component.text("Half Time!", NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, true),
                                Component.text("You have ", NamedTextColor.WHITE)
                                        .append(
                                                Component.text(Globals.playerEventList.get(p), NamedTextColor.GOLD)
                                        )
                                        .append(
                                                Component.text(" points!", NamedTextColor.WHITE)
                                        )
                        );
                    } else {
                        sendTitle(
                                p,
                                Component.text("Half Time!", NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, true));
                    }

                });
            }
        }.runTaskLaterAsynchronously(PlayerDeaths.getInstance(), halfTimeTickLength + 60);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PlayerDeaths.getInstance(), () -> {
            Globals.isEventRunning = false;
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (Globals.playerEventList.containsKey(p)) {
                    p.sendMessage("§6§lYou got " + Globals.playerEventList.get(p) + " points!");
                }
                sendTitle(
                        p,
                        Component.text("Time's Up!", NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, true),
                        Component.text("Teleporting you to spawn...", NamedTextColor.GREEN)
                );
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 29);
                p.teleport(p.getWorld().getSpawnLocation());
                if (p.getGameMode() == GameMode.SPECTATOR) p.setGameMode(GameMode.SURVIVAL);
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
