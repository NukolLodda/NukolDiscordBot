package net.nukollodda.horniestbot;

import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Predicate;

public class Helpers {
    public static final int FILE_MP3 = 0;
    public static final int FILE_PNG = 1;
    public static final int FILE_MP4 = 2;

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

    @NotNull
    public static FileUpload createFileUpload(String path, int type) {
        String directory = switch (type) {
            case FILE_MP3 -> "audios/";
            case FILE_PNG -> "images/";
            case FILE_MP4 -> "videos/";
            default -> "";
        };
        String ender = switch (type) {
            case FILE_MP3 -> ".mp3";
            case FILE_PNG -> ".png";
            case FILE_MP4 -> ".mp4";
            default -> "";
        };
        File file = new File("src/main/resources/assets/" + directory + path + ender);
        return FileUpload.fromData(file);
    }

    @NotNull
    public static FileUpload createFileUpload(@NotNull String path) {
        String[] segments = path.split("\\.");
        String directory = switch (segments[segments.length - 1]) {
            case "png" -> "images/";
            case "mp3" -> "audios/";
            case "mp4" -> "videos/";
            default -> "";
        };
        File file = new File("src/main/resources/assets/" + directory + path);
        return FileUpload.fromData(file);
    }

    @NotNull
    @Contract("_ -> new")
    public static Scanner getReader(String path) throws FileNotFoundException {
        File file = new File("src/main/resources/data/" + path);
        return new Scanner(file);
    }
}