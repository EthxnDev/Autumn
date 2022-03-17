package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class SandLag implements Listener {
    @EventHandler
    public void EntityChangeBlockEvent(EntityChangeBlockEvent e) {
        if (e.getEntityType() == EntityType.FALLING_BLOCK) {
            boolean enabled = Plugin.Instance.getConfig().getBoolean("SandLag.Enabled");
            int limit = Plugin.Instance.getConfig().getInt("SandLag.EntityLimit");
            String playername = "";
            if (enabled == false) return;
            int fallingInChunk = 0;
            for (Entity i : e.getBlock().getChunk().getEntities()) {
                if (i.getType() == EntityType.FALLING_BLOCK) {
                    fallingInChunk = fallingInChunk + 1;
                }
                if (i.getType() == EntityType.PLAYER) {
                    playername = i.getName();
                }
            }
            if (fallingInChunk > limit) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                ServerUtil.broadcastToOps(C.addPrefix(C.getSecondary(playername) + C.getThird(" has been in a chunk with more than ") + C.getSecondary(""+limit) + C.getThird(" falling block entities!")));
            }
        }
    }
}
