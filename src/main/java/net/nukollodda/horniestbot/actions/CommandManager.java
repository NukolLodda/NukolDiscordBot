package net.nukollodda.horniestbot.actions;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {
    private static final String ERROR_MSG = "%s bitch%s u entered my hole wrong and it fucking hurts";
    private static final String ERROR_APPROPRIATE = "%s %s there is an error";
    private static final String INVALID_FORMAT = "\n(invalid format)";
    private static final String UNPROVIDED = "\n(Parameter not provided)";
    private static final String NONEXISTENT = "\n(File does not exist)";
    private static final String NO_ROLE = "\n(No such role exists or can be applied)";
    private static final String CANT_REMOVE = "\n(Role cannot be removed)";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    }
}
