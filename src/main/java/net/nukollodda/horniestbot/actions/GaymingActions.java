package net.nukollodda.horniestbot.actions;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nukollodda.horniestbot.Helpers;

public class GaymingActions extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getName().equals("mc-usernames")) {
            Guild guild = event.getGuild();
            Role role = guild.getRoleById(1263902990629077014L);
            if (role != null) {
                guild.addRoleToMember(event.getAuthor(), role).queue();
            }
            Helpers.addToTxtFile("usernames", event.getMessage().getContentRaw() + " " + event.getAuthor().getId(), true);
        }
    }
}
