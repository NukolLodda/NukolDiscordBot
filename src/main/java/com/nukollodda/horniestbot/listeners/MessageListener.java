package com.nukollodda.horniestbot.listeners;

import com.nukollodda.horniestbot.emoji.Emojis;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    // shadow's id 868223266890850324

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw().toLowerCase();
        int wi = msg.indexOf("worldlot");
        int xi = msg.indexOf("x");
        int li = msg.indexOf("lillipad");
        int ei = msg.indexOf("e1on");
        int ei2 = msg.indexOf("elon");
        if (wi >= 0 && xi > wi && (li > xi || ei > xi || ei2 > xi)) {
            event.getMessage().addReaction(msg.contains("kawaii") ? Emojis.E_25 : Emojis.E_21).queue();
        }

        if (msg.contains("fundy")) {
            event.getMessage().addReaction(Emojis.E_4).queue();
        }

        String authorId = event.getAuthor().getId();
        if (authorId.equals("868223266890850324")) {
            if (msg.contains("why") || msg.contains("warum")) {
                event.getMessage().addReaction(Emojis.E_6).queue();
            }
            int ind = msg.indexOf("want");
            if (ind >= 0 && (msg.indexOf("geschlechtsverkehr") > ind + 4 || msg.indexOf("sex") > ind + 4)
                    && msg.indexOf("no") < ind) {
                event.getMessage().addReaction(Emojis.E_26).queue();
            } else {
                event.getMessage().addReaction(Emojis.E_5).queue();
            }
        }
    }
}
