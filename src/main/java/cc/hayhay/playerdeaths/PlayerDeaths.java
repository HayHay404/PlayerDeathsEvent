package cc.hayhay.playerdeaths;

import cc.hayhay.playerdeaths.commands.CancelEventCommand;
import cc.hayhay.playerdeaths.commands.TimerCommand;
import cc.hayhay.playerdeaths.commands.TopCommand;
import cc.hayhay.playerdeaths.listeners.PlayerDeathListener;
import cc.hayhay.playerdeaths.listeners.PlayerFreezeListener;
import cc.hayhay.playerdeaths.listeners.PlayerJoinListener;
import cc.hayhay.playerdeaths.listeners.InventoryListener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class PlayerDeaths extends JavaPlugin {

    Logger log = getLogger();

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        log.info("Player Deaths started. Enjoy!");
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFreezeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        this.getCommand("pdStart").setExecutor(new TimerCommand());
        this.getCommand("pdTop").setExecutor(new TopCommand());
        this.getCommand("pdStop").setExecutor(new CancelEventCommand());
    }

    @Override
    public void onDisable() {}

    private static PlayerDeaths instance;

    public PlayerDeaths() {
        instance = this;
    }

    public static PlayerDeaths getInstance() {
        return instance;
    }
}


