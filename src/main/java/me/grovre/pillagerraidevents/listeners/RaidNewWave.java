package me.grovre.pillagerraidevents.listeners;

import me.grovre.pillagerraidevents.PillagerRaidEvents;
import org.bukkit.Bukkit;
import org.bukkit.Raid;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidSpawnWaveEvent;

public class RaidNewWave implements Listener {

    @EventHandler
    public void OnNewWave(RaidSpawnWaveEvent event) {

        // Config collection
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("PillagerRaidEvents").getConfig();

        // Simple raid info collection
        Raid raid = event.getRaid();
        int groupcount = raid.getSpawnedGroups();

        // Message maker
        String message = config.getString("NewWaveMessage");
        message = message.replace("%g", Integer.toString(groupcount));

        // Message sender depending on config setup
        if(config.getBoolean("AnnounceNewWave")) {
            if(PillagerRaidEvents.worldAnnouncement) {
                PillagerRaidEvents.announceToWorld(message);
            } else if(PillagerRaidEvents.distanceAnnouncement) {
                PillagerRaidEvents.announceInDistance(message, raid.getLocation(), PillagerRaidEvents.announceDistance);
            }
        }
    }
}
