package ir.syphix.palladiumpool.action;

import ir.syphix.palladiumpool.PalladiumPool;
import ir.syphix.palladiumpool.item.CustomItems;
import ir.syphix.palladiumpool.message.Messages;
import ir.syphix.palladiumpool.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PoolActions {

    public static void giveMoney(Player player, String range) {
        Random random = new Random();
        List<String> moneyRange = TextUtils.split("-", range);
        int min = Integer.parseInt(moneyRange.get(0));
        int max = Integer.parseInt(moneyRange.get(1));
        int moneyAmount = random.nextInt(max - min + 1) + min;

        PalladiumPool.getEconomy().depositPlayer(player, moneyAmount);
    }


    public static void giveItem(Player player, String formattedItemName) {
        List<String> itemInfo = TextUtils.split(";", formattedItemName);
        int chance = Integer.parseInt(itemInfo.get(2).replace("%", ""));
        int amount = Integer.parseInt(itemInfo.get(1));

        if (chance <= randomNumber()) return;
        if (CustomItems.customItems.containsKey(itemInfo.get(0))) {
            ItemStack itemStack = CustomItems.customItems.get(itemInfo.get(0));
            if (player.getInventory().firstEmpty() == -1) {
                player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
                player.sendMessage(TextUtils.toFormattedComponent(Messages.INVENTORY_IS_FULL));
            } else {
                player.getInventory().addItem(itemStack);
            }

        } else {
            if (!Arrays.stream(Material.values()).map(Material::name).toList().contains(itemInfo.get(0))) {
                return;
            }
            ItemStack itemStack = new ItemStack(Material.valueOf(itemInfo.get(0)), amount);
            if (player.getInventory().firstEmpty() == -1) {
                player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
                player.sendMessage(TextUtils.toFormattedComponent(Messages.INVENTORY_IS_FULL));
            } else {
                player.getInventory().addItem(itemStack);
            }
        }
    }

    public static void runCommand(Player player, String formattedCommand) {
        List<String> commandInfo = TextUtils.split(";", formattedCommand);
        String sender = commandInfo.get(0);
        String command = commandInfo.get(1).replace("<player>", player.getName());
        int chance = Integer.parseInt(commandInfo.get(2).replace("%", ""));
        if (chance <= randomNumber()) return;
        if (sender.equals("player")) {
            Bukkit.dispatchCommand(player, command);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    private static double randomNumber() {
        return Math.random() * 100;
    }
}
















