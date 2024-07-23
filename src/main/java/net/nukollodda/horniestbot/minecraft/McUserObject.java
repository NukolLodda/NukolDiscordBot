package net.nukollodda.horniestbot.minecraft;

public class McUserObject {
    private String username;
    private long dcId;
    public McUserObject(String pair) {
        if (pair.indexOf(' ') != pair.lastIndexOf(' ')) {
            throw new RuntimeException("Cannot have more than two spaces in a pair");
        }
        if (pair.indexOf(' ') < 0) {
            throw new RuntimeException("Cannot have an entire character in a pair");
        }
        String[] parts = pair.split("\\s");
        username = parts[0];
    }
}
