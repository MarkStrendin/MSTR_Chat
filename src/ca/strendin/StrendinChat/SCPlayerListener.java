package ca.strendin.StrendinChat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

public class SCPlayerListener  implements Listener {
    public static StrendinChat plugin;
    
    public SCPlayerListener(StrendinChat thisplugin) {
        plugin = thisplugin;
    }    

    public void sendToAllPlayers(String message) {
        for (Player thisPlayer : plugin.getServer().getOnlinePlayers()) {
            thisPlayer.sendMessage(message);
        }        
    }
    
    @EventHandler
    public  void onPlayerChat(PlayerChatEvent event) {
        event.setCancelled(true);
        String strMessage = event.getMessage();
        String strSender = event.getPlayer().getDisplayName();
        
        ChatColor uNameColor = ChatColor.BLUE;
        
        if (event.getPlayer().isOp()) {
            uNameColor = ChatColor.RED;                        
        } 
        
        String strAdjustedMessage = uNameColor + strSender + ChatColor.WHITE + ": " + strMessage;
        
        sendToAllPlayers(strAdjustedMessage);
        
        
        System.out.println(strSender + ": " + strMessage);
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        /*
        // Hijack join message
        event.setJoinMessage(null);
        if (!SCPermissions.canLoginSilently(player)) {
            String strJoinMessage = ChatColor.YELLOW + "*Poof* " + player.getDisplayName() + " appears!";        
            sendToAllPlayers(strJoinMessage);
        }
        
        */
        // Send Message of the day        
        SCComms.sendMOTD(player);
        
        // Send an empty line as a space between the MOTD and the online players list
        SCComms.sendInfo(player, "");
        
        // Send online player list
        SCComms.sendOnlineList(player);
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        // Hijack the message
        event.setLeaveMessage(null);
        sendToAllPlayers(ChatColor.YELLOW + event.getPlayer().getDisplayName() + " was kicked from the server");
    }

    /*
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
    } 
    */

}
