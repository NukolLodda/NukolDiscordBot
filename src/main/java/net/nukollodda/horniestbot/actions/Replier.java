package net.nukollodda.horniestbot.actions;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.utils.FileUpload;
import net.nukollodda.horniestbot.Emojis;
import net.dv8tion.jda.api.entities.Message;
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
    private static final String ERROR_MSG = "%s Bitch%s you entered my hole wrong, and it fucking hurts, try again!";
    private static final String INVALID_FORMAT = "\n(invalid format)";
    private static final String UNPROVIDED = "\n(Parameter not provided)";
    private static final String NONEXISTENT = "\n(File does not exist)";
    private static final String NO_ROLE = "\n(No such role exists or can be applied)";
    private static final String CANT_REMOVE = "\n(Role cannot be removed)";

    private void reply(Message msg, @NotNull String reply) {
        if (!reply.isEmpty()) msg.reply(reply).queue();
    }

    private void replyInvalid(Message msg, String title, String reference) {
        reply(msg, ERROR_MSG.formatted(title, reference) + INVALID_FORMAT);
    }

    private void replyUnprovided(Message msg, String title, String reference) {
        reply(msg, ERROR_MSG.formatted(title, reference) + UNPROVIDED);
    }

    private void replyNonexistent(Message msg, String title, String reference) {
        reply(msg, ERROR_MSG.formatted(title, reference) + NONEXISTENT);
    }

    private void replyNoRole(Message msg, String title, String reference) {
        reply(msg, ERROR_MSG.formatted(title, reference) + NO_ROLE);
    }

    private void replyCantRemove(Message msg, String title, String reference) {
        reply(msg, ERROR_MSG.formatted(title, reference) + CANT_REMOVE);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String rawMsg = message.getContentRaw().toLowerCase();
        char gender = getGender(event.getMember());

        if (rawMsg.equals("blahaj") || rawMsg.equals("blahay")) {
            reply(message, Emojis.E_23.getFormatted() + Emojis.E_24.getFormatted());
        }
        if (Helpers.containsAll(rawMsg, "toki pona", "writ") &&
                Helpers.containsOne(rawMsg, "dick", "penis", "schwanz", "cock", "polla", "pene")) {
            File file = new File("src/main/resources/assets/palisa_unpa.png");
            FileUpload upload = FileUpload.fromData(file);
            event.getChannel().sendMessage("palisa unpa in toki pona is written as:").addFiles(upload).queue();
        }
        if (rawMsg.startsWith("]hb ")) {
            command(event, rawMsg.substring(4).toLowerCase().split(" "));
        }
        if (rawMsg.contains("one drawing of worldlot x lillipad please")) {
            File file = new File("src/main/resources/assets/worldlot_lillipad.png");
            FileUpload upload = FileUpload.fromData(file);
            String reference = switch (gender) {
                case 'm' -> "sir";
                case 'f' -> "ma'am";
                default -> "mx";
            };
            event.getMessage().reply("Here's your drawing " + reference + ", now enjoy ").addFiles(upload).queue();
        }
    }

    private void command(@NotNull MessageReceivedEvent event, String[] cmdThread) {
        char gender = getGender(event.getMember());
        String title = switch (gender) {
            case 'm' -> "Mr.";
            case 'f' -> "Ms.";
            default -> "Mx";
        };
        String reference = switch (gender) {
            case 'm' -> "boy";
            case 'f' -> "girl";
            default -> "ie";
        };
        String part1 = cmdThread[0];
        switch (part1) {
            case "fiction" -> {
                if (cmdThread.length == 2) {
                    File file = new File("src/main/resources/data/bigotedlove/chapter" + cmdThread[1] + ".txt");
                    try {
                        Scanner scanner = new Scanner(file);
                        StringBuilder response = new StringBuilder();
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
                    } catch (FileNotFoundException e) {
                        replyNonexistent(event.getMessage(), title, reference);
                    }
                } else if (cmdThread.length > 2){
                    replyInvalid(event.getMessage(), title, reference);
                } else {
                    replyUnprovided(event.getMessage(), title, reference);
                }
            }
            case "help" -> {
                if (cmdThread.length > 1) {
                    if (cmdThread.length == 2) {
                        String msg = helpOptions(cmdThread, title, reference);
                        reply(event.getMessage(), msg);
                    } else {
                        replyInvalid(event.getMessage(), title, reference);
                    }
                } else {
                    String response = """
                            List of ]hb commands available:
                            fiction - reads pages 1 to 5 of 偏屈な愛
                            help - for help... yay
                            media - do you crave cursed shit? Well this is the command for you as it pulls a video straight from Nukol's photo gallery!
                            role - Applies a self assignable role
                            roles - list out all roles available on the server and a basic description of them all
                            pronoun - assigns to your preferred pronouns
                            """;
                    reply(event.getMessage(), response);
                }
            }
            case "media" -> {
                if (cmdThread.length > 2) {
                    try {
                        File file = new File("src/main/resources/assets/" + cmdThread[2] + "." + cmdThread[1]);
                        FileUpload upload = FileUpload.fromData(file);
                        event.getMessage().reply("Here's your legendary piece of media "
                                + title + " " + event.getAuthor().getName() + ", now enjoy").addFiles(upload).queue();
                    } catch (Exception e) {
                        replyNonexistent(event.getMessage(), title, reference);
                    }
                } else {
                    replyUnprovided(event.getMessage(), title, reference);
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
                                replyCantRemove(event.getMessage(), title, reference);
                            }
                        } else {
                            replyCantRemove(event.getMessage(), title, reference);
                        }
                    } else {
                        replyUnprovided(event.getMessage(), title, reference);
                    }
                } else {
                    if (cmdThread[1].equals("remove")) {
                        replyUnprovided(event.getMessage(), title, reference);
                    }
                    Role role = getAccessRole(event.getGuild(), cmdThread[1]);
                    if (role != null) {
                        event.getGuild().addRoleToMember(event.getAuthor(), role).queue();
                        reply(event.getMessage(), "Role applied successfully");
                    } else {
                        replyNoRole(event.getMessage(), title, reference);
                    }
                }
            }
            case "roles" -> {
                if (cmdThread.length > 3) {
                    replyInvalid(event.getMessage(), title, reference);
                }
                String msg = roleProperties(cmdThread, title, reference);
                reply(event.getMessage(), msg);
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
                                replyCantRemove(event.getMessage(), title, reference);
                            }
                        } else {
                            replyCantRemove(event.getMessage(), title, reference);
                        }
                    } else {
                        replyInvalid(event.getMessage(), title, reference);
                    }
                } else {
                    Role role = getPronounRole(event.getGuild(), cmdThread[1]);
                    if (role != null) {
                        event.getGuild().addRoleToMember(event.getAuthor(), role).queue();
                        reply(event.getMessage(), "Role applied successfully");
                    } else {
                        replyNoRole(event.getMessage(), title, reference);
                    }
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
            case "fiction", "0" -> "The fiction command (page) is for flipping to a page of the story Bigoted Love, written by NukolLodda himself";
            case "help", "1" -> "The help command (cmd) is if you need help on anything";
            case "media", "2" -> "The media command (type, name) is to allow the bot to send cursed videos from NukolLodda's video gallery.\nThe list includes: britney_spears_edit (mov), cucumber_in_peach (mp4), cupcakke_binary_fission (mp4), hawt_poosay (mp4), and whatever_this_is (mov)";
            case "role", "3" -> "The role command (name) allows you to assign a role to yourself";
            case "roles", "4" -> "The roles command (name) lists out a list of available self-assignable roles on the server";
            case "pronoun", "5" -> "The pronoun command (name) assigns you to your preferred pronouns";
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