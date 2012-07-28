package ca.strendin.StrendinChat;

import org.bukkit.entity.Player;

public class SCPermissions {
    public static boolean canAdmin(Player player) {
        return player.hasPermission("strendinchat.admin");
    }
    
    public static boolean canLoginSilently(Player player) {
        return player.hasPermission("strendinchat.invisible");
    }
}
