package net.nukollodda.horniestbot;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Helpers {
    @Contract(pure = true)
    public static boolean containsOne(String org, @NotNull String... strs) {
        for (String str : strs) {
            if (org.contains(str)) return true;
        }
        return false;
    }

    @Contract(pure = true)
    public static boolean containsAll(String org, @NotNull String... strs) {
        for (String str : strs) {
            if (!org.contains(str)) return false;
        }
        return true;
    }

    @Contract(pure = true)
    public static boolean isEither(String org, @NotNull String... strs) {
        for (String str : strs) {
            if (org.equals(str)) return true;
        }
        return false;
    }

    @Contract(pure = true)
    public static boolean containsInOrder(String org, @NotNull String... strs) {
        int offset = 0;
        for (String str : strs) {
            int ind = org.indexOf(str);
            if (ind >= offset) offset = ind;
            else return false;
        }
        return true;
    }

    @Contract(pure = true)
    public static int findIndexEitherOf(String org, @NotNull String... strs) {
        for (String str : strs) {
            int ind = org.indexOf(str);
            if (ind >= 0) return ind;
        }
        return -1;
    }

    @Contract(pure = true)
    public static int findIndexEitherOf(String org, int start, @NotNull String... strs) {
        for (String str : strs) {
            int ind = org.indexOf(str);
            if (ind >= start) return ind;
        }
        return -1;
    }
}
