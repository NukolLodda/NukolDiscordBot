package net.nukollodda.horniestbot.actions;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.utils.FileUpload;
import net.nukollodda.horniestbot.Conversation;
import net.nukollodda.horniestbot.Emojis;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.nukollodda.horniestbot.Helpers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Replier extends ListenerAdapter {
    private static final String ERROR_MSG = "%s bitch%s u entered my hole wrong and it fucking hurts";
    private static final String ERROR_APPROPRIATE = "%s %s there is an error";
    private static final String INVALID_FORMAT = "\n(invalid format)";
    private static final String UNPROVIDED = "\n(Parameter not provided)";
    private static final String NONEXISTENT = "\n(File does not exist)";
    private static final String NO_ROLE = "\n(No such role exists or can be applied)";
    private static final String CANT_REMOVE = "\n(Role cannot be removed)";

    private Conversation conversation;

    private void reply(Message msg, @NotNull String reply) {
        if (!reply.isEmpty()) msg.reply(reply).queue();
    }

    private void replyInvalid(MessageChannelUnion channel, Message msg, String title, String reference) {
        boolean isGnarly = Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch");
        reply(msg, isGnarly ? ERROR_MSG.formatted(title, reference) : ERROR_APPROPRIATE.formatted(title, msg.getAuthor().getName()));
        sendMessage(channel, "try again" + INVALID_FORMAT);
    }

    private void replyUnprovided(MessageChannelUnion channel, Message msg, String title, String reference) {
        boolean isGnarly = Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch");
        reply(msg, isGnarly ? ERROR_MSG.formatted(title, reference) : ERROR_APPROPRIATE.formatted(title, msg.getAuthor().getName()));
        sendMessage(channel, "try again" + UNPROVIDED);
    }

    private void replyNonexistent(MessageChannelUnion channel, Message msg, String title, String reference) {
        boolean isGnarly = Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch");
        reply(msg, isGnarly ? ERROR_MSG.formatted(title, reference) : ERROR_APPROPRIATE.formatted(title, msg.getAuthor().getName()));
        sendMessage(channel, "try again" + NONEXISTENT);
    }

    private void replyNoRole(MessageChannelUnion channel, Message msg, String title, String reference) {
        boolean isGnarly = Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch");
        reply(msg, isGnarly ? ERROR_MSG.formatted(title, reference) : ERROR_APPROPRIATE.formatted(title, msg.getAuthor().getName()));
        sendMessage(channel, "try again" + NO_ROLE);
    }

    private void replyCantRemove(MessageChannelUnion channel, Message msg, String title, String reference) {
        boolean isGnarly = Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch");
        reply(msg, isGnarly ? ERROR_MSG.formatted(title, reference) : ERROR_APPROPRIATE.formatted(title, msg.getAuthor().getName()));
        sendMessage(channel, "try again" + CANT_REMOVE);
    }

    private void replyUnavail(Message msg) {
        reply(msg, "Sorry, this command is unavailable in this channel");
    }

    private void sendMessage(@NotNull MessageChannelUnion channel, String msg) {
        channel.sendMessage(msg).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (message.getAuthor().getName().equals("Horniest Bot")) return;

        String rawMsg = message.getContentRaw().toLowerCase();
        char gender = getGender(event.getMember());

        if (rawMsg.equals("blahaj") || rawMsg.equals("blahay")) {
            reply(message, Emojis.E_22.getFormatted() + Emojis.E_23.getFormatted());
        }
        if (Helpers.containsOne(rawMsg, "palisa unpa li lon sitelen pi toki pona", "penis in toki pona writing",
                "pene en la escritura de toki pona", "penis in das skript von toki pona")) {
            File file = new File("src/main/resources/assets/palisa_unpa.png");
            FileUpload upload = FileUpload.fromData(file);
            event.getChannel().sendMessage("palisa unpa in toki pona is written as\n(sitelen pona on the top, sitelen sitelen on the bottom)").addFiles(upload).queue();
        }
        if (rawMsg.equals("one drawing of worldlot x lillipad please")) {
            File file = new File("src/main/resources/assets/worldlot_lillipad.png");
            FileUpload upload = FileUpload.fromData(file);
            String reference = switch (gender) {
                case 'm' -> "sir";
                case 'f' -> "ma'am";
                default -> "mx";
            };
            event.getMessage().reply("Here's your drawing " + reference + ", enjoy.").addFiles(upload).queue();
        }
        if (rawMsg.startsWith("]hb ")) {
            command(event, rawMsg.substring(4).toLowerCase().split(" "));
        } /* else if (Helpers.isEither(event.getChannel().getName(), "gnarly-shits", "secretsch")) {
            MessageChannelUnion channel = event.getChannel();
            if (conversation == null) {
                conversation = new Conversation();
                conversation.setChannel(channel);
            }
            conversation.addResponse(message);
            conversation.makeResponse();
        }*/
    }

    private void command(@NotNull MessageReceivedEvent event, String[] cmdThread) {
        char gender = getGender(event.getMember());
        MessageChannelUnion channel = event.getChannel();
        boolean isGnarly = Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch");
        String title = switch (gender) {
            case 'm' -> "mr.";
            case 'f' -> "ms.";
            default -> "mx.";
        };
        String reference = switch (gender) {
            case 'm' -> "boy";
            case 'f' -> "girl";
            default -> "ie";
        };
        String part1 = cmdThread[0];
        switch (part1) {
            case "help" -> {
                if (cmdThread.length > 1) {
                    if (cmdThread.length == 2) {
                        String msg = helpOptions(cmdThread, title, reference);
                        reply(event.getMessage(), msg);
                    } else {
                        replyInvalid(channel, event.getMessage(), title, reference);
                    }
                } else {
                    String response = """
                            List of ]hb commands available:
                            help - for help... yay
                            links - to obtain some very interesting sites/videos
                            media - do you crave cursed shit? Well this is the command for you as it pulls a video straight from Nukol's photo gallery!
                            role - Applies a self assignable role
                            roles - list out all roles available on the server and a basic description of them all
                            pin - pins the message before the command
                            pronoun - assigns to your preferred pronouns
                            story - reads a story of either 偏屈な愛 by NukolLodda, The How to S** Series by Thomas Simons, or Smut.
                            unpin - unpins the message before the command
                            """;
                    reply(event.getMessage(), response);
                }
            }
            case "links" -> {
                if (isGnarly) {
                    if (cmdThread.length == 2) {
                        String reply = switch (cmdThread[1]) {
                            case "dick" -> "[www.desmos.com](<https://www.desmos.com/calculator/l1ubbmpgog>)";
                            case "geschlechtsverkehr" ->
                                    "[www.youtube.com](<https://www.youtube.com/watch?v=tYDGD1DatpI>)";
                            case "guide" -> "[www.youtube.com](<https://www.youtube.com/watch?v=K1TtnxaPRms>)";
                            case "guthib" -> "[www.github.com](<https://guthib.com/>)";
                            case "inconspicuous" -> "[www.youtube.com](<https://www.youtube.com/watch?v=dQw4w9WgXcQ>)";
                            case "music" ->
                                    "[www.youtube.com](<https://www.youtube.com/playlist?list=PL3ba1tjwDOJantpMuOSvjdKmdyXgRElrN>)";
                            case "mod" -> "[www.github.com](<https://github.com/NukolLodda/Modussy/tree/master>)";
                            case "progenitor" -> "[www.youtube.com](<https://youtu.be/byueHxMGiEw?feature=shared>)";
                            case "pumpkin" ->
                                    "[www.github.com](<https://github.com/NukolLodda/Modussy/blob/master/src/main/resources/assets/modussy/textures/block/girl_yess_pumpkin.png>)";
                            case "pubes" -> "[www.youtube.com](<https://youtu.be/oJILkXfYW0A?feature=shared>)";
                            case "smack" -> "[www.youtube.com](<https://www.youtube.com/watch?v=2tbd3E-pXUc>)";
                            case "smackraine" -> "[www.youtube.com](<https://youtu.be/iwLTWYpWoYI?feature=shared>)";
                            case "swears" ->
                                    "[docs.google.com](<https://docs.google.com/document/d/1Lpz-6EUnepcuv1ruOvsEQ1G5R3RPr446Q9uwNyuzlrU/edit>)";
                            case "tampon" ->
                                    "[www.tiktok.com](<https://www.tiktok.com/@nukollodda/video/7295349814198340907>)";
                            default -> ERROR_MSG.formatted(title, reference) + NONEXISTENT;
                        };
                        reply(event.getMessage(), reply);
                    } else if (cmdThread.length > 2) {
                        replyInvalid(channel, event.getMessage(), title, reference);
                    } else {
                        replyUnprovided(channel, event.getMessage(), title, reference);
                    }
                } else {
                    replyUnavail(event.getMessage());
                }
            }
            case "media" -> {
                if (isGnarly) {
                    if (cmdThread.length > 2) {
                        try {
                            File file = new File("src/main/resources/assets/" + cmdThread[2] + "." + cmdThread[1]);
                            FileUpload upload = FileUpload.fromData(file);
                            event.getMessage().reply("Here's your legendary piece of media "
                                    + title + " " + event.getAuthor().getName() + ", now enjoy").addFiles(upload).queue();
                        } catch (Exception e) {
                            replyNonexistent(channel, event.getMessage(), title, reference);
                        }
                    } else {
                        replyUnprovided(channel, event.getMessage(), title, reference);
                    }
                } else {
                    replyUnavail(event.getMessage());
                }
            }
            case "role" -> {
                if (cmdThread.length > 2) {
                    if (cmdThread[1].equals("remove") && cmdThread.length == 3) {
                        Role role = getAccessRole(event.getGuild(), cmdThread[2]);
                        if (role != null) {
                            try {
                                event.getGuild().removeRoleFromMember(event.getAuthor(), role).queue();
                                reply(event.getMessage(), "Role removed successfully");
                            } catch (Exception e) {
                                replyCantRemove(channel, event.getMessage(), title, reference);
                            }
                        } else {
                            replyCantRemove(channel, event.getMessage(), title, reference);
                        }
                    } else {
                        replyUnprovided(channel, event.getMessage(), title, reference);
                    }
                } else {
                    if (cmdThread[1].equals("remove")) {
                        replyUnprovided(channel, event.getMessage(), title, reference);
                    }
                    Role role = getAccessRole(event.getGuild(), cmdThread[1]);
                    if (role != null) {
                        event.getGuild().addRoleToMember(event.getAuthor(), role).queue();
                        reply(event.getMessage(), "Role applied successfully");
                    } else {
                        replyNoRole(channel, event.getMessage(), title, reference);
                    }
                }
            }
            case "roles" -> {
                if (cmdThread.length > 3) {
                    replyInvalid(channel, event.getMessage(), title, reference);
                }
                String msg = roleProperties(cmdThread, title, reference);
                reply(event.getMessage(), msg);
            }
            case "pin" -> {
                if (cmdThread.length == 1) {
                    if (event.getMessage().getType().equals(MessageType.INLINE_REPLY)) {
                        Message inline = event.getMessage().getReferencedMessage();
                        if (inline != null) {
                            if (inline.isPinned()) {
                                reply(event.getMessage(), "Bitch it was already pinned");
                            } else {
                                inline.pin().queue();
                            }
                        }
                    } else {
                        Message msg = event.getChannel().getHistoryBefore(event.getMessageId(), 2).complete().getRetrievedHistory().get(0);
                        if (msg.isPinned()) {
                            reply(event.getMessage(), "Bitch it was already pinned");
                        } else {
                            msg.pin().queue();
                        }
                    }
                } else {
                    replyInvalid(channel, event.getMessage(), title, reference);
                }
            }
            case "pronoun" -> {
                if (cmdThread.length > 2) {
                    if (cmdThread[1].equals("remove") && cmdThread.length == 3) {
                        Role role = getPronounRole(event.getGuild(), cmdThread[2]);
                        if (role != null) {
                            try {
                                event.getGuild().removeRoleFromMember(event.getAuthor(), role).queue();
                                reply(event.getMessage(), "Role removed successfully");
                            } catch (Exception e) {
                                replyCantRemove(channel, event.getMessage(), title, reference);
                            }
                        } else {
                            replyCantRemove(channel, event.getMessage(), title, reference);
                        }
                    } else {
                        replyInvalid(channel, event.getMessage(), title, reference);
                    }
                } else {
                    Role role = getPronounRole(event.getGuild(), cmdThread[1]);
                    if (role != null) {
                        event.getGuild().addRoleToMember(event.getAuthor(), role).queue();
                        reply(event.getMessage(), "Role applied successfully");
                    } else {
                        replyNoRole(channel, event.getMessage(), title, reference);
                    }
                }
            }
            case "story" -> {
                if (isGnarly) {
                    if (cmdThread.length == 3) {
                        boolean isPrologue = cmdThread[2].equals("prologue");
                        File file = new File("src/main/resources/data/" + cmdThread[1] +
                                (isPrologue ? "/prologue" : "/chapter" + cmdThread[2]) + ".txt");
                        boolean isPic = Helpers.isEither(cmdThread[2], "picture", "pic", "photo", "png", "image", "img", "cover");
                        if (isPic || !Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch", "spam")) {
                            try {
                                FileUpload upload = FileUpload.fromData(file);
                                event.getMessage().reply(isPic ? cmdThread[2] : isPrologue ? "prologue" : "Chapter " + cmdThread[2]).addFiles(upload).queue();
                            } catch (Exception e) {
                                replyNonexistent(channel, event.getMessage(), title, reference);
                            }
                        } else {
                            try {
                                Scanner scanner = new Scanner(file);
                                StringBuilder response = new StringBuilder(isPrologue ? "# Prologue #\n" : "# Chapter " + cmdThread[2] + " #\n");
                                while (scanner.hasNextLine()) {
                                    response.append(scanner.nextLine()).append("\n");
                                }
                                String chapter = response.toString();
                                int ini = 0;
                                int len = chapter.length();
                                Message msg = event.getMessage();
                                while (ini < len) {
                                    String sec = chapter.substring(ini, Math.min(ini + 2000, len));
                                    int ind = sec.lastIndexOf("\n");
                                    if (sec.isEmpty()) continue;
                                    reply(msg, sec.substring(0, ind));
                                    ini = ini + ind;
                                }
                            } catch (FileNotFoundException ignored) {
                                replyNonexistent(channel, event.getMessage(), title, reference);
                            }
                        }
                    } else if (cmdThread.length == 2) {
                        if (cmdThread[1].equals("name")) {
                            reply(event.getMessage(), "偏屈な愛");
                        } else {
                            boolean isPic = Helpers.isEither(cmdThread[1], "picture", "pic", "photo", "png", "image", "img", "cover");
                            File file = new File(isPic ? "src/main/resources/assets/henkutsuna_ai_cover.png" : "src/main/resources/data/bigotedlove/chapter" + cmdThread[1] + ".txt");
                            if (isPic || !Helpers.isEither(channel.getName(), "gnarly-shits", "secretsch")) {
                                try {
                                    FileUpload upload = FileUpload.fromData(file);
                                    event.getMessage().reply(isPic ? "Henkutsuna Ai Book Cover" : "Chapter " + cmdThread[1]).addFiles(upload).queue();
                                } catch (Exception e) {
                                    replyNonexistent(channel, event.getMessage(), title, reference);
                                }
                            } else {
                                try {
                                    Scanner scanner = new Scanner(file);
                                    StringBuilder response = new StringBuilder("# Chapter " + cmdThread[1] + " #\n");
                                    while (scanner.hasNextLine()) {
                                        response.append(scanner.nextLine()).append("\n");
                                    }
                                    String chapter = response.toString();
                                    int ini = 0;
                                    int len = chapter.length();
                                    Message msg = event.getMessage();
                                    while (ini < len) {
                                        String sec = chapter.substring(ini, Math.min(ini + 2000, len));
                                        int ind = sec.lastIndexOf("\n");
                                        if (sec.isEmpty()) continue;
                                        reply(msg, sec.substring(0, ind));
                                        ini = ini + ind;
                                    }
                                } catch (FileNotFoundException ignored) {
                                    replyNonexistent(channel, event.getMessage(), title, reference);
                                }
                            }
                        }
                    } else if (cmdThread.length > 3) {
                        replyInvalid(channel, event.getMessage(), title, reference);
                    } else {
                        replyUnprovided(channel, event.getMessage(), title, reference);
                    }
                } else {
                    replyUnavail(event.getMessage());
                }
            }
            case "unpin" -> {
                if (cmdThread.length == 1) {
                    if (event.getMessage().getType().equals(MessageType.INLINE_REPLY)) {
                        Message inline = event.getMessage().getReferencedMessage();
                        if (inline != null) {
                            if (inline.isPinned()) {
                                inline.unpin().queue();
                            } else {
                                reply(event.getMessage(), "Bitch it was never pinned");
                            }
                        }
                    } else {
                        Message msg = event.getChannel().getHistoryBefore(event.getMessageId(), 2).complete().getRetrievedHistory().get(0);
                        if (msg.isPinned()) {
                            msg.unpin().queue();
                        } else {
                            reply(event.getMessage(), "Bitch it was never pinned");
                        }
                    }
                } else {
                    replyInvalid(channel, event.getMessage(), title, reference);
                }
            }
            default -> reply(event.getMessage(), ERROR_MSG.formatted(title, reference));
        }
    }

    @Nullable
    private static Role getPronounRole(Guild guild, @NotNull String str) {
        return switch (str) {
            case "he", "him", "his", "he/him" -> guild.getRoleById(1213559767168196709L);
            case "she", "her", "hers", "she/her" -> guild.getRoleById(1213559766106767470L);
            case "they", "them", "their", "theirs", "they/them" -> guild.getRoleById(1213559764857135114L);
            case "it", "its", "it/its" -> guild.getRoleById(1229208789241040956L);
            case "any", "all" -> guild.getRoleById(1213559761908277331L);
            case "ask" -> guild.getRoleById(1213559763246522408L);
            default -> null;
        };
    }

    @Nullable
    private static Role getAccessRole(Guild guild, @NotNull String str) {
        return switch (str) {
            case "gnarly", "gnarly_shitter", "0" -> guild.getRoleById(1235958350336753724L);
            case "general", "general_peoples", "1" -> guild.getRoleById(1213242160380379176L);
            case "mental", "health", "mental_health", "2" -> guild.getRoleById(1235958507627614340L);
            default -> null;
        };
    }

    private static @NotNull String roleProperties(@NotNull String[] cmdThread, String title, String reference) {
        String msg;
        if (cmdThread.length == 2) {
            msg = switch (cmdThread[1]) {
                case "general", "general_people", "0" -> "Accessor role for a channel away from your younger siblings' prying eyes";
                case "gnarly", "gnarly_shitter", "1" -> "Accessor role for a channel of possible nsfw";
                case "mental", "health", "mental_health", "2" -> "Accessor role for a channel to discuss about mental problems and get things off your chest";
                case "he", "him", "his", "he/him" -> "He/Him pronouns";
                case "she", "her", "hers", "she/her" -> "She/Her pronouns";
                case "they", "them", "their", "theirs", "they/them" -> "They/Them pronouns";
                case "it", "its", "it/its" -> "It/Its pronouns";
                case "any", "all" -> "Role if you are fine with any pronouns";
                case "ask" -> "Role if you solely go by a neopronoun or is questioning";
                default -> ERROR_MSG.formatted(title, reference) + NO_ROLE;
            };
        } else {
            msg = """
                   List of self-roles:
                   General Peoples (general_peoples) - Place away from your younger siblings' prying eyes
                   Gnarly Shitter (gnarly_shitter)- Access to nsfw stuff
                   Mental Health (mental_health) - Access to an open place to discuss about mental problems and to get things off your chest
                                       \s
                   List of pronoun roles:
                   He/Him (he)
                   She/Her (she)
                   They/Them (they)
                   It/Its (it)
                   Any (any)
                   Ask (ask)
                   \s""";
        }
        return msg;
    }

    private static String helpOptions(@NotNull String[] cmdThread, String title, String reference) {
        String type = cmdThread[1];
        return switch (type) {
            case "help", "0" -> "The help command (cmd) is if you need help on anything";
            case "links", "1" -> "The links command (preset) is if you want to send a link or something.\nThe list includes: dick, geschlechtsverkehr, guide, inconspicuous, mod, music, progenitor, pumpkin, pubes, smack, smackraine, swears, and tampon";
            case "media", "2" -> "The media command (type, name) is to allow the bot to send cursed videos from NukolLodda's video gallery.\nThe list includes: britney_spears_edit (mov), cucumber_in_peach (mp4), cupcakke_binary_fission (mp4), hawt_poosay (mp4), and whatever_this_is (mov)";
            case "role", "3" -> "The role command (name) allows you to assign a role to yourself";
            case "roles", "4" -> "The roles command (name) lists out a list of available self-assignable roles on the server";
            case "pin", "5" -> "The pin command () pins the message before the command or the message the user replied to.";
            case "pronoun", "6" -> "The pronoun command (name) assigns you to your preferred pronouns";
            case "story", "7" -> "The story command (page) is for flipping to a page of the story 偏屈な愛, written by NukolLodda himself, by default, or if a book name is entered (name, page) then it's either howtosex# where # indicates 2, 3, or 4, or smut";
            case "unpin", "8" -> "The unpin command () unpins the message before the command or the message the user replied to, assuming it was already pinned";
            default -> ERROR_MSG.formatted(title, reference);
        };
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