package ca.strendin.StrendinChat;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class SCComms {
    public static final Logger log = Logger.getLogger("Minecraft");
    private static ChatColor infoColor = ChatColor.AQUA;
    private static ChatColor errorColor = ChatColor.RED;
    private static ChatColor emoteColor = ChatColor.GRAY;

    public static void sendError(Player player, String message) {
        player.sendMessage(errorColor + message);        
    }
    
    public static void sendEmote(Player player, String message) {        
        player.sendMessage(emoteColor + message);        
    }
    
    public static void sendInfo(Player player, String message) {
        player.sendMessage(infoColor + message);
    }
    
    public static void sendConsole(String message) {
        System.out.println(message);
    }
    
    public static void logThis(String message) {
        log.info("[StrendinChat] " + message);
    }
    
    public static void permDenyMsg(Player tothisplayer) {
        tothisplayer.sendMessage(errorColor + "You do not have permission to use that command");        
    }
    
    public static void sendMOTD(Player tothisplayer) {
        for (String thisLine : StrendinChat.messageOfTheDay) {
            tothisplayer.sendMessage(thisLine);
        }
    }
    
    public static void sendOnlineList(Player player) {
        Server server = player.getServer();
        StringBuilder onlineList = new StringBuilder();
        
        onlineList.append(ChatColor.GRAY);
        onlineList.append("Online (" + server.getOnlinePlayers().length + "): ");
        onlineList.append(ChatColor.WHITE);
        for (Player thisPlayer : server.getOnlinePlayers()) {
            if (thisPlayer.isOp()) {
                onlineList.append(ChatColor.RED);                
            }
            onlineList.append(thisPlayer.getDisplayName());
            
            if (thisPlayer.isOp()) {
                onlineList.append(ChatColor.WHITE);                
            }
            onlineList.append(", ");
        }
        
        onlineList.deleteCharAt(onlineList.length()-1);
        onlineList.deleteCharAt(onlineList.length()-1);
        player.sendMessage(onlineList.toString());
    }
}
