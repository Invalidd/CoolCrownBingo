package com.invalid.CCCBingo;

import net.minecraft.server.v1_16_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if (event.getDeathMessage().contains("experienced kinetic energy")) {
            grantAchievement((Player) event.getEntity(), Main.plugin.getConfig().get("achievements.elytra_death").toString());
        }
    }

    @EventHandler
    public void ruinedPortal(EntityPortalEnterEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player))
            return;
        Player player = (Player) entity;
        Location playerLocation = player.getLocation();
        Location structure = player.getWorld().locateNearestStructure(playerLocation, StructureType.RUINED_PORTAL, 1, false);
        playerLocation.setY(0);
        if (structure != null && structure.distance(playerLocation) <= 5){
            grantAchievement(player, Main.plugin.getConfig().get("achievements.ruined_portal").toString());
        }
    }

    public static void grantAchievement(Player player, String achievement){
        try {
            NamespacedKey key = new NamespacedKey("bingokees", achievement);
            AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(key));
            for(String criteria : progress.getRemainingCriteria())
                progress.awardCriteria(criteria);
        } catch(Exception e)  {
            Main.console.sendMessage("Error granting achievement, stacktrace: \n" +e);
        }

    }
}
