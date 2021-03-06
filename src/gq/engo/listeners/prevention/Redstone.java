package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import gq.engo.utils.TPS;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;


public class Redstone implements Listener {
    public final java.util.HashMap<Player, Integer> flicks = new java.util.HashMap<>();
    String playername = "";
    String cachedname = "";

    @EventHandler
    public void redstoneUpdate(BlockRedstoneEvent e) {
        boolean enabled = Plugin.Instance.getConfig().getBoolean("Redstone.Enabled");
        int limit = Plugin.Instance.getConfig().getInt("Redstone.DisableTPS");
        double tps = TPS.getRoundedTPS();
        if (enabled == false) return;
        if (tps < limit) {
            e.getBlock().setType(Material.AIR);

            Entity[] entities = e.getBlock().getChunk().getEntities();
            for (Entity ent : entities) {
                if (ent.getType() == EntityType.PLAYER) {
                    playername = ent.getName();
                    //ent.sendMessage(C.chat(C.getPrefix() + C.getSecondary("Redstone device") + C.getThird("") + " destroyed due to suspected " + C.getSecondary("lag") + C.getThird(".")));
                }
            }
            if (playername != cachedname) {
                cachedname = playername;
                ServerUtil.broadcastToOps(C.addPrefix(C.getSecondary(playername) + C.getThird(" has been in a chunk with redstone while the server was lagging, it has been deleted!")));
            }
        }
    }

    // anti lever spam
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType() == Material.LEVER || e.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR)) {
            int limit = Plugin.Instance.getConfig().getInt("Redstone.MaxLeverCPS");
            if (flicks.containsKey(e.getPlayer())) {
                flicks.put(e.getPlayer(), flicks.get(e.getPlayer()) + 1);
            } else {
                flicks.put(e.getPlayer(), 1);
            }
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        flicks.put(e.getPlayer(), flicks.get(e.getPlayer())-1);
                    }
                },
                1000
            );

            if (flicks.get(e.getPlayer()) > limit) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(C.addPrefix(C.getThird("Please stop interacting so fast!")));
            }
        }
    }
}
