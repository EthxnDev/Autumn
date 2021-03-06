package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import gq.engo.utils.TPS;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Elytra implements Listener {
    public final java.util.HashMap<Player, Integer> warnings = new java.util.HashMap<>();
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        int limit = Plugin.Instance.getConfig().getInt("Elytra.DisableTPS");
        Player p = e.getPlayer();
        if (p.isGliding() && TPS.getRoundedTPS() < limit) {
            p.sendMessage(C.getPrefix() + C.chat(C.getThird("Elytras are disabled under ") + C.getSecondary(""+limit) + C.getThird(" TPS!")));
            p.setGliding(false);
            if (warnings.containsKey(e.getPlayer())) {
                warnings.put(e.getPlayer(), warnings.get(e.getPlayer()) + 1);
            } else {
                warnings.put(e.getPlayer(), 1);
            }
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            warnings.put(e.getPlayer(), warnings.get(e.getPlayer())-1);
                        }
                    },
                    1000
            );
            if (warnings.get(e.getPlayer()) > Plugin.Instance.getConfig().getInt("Elytra.KickAmount")) {
                org.bukkit.Bukkit.getScheduler().runTask(Plugin.Instance, new Runnable() {
                        public void run() {
                            e.getPlayer().kickPlayer(C.getPrefix() + C.chat(C.getThird("Elytras are disabled under ") + C.getSecondary(""+limit) + C.getThird(" TPS!")));
                        }
                });
            }
        }
    }
}
