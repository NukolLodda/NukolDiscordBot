package net.nukollodda.horniestbot.randomlist;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Names {
    @NotNull
    public static String getRandomName() {
        int num = new Random().nextInt(9);
        return switch (num) {
            case 0 -> "Charlotte";
            case 1 -> "James";
            case 2 -> "Micheal";
            case 3 -> "Hudson";
            case 4 -> "Max";
            case 5 -> "Sylvia";
            case 6 -> "Alan";
            case 7 -> "Jane";
            default -> "Ethan";
        };
    }
}
