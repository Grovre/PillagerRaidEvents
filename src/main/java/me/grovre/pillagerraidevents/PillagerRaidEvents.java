package me.grovre.pillagerraidevents;

import me.grovre.pillagerraidevents.listeners.RaidFinish;
import me.grovre.pillagerraidevents.listeners.RaidNewWave;
import me.grovre.pillagerraidevents.listeners.RaidStart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PillagerRaidEvents extends JavaPlugin {

    public static boolean distanceAnnouncement;
    public static boolean worldAnnouncement;
    public static double announceDistance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        distanceAnnouncement = this.getConfig().getBoolean("AnnounceInDistance");
        worldAnnouncement = this.getConfig().getBoolean("AnnounceToWorld");
        announceDistance = this.getConfig().getInt("DistanceToAnnounce");

        // Listener registration
        Bukkit.getServer().getPluginManager().registerEvents(new RaidStart(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new RaidNewWave(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new RaidFinish(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // For every online player, send message
    public static void announceToWorld(String message) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    // For every online player within distance, send message
    public static void announceInDistance(String message, Location loc, double distance) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getLocation().distance(loc) <= distance) {
                p.sendMessage(message);
            }
        }
    }

}
