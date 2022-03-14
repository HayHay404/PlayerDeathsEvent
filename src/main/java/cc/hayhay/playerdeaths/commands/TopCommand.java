package cc.hayhay.playerdeaths.commands;

import cc.hayhay.playerdeaths.Globals;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class TopCommand implements CommandExecutor {
    public static Inventory inv;

    public TopCommand() {
        inv = Bukkit.createInventory(null, 9);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player && (sender.isOp() || sender.hasPermission("playerDeaths.top"))) {
            inv.clear();
            ArrayList<String> topPlayers = top();
            topPlayers.forEach(s -> inv.addItem(createGuiItems(s)));
            ((Player) sender).openInventory(inv);
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    protected ItemStack createGuiItems(final String name) {

        final ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        String playerName = name.substring(name.indexOf("—") + 2);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

        item.setAmount(1);
        meta.setOwningPlayer(offlinePlayer);
        meta.displayName(Component.text(name, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

        item.setItemMeta(meta);

        return item;
    }

    private ArrayList<String> top() {
        ArrayList<String> arr = new ArrayList<>();

        LinkedHashMap<Player, Double> sorted = Globals.playerEventList.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        sorted.forEach((player, aDouble) -> arr.add(aDouble + " pts — " + player.getName()));

        return arr;
    }
}
