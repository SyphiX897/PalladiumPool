package ir.syphix.palladiumpool.task;

import ir.syphix.palladiumpool.data.DataManager;
import ir.syrent.origin.paper.Origin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCheckTask {

    private static int taskId;
    private static final FileConfiguration config = Origin.getPlugin().getConfig();
    public static HashMap<UUID, Long> PLAYERS_TIME = new HashMap<>();

    public static void startTask() {
        Bukkit.getScheduler().runTaskTimer(Origin.getPlugin(), task ->{
            taskId = task.getTaskId();
            int interval = config.getInt("interval");

            for (Player player : Origin.getOnlinePlayers()) {
                Location location = player.getLocation().clone();
                if (location.getBlock().getType() != Material.WATER) return;
            }




        }, 5, 5);
    }

    public static void stopTask() {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
