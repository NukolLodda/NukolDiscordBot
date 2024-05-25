package net.nukollodda.horniestbot;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class Helpers {
    public static boolean containsOne(@NotNull String org, @NotNull String... strs) {
        return forLoopBoolean(org::contains, strs);
    }

    @Contract(pure = true)
    public static boolean containsAll(String org, @NotNull String... strs) {
        for (String str : strs) {
            if (!org.contains(str)) return false;
        }
        return true;
    }

    public static boolean isEither(@NotNull String org, @NotNull String... strs) {
        return forLoopBoolean(org::equals, strs);
    }

    public static boolean startsWithEither(@NotNull String org, @NotNull String... strs) {
        return forLoopBoolean(org::startsWith, strs);
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

    @SafeVarargs
    public static <T> boolean forLoopBoolean(Predicate<T> predicate, @NotNull T... types) {
        for (T type : types) {
            if (predicate.test(type)) return true;
        }
        return false;
    }
}
