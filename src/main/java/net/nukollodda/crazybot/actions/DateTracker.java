package net.nukollodda.crazybot.actions;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.LocalDate;
import java.time.Month;

public class DateTracker extends ListenerAdapter {
    private boolean hasDone = false;
    @Override
    public void onReady(ReadyEvent event) {
        Month month = LocalDate.now().getMonth();
        int day = LocalDate.now().getDayOfMonth();
        if (month.equals(Month.JUNE)) {
            if (day == 1 && !hasDone) {
                //event.getJDA().getChannelById(Channel.class, );
                hasDone = true;
            } else {
                hasDone = false;
            }
        }
    }
}
