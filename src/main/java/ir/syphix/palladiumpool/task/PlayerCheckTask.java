package ir.syphix.palladiumpool.task;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import ir.syphix.palladiumpool.action.PoolActions;
import ir.syphix.palladiumpool.data.DataManager;
import ir.syrent.origin.paper.Origin;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

public class PlayerCheckTask {

    private static int taskId;
    public static HashMap<UUID, Long> PLAYERS_TIME = new HashMap<>();

    public static void startTask() {
        Bukkit.getScheduler().runTaskTimer(Origin.getPlugin(), task -> {
            taskId = task.getTaskId();
            long interval = DataManager.poolInterval() * 1000L;

            for (Player player : Origin.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                Location location = player.getLocation().clone();
                Material blockType = location.getBlock().getType();

                if (blockType != Material.WATER && PLAYERS_TIME.containsKey(uuid)) {
                    Origin.broadcast("8");
                    PLAYERS_TIME.remove(uuid);
                    sendLeaveDetails(player);
                }

                boolean isDisabledWorld = false;
                for (String worldName : DataManager.disabledWorlds()) {
                    if (player.getWorld().getName().equals(worldName)) {
                        isDisabledWorld = true;
                        break;
                    }
                }
                if (isDisabledWorld) return;

                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                World world = player.getWorld();
                RegionManager regionManager = container.get(BukkitAdapter.adapt(world));
                boolean isAllowedRegion = false;

                for (String region : DataManager.regions()) {
                    ProtectedRegion protectedRegion = regionManager.getRegion(region);
                    if (protectedRegion == null) continue;
                    if (protectedRegion.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                        isAllowedRegion = true;
                    }
                }
                if (!isAllowedRegion) return;

                if (blockType == Material.WATER && !PLAYERS_TIME.containsKey(uuid)) {
                    PLAYERS_TIME.put(uuid, System.currentTimeMillis());
                    sendEnterDetails(player);
                }

                if (blockType == Material.WATER && PLAYERS_TIME.containsKey(uuid)) {
                    if (PLAYERS_TIME.get(uuid) + interval <= System.currentTimeMillis()) {
                        PLAYERS_TIME.put(uuid, System.currentTimeMillis());

                        if (DataManager.Reward.isMoneyEnable()) {
                             PoolActions.giveMoney(player, DataManager.Reward.moneyRange());
                        }
                        if (DataManager.Reward.isItemEnable()) {
                            for (String formattedItemName : DataManager.Reward.items()) {
                                PoolActions.giveItem(player, formattedItemName);
                            }
                        }
                        if (DataManager.Reward.isCommandEnable()) {
                            for (String formattedCommand : DataManager.Reward.commands()) {
                                PoolActions.runCommand(player, formattedCommand);
                            }
                        }
                    }
                }
            }
        }, 0, 5);
    }

    public static void stopTask() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    private static void sendEnterDetails(Player player) {
        if (DataManager.EnterPool.isTitleEnable()) {
            player.showTitle(Title.title(DataManager.EnterPool.title(player),
                    DataManager.EnterPool.subTitle(player), Title.Times.times(Duration.ofMillis(500),
                            Duration.ofMillis(750), Duration.ofMillis(500))));
        }
        if (DataManager.EnterPool.isActionBarEnable()) {
            player.sendActionBar(DataManager.EnterPool.actionBar(player));
        }
        if (DataManager.EnterPool.isMessageEnable()) {
            if (DataManager.EnterPool.isMessageBroadcast()) {
                Bukkit.broadcast(DataManager.EnterPool.message(player));
            } else {
                player.sendMessage(DataManager.EnterPool.message(player));
            }
        }
    }

    private static void sendLeaveDetails(Player player) {
        if (DataManager.LeavePool.isTitleEnable()) {
            player.showTitle(Title.title(DataManager.LeavePool.title(player),
                    DataManager.LeavePool.subTitle(player), Title.Times.times(Duration.ofMillis(500),
                            Duration.ofMillis(750), Duration.ofMillis(500))));
        }
        if (DataManager.LeavePool.isActionBarEnable()) {
            player.sendActionBar(DataManager.LeavePool.actionBar(player));
        }
        if (DataManager.LeavePool.isMessageEnable()) {
            if (DataManager.LeavePool.isMessageBroadcast()) {
                Bukkit.broadcast(DataManager.LeavePool.message(player));
            } else {
                player.sendMessage(DataManager.LeavePool.message(player));
            }
        }
    }
}
