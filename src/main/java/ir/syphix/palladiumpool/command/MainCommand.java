package ir.syphix.palladiumpool.command;

import ir.syphix.palladiumpool.PalladiumPool;
import ir.syphix.palladiumpool.message.Messages;
import ir.syphix.palladiumpool.utils.TextUtils;
import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.command.OriginCommand;
import ir.syrent.origin.paper.command.interfaces.SenderExtension;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;

public class MainCommand extends OriginCommand {
    public MainCommand() {
        super("palladiumpool", "pp");

        Command.Builder<SenderExtension> reload = getBuilder()
                .literal("reload")
                .permission("reload")
                .handler(context -> {
                    Player player = context.sender().player();
                    Origin.getPlugin().reloadConfig();
                    PalladiumPool.initializeMessageFile();
                    if (player == null) return;
                    player.sendMessage(TextUtils.toFormattedComponent(Messages.RELOAD));
                });
        getManager().command(reload);
    }
}
