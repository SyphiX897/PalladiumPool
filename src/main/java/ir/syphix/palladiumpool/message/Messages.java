package ir.syphix.palladiumpool.message;

import ir.syphix.palladiumpool.PalladiumPool;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

    FileConfiguration config = PalladiumPool.messageFile();

    public static String PREFIX;
    public static String INVENTORY_IS_FULL;
    public static String RELOAD;


    public Messages() {
        PREFIX = getMessage("prefix");
        INVENTORY_IS_FULL = getMessage("inventory_is_full");
        RELOAD = getMessage("reload");
    }

    private String getMessage(String path) {
        return config.getString(path);
    }
}
