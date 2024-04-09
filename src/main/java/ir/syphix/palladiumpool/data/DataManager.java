package ir.syphix.palladiumpool.data;

import ir.syphix.palladiumpool.utils.TextUtils;
import ir.syrent.origin.paper.Origin;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class DataManager {
    private static final FileConfiguration config = Origin.getPlugin().getConfig();


    public static List<String> enabledWorlds() {
        return TextUtils.split(";", config.getString("enabled_worlds"));
    }
    public static List<String> disabledWorlds() {
        return TextUtils.split(";", config.getString("disabled_worlds"));
    }
    public static List<String> regions() {
        return TextUtils.split(";", config.getString("regions"));
    }
    public static int poolInterval() {
        return config.getInt("pool.interval");
    }

    public static class EnterPool {
        public static final ConfigurationSection enterPoolSection = config.getConfigurationSection("pool.enter_pool");

        public static boolean isTitleEnable() {
            return enterPoolSection.getBoolean("title.enabled");
        }
        public static boolean isActionBarEnable() {
            return enterPoolSection.getBoolean("actionBar.enabled");
        }
        public static boolean isMessageEnable() {
            return enterPoolSection.getBoolean("message.enabled");
        }
        public static Component title() {
            return TextUtils.toComponent(enterPoolSection.getString("title.text"));
        }
        public static Component actionBar() {
            return TextUtils.toComponent(enterPoolSection.getString("actionBar.text"));
        }
        public static Component message() {
            return TextUtils.toComponent(enterPoolSection.getString("message.text"));
        }
    }

    public static class LeavePool {
        public static final ConfigurationSection leavePoolSection = config.getConfigurationSection("pool.leave_pool");

        public static boolean isTitleEnable() {
            return leavePoolSection.getBoolean("title.enabled");
        }
        public static boolean isActionBarEnable() {
            return leavePoolSection.getBoolean("actionBar.enabled");
        }
        public static boolean isMessageEnable() {
            return leavePoolSection.getBoolean("message.enabled");
        }
        public static Component title() {
            return TextUtils.toComponent(leavePoolSection.getString("title.text"));
        }
        public static Component actionBar() {
            return TextUtils.toComponent(leavePoolSection.getString("actionBar.text"));
        }
        public static Component message() {
            return TextUtils.toComponent(leavePoolSection.getString("message.text"));
        }
    }

    public static class Reward {
        public static final ConfigurationSection rewardSection = config.getConfigurationSection("pool.reward");

        public static boolean isMoneyEnable() {
            return rewardSection.getBoolean("money.enabled");
        }
        public static String moneyRange() {
            return rewardSection.getString("money.amount");
        }

        public static boolean isCrateEnable() {
            return rewardSection.getBoolean("crate.enabled");
        }
        public static List<String> crateKeys() {
            return rewardSection.getStringList("crate.keys");
        }

        public static boolean isItemEnable() {
            return rewardSection.getBoolean("item.enabled");
        }
        public static List<String> items() {
            return rewardSection.getStringList("item.items");
        }
    }
}
