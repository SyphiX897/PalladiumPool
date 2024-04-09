package ir.syphix.palladiumpool;

import ir.syphix.palladiumpool.message.Messages;
import ir.syphix.palladiumpool.task.PlayerCheckTask;
import ir.syphix.palladiumpool.utils.YamlConfig;
import ir.syrent.origin.paper.Origin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class PalladiumPool extends JavaPlugin {

    private static FileConfiguration messageFile;
    private static Economy economy = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initializeMessageFile();
        PlayerCheckTask.startTask();

    }


    public static void initializeMessageFile() {
        YamlConfig messageConfig = new YamlConfig(Origin.getPlugin().getDataFolder(), "messages.yml");
        messageConfig.saveDefaultConfig();
        messageFile = messageConfig.getConfig();
        new Messages();
    }

    public static FileConfiguration messageFile() {
        return messageFile;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return economy;
    }

}
