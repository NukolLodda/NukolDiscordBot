package net.nukollodda.crazybot.actions;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.utils.FileUpload;
import net.nukollodda.crazybot.Conversation;
import net.nukollodda.crazybot.Emojis;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nukollodda.crazybot.Helpers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Replier extends ListenerAdapter {
    private Conversation conversation;

    private void reply(Message msg, @NotNull String reply) {
        if (!reply.isEmpty()) msg.reply(reply).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        Channel channel = event.getChannel();
        String channelName = channel.getName().toLowerCase();
        if (message.getAuthor().getName().equals("Horniest Bot")) return;

        String rawMsg = message.getContentRaw().toLowerCase();
        char gender = getGender(event.getMember());

        if (rawMsg.equals("blahaj") || rawMsg.equals("blahay")) {
            reply(message, Emojis.E_22.getFormatted() + Emojis.E_23.getFormatted());
        }
        if (Helpers.containsOne(rawMsg, "palisa unpa li lon sitelen pi toki pona", "penis in toki pona writing",
                "pene en la escritura de toki pona", "penis in das skript von toki pona") && (channelName.startsWith("gnarly") || channelName.equals("secretsch"))) {
            FileUpload upload = Helpers.createFileUpload("palisa_unpa", Helpers.FILE_PNG);
            event.getChannel().sendMessage("palisa unpa in toki pona is written as\n(sitelen pona on the top, sitelen sitelen on the bottom)").addFiles(upload).queue();
        }
        if (rawMsg.equals("one drawing of worldlot x lillipad please")) {
            FileUpload upload = Helpers.createFileUpload("worldlot_lillipad", Helpers.FILE_PNG);
            String reference = switch (gender) {
                case 'm' -> "sir";
                case 'f' -> "ma'am";
                default -> "mx";
            };
            event.getMessage().reply("Here's your drawing " + reference + ", enjoy.").addFiles(upload).queue();
        }
        if (rawMsg.equals("gato interesante")) {
            FileUpload upload = Helpers.createFileUpload("gato_interesante", Helpers.FILE_PNG);
            event.getMessage().reply("El gato interesante de verdad").addFiles(upload).queue();
        }

        if (rawMsg.equals("the bratty lady coconut femininomenon")) {
            FileUpload upload = Helpers.createFileUpload("kokonut_femininomenon", Helpers.FILE_PNG);
            event.getMessage().reply("You think you just fell out of a coconut tree?! You exist in the context").addFiles(upload).queue();
        }
        /*if (channelName.startsWith("gnarly") || channelName.equals("secretsch")) {
            MessageChannelUnion channel = event.getChannel();
            if (conversation == null) {
                conversation = new Conversation();
                conversation.setChannel(channel);
            }
            conversation.addResponse(message);
            conversation.makeResponse();
        }*/
    }

    private static char getGender(Member member) {
        char gender = 'n';
        if (member != null) {
            List<Role> roles = member.getRoles();
            boolean hasMale = false;
            boolean hasFemale = false;
            for (Role r : roles) {
                if (r.getName().equals("He/Him")) hasMale = true;
                if (r.getName().equals("She/Her")) hasFemale = true;
            }
            if (hasMale != hasFemale) {
                gender = hasMale ? 'm' : 'f';
            }
        }
        return gender;
    }
}