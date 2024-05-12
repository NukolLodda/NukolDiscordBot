package net.nukollodda.horniestbot.actions;

import net.nukollodda.horniestbot.Emojis;
import net.nukollodda.horniestbot.Helpers;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Reactor extends ListenerAdapter {
    public static final String SEX = "geschlechtsverkehr";

    private void react(Message msg, Emoji emoji) {
        msg.addReaction(emoji).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String rawMsg = message.getContentRaw().toLowerCase();

        int ma = Helpers.findIndexEitherOf(rawMsg, "worldlot", "フアン", "ジョン");
        int mb = Helpers.findIndexEitherOf(rawMsg, "x", "と");
        int mc = Helpers.findIndexEitherOf(rawMsg, ma,"lillipad", "elon", "e1on", "イーロン");
        Emoji emoji = null;
        if (ma >= 0 && (mb > ma || mb > mc) && mc >= 0) {
            emoji = Helpers.containsOne(rawMsg, "kawaii", "uwu", "yaoi", "可愛", "やおい") ? Emojis.E_25 : Emojis.E_21;
        }

        if (rawMsg.contains("fundy")) {
            emoji = Emojis.E_4;
        }

        if (rawMsg.contains("nukol is hot")) {
            emoji = Emojis.E_12;
        }

        String authorId = event.getAuthor().getId();
        if (authorId.equals("868223266890850324")) {
            if (rawMsg.contains("why") || rawMsg.contains("warum")) {
                emoji = Emojis.E_6;
            } else {
                int ind = rawMsg.indexOf("want");
                emoji = ind >= 0 && rawMsg.indexOf("no") < ind &&
                        (rawMsg.indexOf(SEX) > ind + 4 || rawMsg.indexOf("sex") > ind + 4) ? Emojis.E_26 : Emojis.E_5;
            }
        } else if (rawMsg.contains(SEX) && Helpers.containsOne(rawMsg, "schatten", "shadow", "sara")) {
            emoji = Emojis.E_26;
        }
        if (rawMsg.equals(SEX)) emoji = Emojis.E_1;
        if (emoji != null) react(message, emoji);
    }
}
