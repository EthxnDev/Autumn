package gq.engo.utils.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaterialHandler {
    public static boolean findMaterial(Material[] array, org.bukkit.Material find) {
        boolean isIn = false;
        for (Material i : array) {
            if (i == find) {
                isIn = true;
                break;
            }
        }
        return isIn;
    }

    public static void clearMaterials(Player p, Material item) {
        for (ItemStack i : p.getInventory()) {
            if (i == null) continue;
            if (i.getType().equals(item)) {
                p.getInventory().setItemInOffHand(null);
                p.getInventory().setItemInMainHand(null)    ;
                i.setAmount(0);
            }
        }
    }
}
