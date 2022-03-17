package gq.engo.listeners.prevention;

import gq.engo.Plugin;
import gq.engo.utils.C;
import gq.engo.utils.handlers.MaterialHandler;
import gq.engo.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class Antillegal implements Listener {
    Material[] illegalblocks = new Material[]{ Material.BEDROCK, Material.ENDER_PORTAL_FRAME, Material.BARRIER };

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (Plugin.Instance.getConfig().getBoolean("Illegals.Enabled") == false) return;
        Player plr = event.getPlayer();
        Material type = event.getBlock().getType();
        if (MaterialHandler.findMaterial(this.illegalblocks, type) && (!plr.isOp() || Plugin.Instance.getConfig().getBoolean("Illegals.OPsBypass") == false )) {
            event.setCancelled(true);
            MaterialHandler.clearMaterials(plr, type);
            plr.sendMessage(C.addPrefix("That block is not allowed!"));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER || Plugin.Instance.getConfig().getBoolean("Anti32k.Enabled") == false) return;
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        double damage = event.getDamage();
        ItemStack offhanditem = damager.getServer().getPlayer(damager.getName()).getInventory().getItemInOffHand();
        ItemStack mainhanditem = damager.getServer().getPlayer(damager.getName()).getInventory().getItemInMainHand();
        if (damage > 30.0D && (!damager.isOp() || Plugin.Instance.getConfig().getBoolean("Anti32k.OPsBypass") == false)) {
            damager.sendMessage(C.addPrefix("Detected impossible damage. " + C.getThird("") + "(" + C.getSecondary("") + (int)damage + " dmg" + C.getThird(")")));
            event.setCancelled(true);
            if (offhanditem.getItemMeta() == mainhanditem.getItemMeta()) {
                offhanditem.setAmount(0);
            }
            mainhanditem.setAmount(0);
        }
    }

    @EventHandler
    public void onItemSwap(PlayerItemHeldEvent e) {
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        PotionEffect[] potions = e.getPlayer().getActivePotionEffects().toArray(new org.bukkit.potion.PotionEffect[0]);
        for (PotionEffect i : potions) {
            if (i.getType() == PotionEffectType.DAMAGE_RESISTANCE) {
                if (i.getAmplifier() > 1) {
                    ServerUtil.clearPotions(e.getPlayer(), i);
                    continue;
                }
            } else {
                if (i.getAmplifier() > 5) {
                    ServerUtil.clearPotions(e.getPlayer(), i);
                    continue;
                }
            }

            if (i.getDuration() > 600) {
                ServerUtil.clearPotions(e.getPlayer(), i);
                continue;
            }
        }
    }
}
