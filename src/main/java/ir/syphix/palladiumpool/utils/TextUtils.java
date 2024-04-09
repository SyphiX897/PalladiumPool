package ir.syphix.palladiumpool.utils;

import ir.syphix.palladiumpool.message.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Arrays;
import java.util.List;

public class TextUtils {

    static MiniMessage miniMessage = MiniMessage.builder()
            .tags(
                    TagResolver.resolver(
                            TagResolver.standard()
                    )
            )
            .build();

    public static Component toFormattedComponent(String message, TagResolver... placeholders) {
        return toComponent(String.format("%s%s", Messages.PREFIX, message), placeholders);
    }

    public static Component toComponent(String content, TagResolver... placeholders) {
        return Component.empty().decoration(TextDecoration.ITALIC, false).append(miniMessage.deserialize(content, placeholders));


    }

    public static List<String> split(String regex, String content) {
        return Arrays.stream(content.split(regex)).toList();
    }
}
