package ir.syphix.palladiumpool.action;

import ir.syphix.palladiumpool.PalladiumPool;
import ir.syphix.palladiumpool.utils.TextUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class PoolActions {

    public static void giveMoney(Player player, String range) {

        Random random = new Random();
        List<String> moneyRange = TextUtils.split("-", range);
        int min = Integer.parseInt(moneyRange.get(0));
        int max = Integer.parseInt(moneyRange.get(1));
        int money = random.nextInt(max - min + 1) + min;

        PalladiumPool.getEconomy().depositPlayer(player, money);
    }

    public static void giveCrate(Player player, String formattedKeyName) {
        List<String> keyInfo = TextUtils.split(";", formattedKeyName);

    }

    public static void giveItem(Player player, String formattedItemName) {
        List<String> itemInfo = TextUtils.split(";", formattedItemName);
    }

    public static double randomNumber() {
        return Math.random() * 100;
    }
}
