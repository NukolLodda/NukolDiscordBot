package net.nukollodda.horniestbot;

import net.nukollodda.horniestbot.actions.CommandManager;
import net.nukollodda.horniestbot.actions.Reactor;
import net.nukollodda.horniestbot.actions.Replier;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class HorniestBot {
    private final Dotenv config;
    private final ShardManager shardManager;
    public HorniestBot() {
        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");
        System.out.println(config.get("SHADOW"));
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("geschlechtsverkehr"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();
        shardManager.addEventListener(new Reactor(config));
        shardManager.addEventListener(new Replier());
        shardManager.addEventListener(new CommandManager());
        // 593885999918161 - permission integer
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            HorniestBot bot = new HorniestBot();
        } catch (Exception e) {
            System.out.println("Du bist eine schwanz");
        }
    }
}