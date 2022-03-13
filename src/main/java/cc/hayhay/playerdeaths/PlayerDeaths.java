package cc.hayhay.playerdeaths;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class PlayerDeaths extends JavaPlugin {

    Logger log = getLogger();

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        log.info("Player Deaths started. Enjoy!");
        getServer().getPluginManager().registerEvents(new PdListener(), this);
        getServer().getPluginManager().registerEvents(new TopCmdListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFreezeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getCommand("pdStart").setExecutor(new Timer());
        this.getCommand("pdTop").setExecutor(new TopCmd());
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


