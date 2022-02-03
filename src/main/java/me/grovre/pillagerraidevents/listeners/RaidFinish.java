package me.grovre.pillagerraidevents.listeners;

import me.grovre.pillagerraidevents.PillagerRaidEvents;
import org.bukkit.Bukkit;
import org.bukkit.Raid;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidFinishEvent;

public class RaidFinish implements Listener {

    @EventHandler
    public void OnRaidFinish(RaidFinishEvent event) {
        System.out.println("Raid finished!");

        // Config collection
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("PillagerRaidEvents").getConfig();

        // Raid info collection
        Raid raid = event.getRaid();

        String message = config.getString("RaidFinishMessage");
        message = message.replace("%p", getInvolvedPlayers(event));

        // Sentinel against unwanted announcement
        if(!config.getBoolean("AnnounceRaidFinish")) return;

        // Announcer of finished raids
        if(PillagerRaidEvents.worldAnnouncement) {
            PillagerRaidEvents.announceToWorld(message);
        } else if(PillagerRaidEvents.distanceAnnouncement) {
            PillagerRaidEvents.announceInDistance(message, raid.getLocation(), PillagerRaidEvents.announceDistance);
        }
    }

    /*public String getInvolvedPlayers(Raid raid) {
        Location raidLocation = raid.getLocation();
        StringBuilder players = new StringBuilder();
        for(Player p : Bukkit.getOnlinePlayers()) {
            boolean firstPlayerFlag = false;
            if(p.getLocation().distance(raidLocation) <= 250 && !firstPlayerFlag) {
                players.append(p.getDisplayName());
                firstPlayerFlag = true;
            }
            if(p.getLocation().distance(raidLocation) <= 250) {
                players.append(p.getDisplayName());
            }
        }
        return players.toString();
    }*/

    public String getInvolvedPlayers(RaidFinishEvent event) {

        StringBuilder players = new StringBuilder();
        boolean firstPlayerFlag = false;
        for(Player p : event.getWinners()) {
            if(p.getLocation().distance(event.getRaid().getLocation()) <= 250) {
                if(!firstPlayerFlag) {
                    players.append(p.getDisplayName());
                    firstPlayerFlag = true;
                    continue;
                }
                players.append(p.getDisplayName());
            }
        }
        return players.toString();
    }

}
