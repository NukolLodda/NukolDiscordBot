package net.nukollodda.crazybot;

import net.nukollodda.crazybot.actions.CommandManager;
import net.nukollodda.crazybot.actions.Reactor;
import net.nukollodda.crazybot.actions.Replier;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class CrazyBot {
    private final Dotenv config;
    private final ShardManager shardManager;
    public CrazyBot() {
        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("geschlechtsverkehr"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();
        shardManager.addEventListener(new Reactor(config));
        shardManager.addEventListener(new Replier());
        shardManager.addEventListener(new CommandManager(config));
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            CrazyBot bot = new CrazyBot();
        } catch (Exception e) {
            System.out.println("Du bist eine schwanz");
        }
    }
}