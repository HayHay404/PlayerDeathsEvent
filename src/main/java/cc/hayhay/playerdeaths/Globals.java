package cc.hayhay.playerdeaths;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.*;

public class Globals {
    public static HashMap<Player, Double> playerEventList = new HashMap<>();
    public static HashMap<Player, ArrayList<Component>> playerDeaths = new HashMap<>();
    public static ArrayList<Component> deathTypes = new ArrayList<>();
    public static boolean isEventRunning = false;
    public static boolean isEventStarting = false;
}

