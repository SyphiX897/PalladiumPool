package ir.syphix.palladiumpool.data;

import ir.syphix.palladiumpool.utils.TextUtils;
import ir.syrent.origin.paper.Origin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("unused")
public class DataManager extends TextUtils{
    private static final FileConfiguration config = Origin.getPlugin().getConfig();


    public static List<String> disabledWorlds() {
        return split(";", config.getString("disabled_worlds"));
    }
    public static List<String> regions() {
        return split(";", config.getString("allowed_regions"));
    }
    public static long poolInterval() {
        return (config.getInt("pool.interval") * 1000L);
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
        public static boolean isMessageBroadcast() {
            return enterPoolSection.getBoolean("message.broadcast");
        }
        public static Component title(Player player) {
            return toComponent(enterPoolSection.getString("title.title"), Placeholder.unparsed("player", player.getName()));
        }
        public static Component subTitle(Player player) {
            return toComponent(enterPoolSection.getString("title.subTitle"), Placeholder.unparsed("player", player.getName()));
        }
        public static Component actionBar(Player player) {
            return toComponent(enterPoolSection.getString("actionBar.actionBar"), Placeholder.unparsed("player", player.getName()));
        }
        public static Component message(Player player) {
            return toComponent(enterPoolSection.getString("message.message"), Placeholder.unparsed("player", player.getName()));
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
        public static boolean isMessageBroadcast() {
            return leavePoolSection.getBoolean("message.broadcast");
        }
        public static Component title(Player player) {
            return toComponent(leavePoolSection.getString("title.title"), Placeholder.unparsed("player", player.getName()));
        }
        public static Component subTitle(Player player) {
            return toComponent(leavePoolSection.getString("title.subTitle"), Placeholder.unparsed("player", player.getName()));
        }
        public static Component actionBar(Player player) {
            return toComponent(leavePoolSection.getString("actionBar.actionBar"), Placeholder.unparsed("player", player.getName()));
        }
        public static Component message(Player player) {
            return toComponent(leavePoolSection.getString("message.message"), Placeholder.unparsed("player", player.getName()));
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

        public static boolean isItemEnable() {
            return rewardSection.getBoolean("item.enabled");
        }
        public static List<String> items() {
            return rewardSection.getStringList("item.items");
        }

        public static boolean isCommandEnable() {
            return rewardSection.getBoolean("command.enabled");
        }
        public static List<String> commands() {
            return rewardSection.getStringList("command.commands");
        }
    }
}
