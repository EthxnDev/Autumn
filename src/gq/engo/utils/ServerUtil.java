package gq.engo.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import gq.engo.utils.handlers.MaterialHandler;

public class ServerUtil {
    public static void broadcast(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    public static void broadcastToOps(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOp()) {
                p.sendMessage(message);
            }
        }
    }

    public static Player[] getOnline() {
        Player[] plrs = {};
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("autumn.vanish")) {
                plrs[plrs.length + 1] = p;
            }
        }
        return plrs;
    }

    public static void clearPotions(Player p, org.bukkit.potion.PotionEffect i) {
        p.removePotionEffect(i.getType());
        MaterialHandler.clearMaterials(p, Material.POTION);
        MaterialHandler.clearMaterials(p, Material.SPLASH_POTION);
        MaterialHandler.clearMaterials(p, Material.LINGERING_POTION);
        p.sendMessage(C.addPrefix("Detected impossible potion effect. " + C.getThird("") + "(" + C.getSecondary("") + i.getType().getName() + " " + i.getAmplifier() + C.getThird(")")));
    }
}
