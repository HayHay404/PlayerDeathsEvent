package cc.hayhay.playerdeaths.commands;

import cc.hayhay.playerdeaths.Globals;
import cc.hayhay.playerdeaths.PlayerDeaths;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class CancelEventCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("playerDeaths.start")
                || sender.isOp()
                || sender instanceof ConsoleCommandSender) {

            if (!Globals.isEventRunning) {
                sender.sendMessage(Component.text("No event running", NamedTextColor.RED));
                return true;
            }

            Globals.isEventRunning = false;
            Bukkit.getScheduler().cancelTasks(PlayerDeaths.getInstance());

            sender.sendMessage(Component.text("Event is now cancelled.", NamedTextColor.GREEN));
            return true;
        }

        return false;
    }
}
