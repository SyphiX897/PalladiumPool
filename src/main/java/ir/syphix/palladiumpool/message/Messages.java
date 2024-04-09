package ir.syphix.palladiumpool.message;

import ir.syphix.palladiumpool.PalladiumPool;
import ir.syphix.palladiumpool.data.DataManager;
import ir.syrent.origin.paper.Origin;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

    FileConfiguration config = PalladiumPool.messageFile();

    public static String PREFIX;
    public static String NEED_PERMISSION;
    public static String PLAYER_NOT_FOUND;
    public static String INVENTORY_IS_FULL;
    public static String RELOAD;


    public Messages() {
        PREFIX = getMessage("prefix");
        NEED_PERMISSION = getMessage("need_permission");
        PLAYER_NOT_FOUND = getMessage("player_not_found");
        INVENTORY_IS_FULL = getMessage("inventory_is_full");
        RELOAD = getMessage("reload");
    }

    private String getMessage(String path) {
        return config.getString(path);
    }
}
