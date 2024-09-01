package net.nukollodda.crazybot.minecraft;

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
        dcId = Long.getLong(parts[1]);
    }

    public boolean dcidMatch(long id) {
        return dcId == id;
    }

    public void write() {
        // todo write to the file
    }
}
