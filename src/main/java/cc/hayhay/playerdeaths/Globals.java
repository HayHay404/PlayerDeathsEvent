package cc.hayhay.playerdeaths;

import org.bukkit.entity.Player;

import java.util.*;

public class Globals {
    public static HashMap<Player, Double> playerEventList = new HashMap<>();
    public static HashMap<Player, ArrayList<String>> playerDeaths = new HashMap<>();
    public static ArrayList<String> deathTypes = new ArrayList<>();
    public static boolean isEventRunning = false;
    public static boolean isEventStarting = false;
}

