package net.nukollodda.horniestbot.actions;

import io.github.cdimascio.dotenv.Dotenv;
import net.nukollodda.horniestbot.Emojis;
import net.nukollodda.horniestbot.Helpers;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Reactor extends ListenerAdapter {
    public static final String SEX = "geschlechtsverkehr";
    private final Dotenv config;

    public Reactor(Dotenv config) {
        this.config = config;
    }

    private void react(@NotNull Message msg, Emoji emoji) {
        msg.addReaction(emoji).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().getName().equals("Horniest Bot")) return;
        String rawMsg = message.getContentRaw().toLowerCase();
        Emoji emoji = null;

        int ma = Helpers.findIndexEitherOf(rawMsg, config.get("WORLDLOT").split(" "));
        int mb = Helpers.findIndexEitherOf(rawMsg, "x", "と");
        int mc = Helpers.findIndexEitherOf(rawMsg, ma, config.get("LILLIPAD").split(" "));
        if (ma >= 0 && (mb > ma || mb > mc) && mc >= 0) {
            emoji = Helpers.containsOne(rawMsg, "kawaii", "uwu", "yaoi", "可愛", "やおい") ? Emojis.E_24 : Emojis.E_20;
        }

        int md = Helpers.findIndexEitherOf(rawMsg, config.get("BRICK").split(" "));
        int me = Helpers.findIndexEitherOf(rawMsg, config.get("TRUNK").split(" "));
        if (md >= 0 && (mb > md || mb > me) && me >= 0 ||
                Helpers.containsOne(rawMsg, "henkustuna ai", "bigoted love", "偏屈な愛")) {
            emoji = Emojis.E_36;
        }

        if (rawMsg.contains("fundy")) {
            emoji = Emojis.E_4;
        }

        if (rawMsg.contains("nukol is hot")) {
            emoji = Emojis.E_11;
        }

        if (rawMsg.contains("twink")) {
            emoji = Helpers.containsOne(rawMsg, "fuck", "sex") ? Emojis.E_30 : Emojis.E_32;
        }

        String authorId = event.getAuthor().getId();
        if (authorId.equals(config.get("SHADOW_ID"))) {
            if (rawMsg.contains("why") || rawMsg.contains("warum")) {
                emoji = Emojis.E_31;
            } else {
                int ind = rawMsg.indexOf("want");
                emoji = ind >= 0 && rawMsg.indexOf("no") < ind &&
                        (rawMsg.indexOf(SEX) > ind + 4 || rawMsg.indexOf("sex") > ind + 4) ? Emojis.E_25 : Emojis.E_5;
            }
        } else if (rawMsg.contains(SEX) && Helpers.containsOne(rawMsg, config.get("SHADOW").split(" "))) {
            emoji = Emojis.E_25;
        }
        if (rawMsg.equals(SEX)) emoji = Emojis.E_1;
        if (emoji != null) react(message, emoji);
    }
}
