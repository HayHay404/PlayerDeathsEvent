package cc.hayhay.playerdeaths;

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
                || sender instanceof ConsoleCommandSender
                && Globals.isEventRunning
                && args.length == 0) {
            Globals.isEventRunning = false;
            System.out.println("Cancelled all tasks");
            Bukkit.getScheduler().cancelTasks(PlayerDeaths.getInstance());

            sender.sendMessage("Event is now cancelled.");
        }
        return false;
    }
}
