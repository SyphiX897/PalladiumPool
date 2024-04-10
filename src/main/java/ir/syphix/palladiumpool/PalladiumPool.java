package ir.syphix.palladiumpool;

import ir.syphix.palladiumpool.command.MainCommand;
import ir.syphix.palladiumpool.item.CustomItems;
import ir.syphix.palladiumpool.message.Messages;
import ir.syphix.palladiumpool.task.PlayerCheckTask;
import ir.syphix.palladiumpool.utils.YamlConfig;
import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.OriginPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;

public final class PalladiumPool extends OriginPlugin {

    private static FileConfiguration messageFile;
    private static Economy economy;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        economy = Origin.getPlugin().getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        initializeMessageFile();
        new Messages();
        new MainCommand();
        CustomItems.addItems();
        PlayerCheckTask.startTask();

    }


    public static void initializeMessageFile() {
        YamlConfig messageConfig = new YamlConfig(Origin.getPlugin().getDataFolder(), "messages.yml");
        messageConfig.saveDefaultConfig();
        messageFile = messageConfig.getConfig();
    }

    public static FileConfiguration messageFile() {
        return messageFile;
    }

    public static Economy getEconomy() {
        return economy;
    }

}
