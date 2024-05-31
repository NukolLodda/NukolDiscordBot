package net.nukollodda.horniestbot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

public class ApplicableRoles {
    public static Role getRole(Guild guild, String name) {
        String lowerCase = name.toLowerCase();
        return switch (lowerCase) {
            case "he", "him", "his", "he/him" -> guild.getRoleById(1213559767168196709L);
            case "she", "her", "hers", "she/her" -> guild.getRoleById(1213559766106767470L);
            case "they", "them", "their", "theirs", "they/them" -> guild.getRoleById(1213559764857135114L);
            case "it", "its", "it/its" -> guild.getRoleById(1229208789241040956L);
            case "any", "all" -> guild.getRoleById(1213559761908277331L);
            case "ask" -> guild.getRoleById(1213559763246522408L);
            case "gnarly", "gnarly_shitter", "0" -> guild.getRoleById(1235958350336753724L);
            case "general", "general_peoples", "1" -> guild.getRoleById(1213242160380379176L);
            case "mental", "health", "mental_health", "2" -> guild.getRoleById(1235958507627614340L);
            default -> null;
        };
    }
}
