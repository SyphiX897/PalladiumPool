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

public class PlayerCheckTask extends DataManager{

    private static int taskId;
    public static HashMap<UUID, Long> PLAYERS_TIME = new HashMap<>();

    public static void startTask() {
        Bukkit.getScheduler().runTaskTimer(Origin.getPlugin(), task -> {
            taskId = task.getTaskId();

            for (Player player : Origin.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                Location location = player.getLocation().clone();
                Material blockType = location.getBlock().getType();


                if (blockType != Material.WATER && PLAYERS_TIME.containsKey(uuid)) {
                    PLAYERS_TIME.remove(uuid);
                    sendLeaveDetails(player);
                }

                if (disabledWorlds().contains(location.getWorld().getName())) {
                    return;
                }

                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                World world = player.getWorld();
                RegionManager regionManager = container.get(BukkitAdapter.adapt(world));
                boolean isAllowedRegion = false;

                for (String region : regions()) {
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
                    if (PLAYERS_TIME.get(uuid) + poolInterval() <= System.currentTimeMillis()) {
                        PLAYERS_TIME.put(uuid, System.currentTimeMillis());

                        if (Reward.isMoneyEnable()) {
                             PoolActions.giveMoney(player, Reward.moneyRange());
                        }
                        if (Reward.isItemEnable()) {
                            for (String formattedItemName : Reward.items()) {
                                PoolActions.giveItem(player, formattedItemName);
                            }
                        }
                        if (Reward.isCommandEnable()) {
                            for (String formattedCommand : Reward.commands()) {
                                PoolActions.runCommand(player, formattedCommand);
                            }
                        }
                    }
                }
            }
        }, 0, 5);
    }

    private static void sendEnterDetails(Player player) {
        if (EnterPool.isTitleEnable()) {
            player.showTitle(Title.title(EnterPool.title(player),
                    EnterPool.subTitle(player), Title.Times.times(Duration.ofMillis(500),
                            Duration.ofMillis(750), Duration.ofMillis(500))));
        }
        if (EnterPool.isActionBarEnable()) {
            player.sendActionBar(EnterPool.actionBar(player));
        }
        if (EnterPool.isMessageEnable()) {
            if (EnterPool.isMessageBroadcast()) {
                Bukkit.broadcast(EnterPool.message(player));
            } else {
                player.sendMessage(EnterPool.message(player));
            }
        }
    }

    private static void sendLeaveDetails(Player player) {
        if (LeavePool.isTitleEnable()) {
            player.showTitle(Title.title(LeavePool.title(player),
                    LeavePool.subTitle(player), Title.Times.times(Duration.ofMillis(500),
                            Duration.ofMillis(750), Duration.ofMillis(500))));
        }
        if (LeavePool.isActionBarEnable()) {
            player.sendActionBar(LeavePool.actionBar(player));
        }
        if (LeavePool.isMessageEnable()) {
            if (LeavePool.isMessageBroadcast()) {
                Bukkit.broadcast(LeavePool.message(player));
            } else {
                player.sendMessage(LeavePool.message(player));
            }
        }
    }
}
