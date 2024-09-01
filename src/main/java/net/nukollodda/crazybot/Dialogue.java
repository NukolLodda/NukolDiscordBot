package net.nukollodda.crazybot;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Dialogue {
    private static int route;
    public static void start(String msg, char gender, MessageChannelUnion channel) {}


    private static void route1(String msg, char gender, MessageChannelUnion channel) {}

    private static void endConversation() {
        route = 0;
    }
}
