package me.grovre.pillagerraidevents.listeners;

import me.grovre.pillagerraidevents.PillagerRaidEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Raid;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

public class RaidStart implements Listener {

    @EventHandler
    public void OnRaidStart(RaidTriggerEvent event) {

        // Config collection
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("PillagerRaidEvents").getConfig();

        // Raid info collection
        Raid raid = event.getRaid();
        Location raidLocation = raid.getLocation();
        int waveAmount = raid.getTotalWaves();
        Player startingPlayer = event.getPlayer();
        int omenLevel = raid.getBadOmenLevel();

        // Ugly if that is also a sentinel man :)
        if(!config.getBoolean("AnnounceToWorld") &&
                !config.getBoolean("AnnounceInDistance"))
            return;

        if(!config.getBoolean("AnnounceNewRaid")) return;
        // Message thingy
        String message = ChatColor.YELLOW + config.getString("RaidStartMessage");
        message = message.replace("%l", locToString(raidLocation));
        message = message.replace("%p", startingPlayer.getDisplayName());
        message = message.replace("%w", Integer.toString(waveAmount));
        message = message.replace("%o", Integer.toString(omenLevel));

        // Announcer
        if(config.getBoolean("AnnounceToWorld")) {
            PillagerRaidEvents.announceToWorld(message);
        } else if(config.getBoolean("AnnounceInDistance")) {
            PillagerRaidEvents.announceInDistance(message, raidLocation, PillagerRaidEvents.announceDistance);
        }
    }

    // Makes location look good in message
    public String locToString(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        return "(" + x + ", " + z + ")";
    }
}
