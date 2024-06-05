package net.nukollodda.horniestbot.actions;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandManager extends ListenerAdapter {
    private static final String ERROR_MSG = "%s bitch%s u entered my hole wrong and it fucking hurts";
    private static final String ERROR_APPROPRIATE = "%s %s there is an error";
    private static final String INVALID_FORMAT = "\n(invalid format)";
    private static final String UNPROVIDED = "\n(Parameter not provided)";
    private static final String NONEXISTENT = "\n(File does not exist)";
    private static final String NO_ROLE = "\n(No such role exists or can be applied)";
    private static final String CANT_REMOVE = "\n(Role cannot be removed)";
    private static final String INVALID_ID = "\n(ID is invalid)";

    private static final String NOT_AVIAL = "this command is not available in this channel";
    private static final String NOT_THIS = "this option is not available in this channel";

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

    private static void setCmds(@NotNull Event event) {
        List<CommandData> data = new ArrayList<>();
        OptionData roleOption = new OptionData(OptionType.STRING, "action", "whether to add or to remove a role", true)
                .addChoice("add", "add")
                .addChoice("remove", "remove");
        List<OptionData> pinOptions = List.of(
                new OptionData(OptionType.INTEGER, "offset", "how far back is the comment supposed to be pinned at", false),
                new OptionData(OptionType.STRING, "id", "the id of the message to be pinned", false));

        data.add(Commands.slash("help", "For help... yay").addOptions(
                new OptionData(OptionType.STRING, "command", "name of the command you inquire help about", false)
                        .addChoice("help", "help")
                        .addChoice("links", "links")
                        .addChoice("media", "media")
                        .addChoice("role", "role")
                        .addChoice("roles", "roles")
                        .addChoice("pin", "pin")
                        .addChoice("pronoun", "pronoun")
                        .addChoice("story", "story")
                        .addChoice("unpin", "unpin")
        ));
        data.add(Commands.slash("links", "To obtain some very interesting sites/videos").addOptions(
                new OptionData(OptionType.STRING, "title", "title of the link", true)
                        .addChoice("dick", "dick")
                        .addChoice("geschlechtsverkehr", "geschlechtsverkehr")
                        .addChoice("guide", "guide")
                        .addChoice("guthib", "guthib")
                        .addChoice("inconspicuous", "inconspicuous")
                        .addChoice("music", "music")
                        .addChoice("mod", "mod")
                        .addChoice("progenitor", "progenitor")
                        .addChoice("pumpkin", "pumpkin")
                        .addChoice("pubes", "pubes")
                        .addChoice("smack", "smack")
                        .addChoice("smackraine", "smackraine")
                        .addChoice("swears", "swears")
                        .addChoice("tampon", "tampon")
        ));
        data.add(Commands.slash("media", "Pulls a video straight from Nukol's photo gallery!").addOptions(
                new OptionData(OptionType.STRING, "name", "name of the piece of media in question", true)
                        .addChoice("britney spears edit", "britney_spears_edit")
                        .addChoice("cucumber in peach", "cucumber_in_peach")
                        .addChoice("deutsch", "deutsch")
                        .addChoice("hawt poosay", "hawt_poosay")
                        .addChoice("poosay", "poosay")
                        .addChoice("ranboob", "ranboob")
                        .addChoice("waffles", "waffles")
                        .addChoice("thing", "whatever_this_is")
        ));
        data.add(Commands.slash("role", "Applies a self assignable role").addOptions(roleOption,
                new OptionData(OptionType.STRING, "role", "role to add or remove", true)
                        .addChoice("general peoples", "general")
                        .addChoice("gnarly shitter", "gnarly")
                        .addChoice("mental health", "mental")
        ));
        data.add(Commands.slash("roles", "List out all roles available on the server and a basic description of them all"));
        data.add(Commands.slash("pin", "Pins the message before the command").addOptions(pinOptions));
        data.add(Commands.slash("pronoun", "Assigns to your preferred pronouns").addOptions(roleOption,
                new OptionData(OptionType.STRING, "role", "pronoun to add or remove", true)
                        .addChoice("he/him", "he")
                        .addChoice("she/her", "she")
                        .addChoice("they/them", "they")
                        .addChoice("it/its", "it")
                        .addChoice("ask me", "ask")
                        .addChoice("any/all", "any")
        ));
        data.add(Commands.slash("story", "Reads a story of either 偏屈な愛, The How to S** Series, or Smut.").addOptions(
                new OptionData(OptionType.STRING, "title", "name of the story you'd like to read", true)
                        .addChoice("偏屈な愛", "bigotedlove")
                        .addChoice("henkutsuna ai", "bigotedlove")
                        .addChoice("how to sex 2", "howtosex2")
                        .addChoice("how to sex 3", "howtosex3")
                        .addChoice("how to sex 4", "howtosex4")
                        .addChoice("smut", "smut"),
                new OptionData(OptionType.STRING, "additional", "would you like the prelude or the cover of the story", false)
                        .addChoice("cover", "cover")
                        .addChoice("prelude", "prelude")
                        .addChoice("name", "name"),
                new OptionData(OptionType.INTEGER, "chapter", "chapter number of the story you'd like to read", false)
        ));
        data.add(Commands.slash("unpin", "Unpins the message before the command").addOptions(pinOptions));
        event.getJDA().updateCommands().addCommands(data).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        Member member = event.getMember();
        char gender = getGender(member);
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
        String addressee = switch (gender) {
            case 'm' -> "sir";
            case 'f' -> "ma'am";
            default -> "mx";
        };
        MessageChannelUnion channel = event.getChannel();
        String channelName = channel.getName().toLowerCase();
        boolean rightChannel = channelName.equals("secretsch") || channelName.startsWith("gnarly");
        boolean prematurelyFired = false;
        String erMsg = rightChannel ?
                ERROR_MSG.formatted(title, reference) :
                ERROR_APPROPRIATE.formatted(title, event.getUser().getName());
        String msg = erMsg + INVALID_FORMAT;
        ReplyCallbackAction action = event.deferReply();
        switch (cmd) {
            case "help" -> {
                OptionMapping option = event.getOption("command");
                if (option != null) {
                    String op = option.getAsString();
                    msg = switch (op) {
                        case "help" -> "The help command (cmd) is if you need help on anything";
                        case "links" ->
                                rightChannel ? "The links command (preset) is if you want to send a link or something.\nThe list includes: dick, geschlechtsverkehr, guide, inconspicuous, mod, music, progenitor, pumpkin, pubes, smack, smackraine, swears, and tampon" : NOT_AVIAL;
                        case "media" ->
                                rightChannel ? "The media command (type, name) is to allow the bot to send cursed videos from NukolLodda's video gallery.\nThe list includes: britney_spears_edit (mov), cucumber_in_peach (mp4), cupcakke_binary_fission (mp4), hawt_poosay (mp4), and whatever_this_is (mov)" : NOT_AVIAL;
                        case "role" -> "The role command (name) allows you to assign a role to yourself";
                        case "roles" ->
                                "The roles command (name) lists out a list of available self-assignable roles on the server";
                        case "pin" ->
                                "The pin command (back) pins the message before the command or the message the user replied to.";
                        case "pronoun" -> "The pronoun command (name) assigns you to your preferred pronouns";
                        case "story" ->
                                rightChannel ? "The story command (page) is for flipping to a page of the story 偏屈な愛, written by NukolLodda himself, by default, or if a book name is entered (name, page) then it's either howtosex# where # indicates 2, 3, or 4, or smut" : NOT_AVIAL;
                        case "unpin" ->
                                "The unpin command (back) unpins the message before the command or the message the user replied to, assuming it was already pinned";
                        default -> erMsg;
                    };
                } else {
                    msg = """
                            List of slash commands available:
                            help - For help... yay
                            links - To obtain some very interesting sites/videos
                            media - Pulls a video straight from Nukol's photo gallery!
                            role - Applies a self assignable role
                            roles - List out all roles available on the server and a basic description of them all
                            pin - Pins the message before the command
                            pronoun - Assigns to your preferred pronouns
                            story - Reads a story of either 偏屈な愛, The How to S** Series, or Smut.
                            unpin - Unpins the message before the command""";
                }
            }
            case "links" -> {
                OptionMapping option = event.getOption("title");
                if (option != null) {
                    String op = option.getAsString();
                    msg = switch (op) {
                        case "dick" ->
                                rightChannel ? "[www.desmos.com](<https://www.desmos.com/calculator/l1ubbmpgog>)" : NOT_THIS;
                        case "geschlechtsverkehr" ->
                                rightChannel ? "[www.youtube.com](<https://www.youtube.com/watch?v=tYDGD1DatpI>)" : NOT_THIS;
                        case "guide" ->
                                rightChannel ? "[www.youtube.com](<https://www.youtube.com/watch?v=K1TtnxaPRms>)" : NOT_THIS;
                        case "guthib" -> "[www.github.com](<https://guthib.com/>)";
                        case "inconspicuous" -> "[www.youtube.com](<https://www.youtube.com/watch?v=dQw4w9WgXcQ>)";
                        case "music" ->
                                rightChannel ? "[www.youtube.com](<https://www.youtube.com/playlist?list=PL3ba1tjwDOJantpMuOSvjdKmdyXgRElrN>)" : NOT_THIS;
                        case "mod" ->
                                rightChannel ? "[www.github.com](<https://github.com/NukolLodda/Modussy/tree/master>)" : NOT_THIS;
                        case "progenitor" ->
                                rightChannel ? "[www.youtube.com](<https://youtu.be/byueHxMGiEw?feature=shared>)" : NOT_THIS;
                        case "pumpkin" ->
                                "[www.github.com](<https://github.com/NukolLodda/Modussy/blob/master/src/main/resources/assets/modussy/textures/block/girl_yess_pumpkin.png>)";
                        case "pubes" ->
                                rightChannel ? "[www.youtube.com](<https://youtu.be/oJILkXfYW0A?feature=shared>)" : NOT_THIS;
                        case "smack" ->
                                rightChannel ? "[www.youtube.com](<https://www.youtube.com/watch?v=2tbd3E-pXUc>)" : NOT_THIS;
                        case "smackraine" ->
                                rightChannel ? "[www.youtube.com](<https://youtu.be/iwLTWYpWoYI?feature=shared>)" : NOT_THIS;
                        case "swears" ->
                                rightChannel ? "[docs.google.com](<https://docs.google.com/document/d/1Lpz-6EUnepcuv1ruOvsEQ1G5R3RPr446Q9uwNyuzlrU/edit>)" : NOT_THIS;
                        case "tampon" ->
                                rightChannel ? "[www.tiktok.com](<https://www.tiktok.com/@nukollodda/video/7295349814198340907>)" : NOT_THIS;
                        default -> {
                            action = action.setEphemeral(true);
                            yield erMsg + NONEXISTENT;
                        }
                    };
                } else {
                    action = action.setEphemeral(true);
                    msg = erMsg + UNPROVIDED;
                }
            }
            case "media" -> {
                OptionMapping option = event.getOption("name");
                if (!rightChannel) {
                    action = action.setEphemeral(true);
                    msg = NOT_AVIAL;
                } else if (option != null) {
                    msg = "your beautiful piece of media will arrive shortly " + addressee;
                    String op = option.getAsString();
                    try {
                        File file = new File("src/main/resources/assets/" + op + ".mp4");
                        FileUpload upload = FileUpload.fromData(file);
                        action.queue();
                        event.getHook().sendMessage(msg).queue();
                        prematurelyFired = true;
                        channel.sendMessage("heres your legendary piece of media "
                                + title + " " + event.getUser().getName() + ", now enjoy").addFiles(upload).queue();
                    } catch (Exception ignored) {
                        action = action.setEphemeral(true);
                        msg = erMsg + NONEXISTENT;
                    }
                } else {
                    action = action.setEphemeral(true);
                    msg = erMsg + UNPROVIDED;
                }
            }
            case "role" -> {
                Guild guild = event.getGuild();
                if (guild != null && member != null) {
                    OptionMapping actionMap = event.getOption("action");
                    OptionMapping roleOption = event.getOption("role");
                    if (actionMap != null && roleOption != null) {
                        String act = actionMap.getAsString();
                        Role role = switch (roleOption.getAsString()) {
                            case "general" -> guild.getRoleById(1213242160380379176L);
                            case "gnarly" -> guild.getRoleById(1235958350336753724L);
                            case "mental" -> guild.getRoleById(1235958507627614340L);
                            default -> null;
                        };
                        if (role != null) {
                            if (act.equals("add")) {
                                try {
                                    guild.addRoleToMember(member.getUser(), role).queue();
                                    msg = "role added!";
                                } catch (Exception ignored) {
                                    action = action.setEphemeral(true);
                                    msg = erMsg + NO_ROLE;
                                }
                            } else {
                                try {
                                    guild.addRoleToMember(member.getUser(), role).queue();
                                    msg = "role removed!";
                                } catch (Exception ignored) {
                                    action = action.setEphemeral(true);
                                    msg = erMsg + CANT_REMOVE;
                                }
                            }
                        } else {
                            action = action.setEphemeral(true);
                            msg = erMsg + NO_ROLE;
                        }
                    }
                } else {
                    action = action.setEphemeral(true);
                    msg = erMsg;
                }
            }
            case "roles" -> msg = """
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
            case "pin" -> {
                OptionMapping option = event.getOption("offset");
                OptionMapping optionId = event.getOption("id");
                if (optionId != null) {
                    Message setToPin = channel.getHistory().getMessageById(optionId.getAsLong());
                    if (setToPin != null) {
                        action = action.setEphemeral(true);
                        if (setToPin.isPinned()) {
                            msg = "bitch this message was already pinned!";
                        } else {
                            setToPin.pin().queue();
                            msg = "Message pinned!";
                        }
                    } else {
                        msg = erMsg + INVALID_ID;
                    }
                } else {
                    action = action.setEphemeral(true);
                    int offset = 1;
                    if (option != null) {
                        offset += option.getAsInt();
                    }
                    Message setToPin = channel.getHistory().retrievePast(offset).complete().get(offset - 1);
                    if (setToPin.isPinned()) {
                        msg = "bitch this message was already pinned!";
                    } else {
                        setToPin.pin().queue();
                        msg = "Message pinned!";
                    }
                }
            }
            case "pronoun" -> {
                Guild guild = event.getGuild();
                if (guild != null && member != null) {
                    OptionMapping actionMap = event.getOption("action");
                    OptionMapping roleOption = event.getOption("role");
                    if (actionMap != null && roleOption != null) {
                        String act = actionMap.getAsString();
                        Role role = switch (roleOption.getAsString()) {
                            case "he" -> guild.getRoleById(1213559767168196709L);
                            case "she" -> guild.getRoleById(1213559766106767470L);
                            case "they" -> guild.getRoleById(1213559764857135114L);
                            case "it" -> guild.getRoleById(1229208789241040956L);
                            case "ask" -> guild.getRoleById(1213559763246522408L);
                            case "any" -> guild.getRoleById(1213559761908277331L);
                            default -> null;
                        };
                        if (role != null) {
                            if (act.equals("add")) {
                                try {
                                    guild.addRoleToMember(member.getUser(), role).queue();
                                    msg = "role added!";
                                } catch (Exception ignored) {
                                    action = action.setEphemeral(true);
                                    msg = erMsg + NO_ROLE;
                                }
                            } else {
                                try {
                                    guild.addRoleToMember(member.getUser(), role).queue();
                                    msg = "role removed!";
                                } catch (Exception ignored) {
                                    action = action.setEphemeral(true);
                                    msg = erMsg + CANT_REMOVE;
                                }
                            }
                        } else {
                            action = action.setEphemeral(true);
                            msg = erMsg + NO_ROLE;
                        }
                    }
                } else {
                    action = action.setEphemeral(true);
                    msg = erMsg;
                }
            }
            case "story" -> {
                OptionMapping option = event.getOption("title");
                action = action.setEphemeral(true);
                if (rightChannel) {
                    if (option != null) {
                        String name = option.getAsString();
                        OptionMapping page = event.getOption("chapter");
                        OptionMapping section = event.getOption("additional");
                        String storyName = switch (name) {
                            case "bigotedlove" -> "偏屈な愛";
                            case "howtosex2" -> "How to Sex 2";
                            case "howtosex3" -> "How to Sex 3";
                            case "howtosex4" -> "How to Sex 4";
                            case "smut" -> "Smut";
                            default -> "";
                        };
                        if (page != null) {
                            int chapter = page.getAsInt();
                            File file = new File("src/main/resources/data/" + name + "/chapter" + chapter + ".txt");
                            try {
                                Scanner scanner = new Scanner(file);
                                StringBuilder response = new StringBuilder("# " + storyName + ": Chapter " + chapter + " #\n");
                                while (scanner.hasNextLine()) {
                                    response.append(scanner.nextLine()).append("\n");
                                }
                                String chapterpg = response.toString();
                                int ini = 0;
                                int len = chapterpg.length();
                                action.queue();
                                event.getHook().sendMessage(storyName).queue();
                                prematurelyFired = true;
                                while (ini < len) {
                                    String sec = chapterpg.substring(ini, Math.min(ini + 2000, len));
                                    int ind = sec.lastIndexOf("\n");
                                    if (sec.isEmpty()) continue;
                                    String subsec = sec.substring(0, ind);
                                    if (!subsec.isEmpty()) channel.sendMessage(sec.substring(0, ind)).queue();
                                    ini = ini + ind;
                                }
                            } catch (FileNotFoundException ignored) {
                                msg = erMsg + NONEXISTENT;
                            }
                        } else if (section != null) {
                            String val = section.getAsString();
                            String loc = switch (val) {
                                case "cover" -> "assets/" + name + "_cover.png";
                                case "prelude" -> "data/" + name + "/prelude.txt";
                                default -> "";
                            };
                            if (loc.isEmpty()) {
                                if (val.equals("name")) {
                                    msg = storyName.isEmpty() ? erMsg + NONEXISTENT : storyName;
                                } else {
                                    msg = erMsg + UNPROVIDED;
                                }
                            } else {
                                File file = new File("src/main/resources/" + loc);
                                if (loc.endsWith(".txt")) {
                                    try {
                                        Scanner scanner = new Scanner(file);
                                        StringBuilder response = new StringBuilder("# Prelude #\n");
                                        action.queue();
                                        event.getHook().sendMessage("The prelude of " + storyName).queue();
                                        prematurelyFired = true;
                                        while (scanner.hasNextLine()) {
                                            response.append(scanner.nextLine()).append("\n");
                                        }
                                        String pg = response.toString();
                                        int ini = 0;
                                        int len = pg.length();
                                        while (ini < len) {
                                            String sec = pg.substring(ini, Math.min(ini + 2000, len));
                                            int ind = sec.lastIndexOf("\n");
                                            if (sec.isEmpty()) continue;
                                            String subsec = sec.substring(0, ind);
                                            if (!subsec.isEmpty()) channel.sendMessage(subsec).queue();
                                            ini = ini + ind;
                                        }
                                    } catch (FileNotFoundException ignored) {
                                        msg = erMsg + NONEXISTENT;
                                    }
                                } else {
                                    try {
                                        FileUpload upload = FileUpload.fromData(file);
                                        action.queue();
                                        event.getHook().sendMessage("heres your book cover").queue();
                                        channel.sendMessage(name + " book cover").addFiles(upload).queue();
                                        prematurelyFired = true;
                                    } catch (Exception ignored) {
                                        msg = erMsg + NONEXISTENT;
                                    }
                                }
                            }
                        } else {
                            msg = erMsg + INVALID_FORMAT;
                    }
                    } else {
                        msg = NOT_AVIAL;
                    }
                }
            }
            case "unpinned" -> {
                OptionMapping option = event.getOption("offset");
                OptionMapping optionId = event.getOption("id");
                if (optionId != null) {
                    Message setToPin = channel.getHistory().getMessageById(optionId.getAsLong());
                    if (setToPin != null) {
                        action = action.setEphemeral(true);
                        if (!setToPin.isPinned()) {
                            msg = "bitch this message was never pinned!";
                        } else {
                            setToPin.unpin().queue();
                            msg = "Message unpinned!";
                        }
                    } else {
                        msg = erMsg + INVALID_ID;
                    }
                } else {
                    action = action.setEphemeral(true);
                    int offset = 1;
                    if (option != null) {
                        offset += option.getAsInt();
                    }
                    Message setToPin = channel.getHistory().retrievePast(offset).complete().get(offset - 1);
                    if (!setToPin.isPinned()) {
                        msg = "bitch this message was never pinned!";
                    } else {
                        setToPin.unpin().queue();
                        msg = "Message unpinned!";
                    }
                }
            }
        }

        if (!prematurelyFired) {
            action.queue();
            event.getHook().sendMessage(msg).queue();
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        setCmds(event);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        setCmds(event);
    }
}
