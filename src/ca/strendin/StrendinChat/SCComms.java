package ca.strendin.StrendinChat;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class SCComms {
	public static final String pluginName = "MSTR_Chat";
    public static final Logger log = Logger.getLogger("Minecraft");
    private static ChatColor infoColor = ChatColor.AQUA;
    private static ChatColor errorColor = ChatColor.RED;
    private static ChatColor emoteColor = ChatColor.GRAY;
    private static ChatColor serverMsgColor = ChatColor.YELLOW;

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
        log.info(pluginName + " " + message);
    }
    
    public static void permDenyMsg(Player tothisplayer) {
        tothisplayer.sendMessage(errorColor + "You do not have permission to use that command");        
    }
    
    public static void sendMOTD(Player tothisplayer) {
        for (String thisLine : StrendinChat.messageOfTheDay) {
            tothisplayer.sendMessage(thisLine);
        }
    }
    
    public static void sendToAllPlayers(String message) {
        for (Player thisPlayer : Bukkit.getServer().getOnlinePlayers()) {
            thisPlayer.sendMessage(message);
        }        
    }
    
    public static void sendToOps(String message) {
        for(Player thisPlayer : org.bukkit.Bukkit.getServer().getOnlinePlayers()) {
            if (thisPlayer.isOp()) {
                thisPlayer.sendMessage(serverMsgColor + "To ops: " + message);
            }        
        }        
    }
    
    public static void sendOnlineList(Player player) {
        Server server = player.getServer();
        StringBuilder onlineList = new StringBuilder();
        
        onlineList.append(ChatColor.GRAY);
        onlineList.append("Online" + ChatColor.GRAY + " (" + server.getOnlinePlayers().length + "): ");
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
    
    public static void sendOfflineList(Player player) {
		Server server = player.getServer();
		StringBuilder offlineList = new StringBuilder();
		offlineList.append(ChatColor.GRAY);
        offlineList.append("Offline: ");
        
        for (OfflinePlayer thisPlayer : server.getOfflinePlayers()) {
            if (!thisPlayer.isOnline()) {	        	
	            offlineList.append(thisPlayer.getName());	            
	            offlineList.append(", ");          
            }            
        }
        

        offlineList.deleteCharAt(offlineList.length()-1);
        offlineList.deleteCharAt(offlineList.length()-1);
        player.sendMessage(offlineList.toString());
	}
    
    public static String onlyAlpha(String input) {
        
        String working = input;
        
        // only output alphabet characters and numbers - remove anything else        
        String REGEX = "[^a-z0-9:]";
        
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(working); // get a matcher object
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
          m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        
        working = sb.toString();
        return working;
    }
    
    
}
