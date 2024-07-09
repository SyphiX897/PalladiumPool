package ir.syphix.palladiumpool.item;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import ir.syphix.palladiumpool.PalladiumPool;
import ir.syrent.origin.paper.Origin;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class CustomItems {

    public static HashMap<String, ItemStack> customItems = new HashMap<>();

    public static void addItems() {
        if (Origin.hasPlugin("Slimefun")) {
            for (SlimefunItem slimefunItem : Slimefun.getRegistry().getAllSlimefunItems()) {
                customItems.put(slimefunItem.getId(), slimefunItem.getItem());
            }
        }
    }

}
