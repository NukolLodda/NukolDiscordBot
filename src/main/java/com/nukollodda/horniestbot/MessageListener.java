package com.nukollodda.horniestbot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    public static final String SEX = "geschlechtsverkehr";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw().toLowerCase();
        int ma1 = msg.indexOf("worldlot");

        int mb = msg.indexOf("x");

        int mc1 = msg.indexOf("lillipad");
        int mc2 = msg.indexOf("e1on");
        int mc3 = msg.indexOf("elon");
        if (ma1 >= 0 && mb > ma1 && (mc1 > mb || mc2 > mb || mc3 > mb)) {
            event.getMessage().addReaction(msg.contains("kawaii") ? Emojis.E_25 : Emojis.E_21).queue();
        }

        if (msg.contains("fundy")) {
            event.getMessage().addReaction(Emojis.E_4).queue();
        }

        if (msg.equals("blahaj") || msg.equals("blahay")) {
            event.getMessage().reply(Emojis.E_23.getFormatted() + Emojis.E_24.getFormatted()).queue();
        }

        if (msg.contains("nukol is hot")) {
            event.getMessage().addReaction(Emojis.E_12).queue();
        }

        String authorId = event.getAuthor().getId();
        if (authorId.equals("868223266890850324")) {
            if (msg.contains("why") || msg.contains("warum")) {
                event.getMessage().addReaction(Emojis.E_6).queue();
            }
            int ind = msg.indexOf("want");
            if (ind >= 0 && (msg.indexOf(SEX) > ind + 4 || msg.indexOf("sex") > ind + 4)
                    && msg.indexOf("no") < ind) {
                event.getMessage().addReaction(Emojis.E_26).queue();
            } else {
                event.getMessage().addReaction(Emojis.E_5).queue();
            }
        } else if (msg.contains(SEX)) {
            event.getMessage().addReaction(Emojis.E_1).queue();
        }
    }
}
