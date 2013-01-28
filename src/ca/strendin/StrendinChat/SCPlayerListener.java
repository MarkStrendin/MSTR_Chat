package ca.strendin.StrendinChat;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class SCPlayerListener  implements Listener {
	private ChatColor opNameColour = ChatColor.RED;
	private ChatColor nameColour = ChatColor.BLUE;
	
    public static StrendinChat plugin;
    
    public SCPlayerListener(StrendinChat thisplugin) {
        plugin = thisplugin;
    }    

    
    
    @EventHandler
    public  void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        String strMessage = event.getMessage();
        String strSender = event.getPlayer().getDisplayName();
        
        ChatColor uNameColor = nameColour;
        
        if (event.getPlayer().isOp()) {
            uNameColor = opNameColour;
        } 
        
        if (event.getPlayer().getLevel() >= 50) {
        	strSender = rainbowify(strSender);
        }
        
        String strAdjustedMessage = uNameColor + strSender + ChatColor.WHITE + ": " + strMessage;        
        
        SCComms.sendToAllPlayers(strAdjustedMessage);
        
        
        SCComms.sendConsole(event.getPlayer().getDisplayName() + ": " + event.getMessage());
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
        SCComms.sendToAllPlayers(ChatColor.YELLOW + event.getPlayer().getDisplayName() + " was kicked from the server");
    }

    @EventHandler
    public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent event) {    	
    	Player thisPlayer = event.getPlayer();
    	if (thisPlayer.getLevel() >= 25) {
    		StringBuilder levelUpMessage = new StringBuilder();
    		
    		levelUpMessage.append("*");
    		levelUpMessage.append(thisPlayer.getDisplayName());
    		levelUpMessage.append(" has reached level ");
    		levelUpMessage.append(thisPlayer.getLevel());
    		levelUpMessage.append("!");
    		
    		SCComms.sendToAllPlayers(colorify(levelUpMessage.toString(),thisPlayer.getLevel()));
    	}
    }
    
    
    private static String colorify(String text, int level) {
    	
    	if (level >= 50) {
    		return rainbowify(text);
    	} else if (level >= 45) {
    		return ChatColor.GOLD + "" + ChatColor.ITALIC + text;
    	} else if (level >= 40) {
    		return ChatColor.GREEN + "" + ChatColor.ITALIC + text;
    	} else if (level >= 35) {
    		return ChatColor.BLUE + "" + ChatColor.ITALIC +  text;
    	} else if (level >= 30) {
    		return ChatColor.AQUA + "" + ChatColor.ITALIC + text;
    	} else {
    		return ChatColor.GRAY + "" + ChatColor.ITALIC + text;    		
    	}
    	
    }
    
    
    private static String rainbowify(String thisText) {
		ChatColor[] colors = {
				ChatColor.RED,
				ChatColor.GOLD, 
				ChatColor.YELLOW,
				ChatColor.GREEN,
				ChatColor.BLUE,
				ChatColor.DARK_PURPLE};
		
		StringBuilder returnMe = new StringBuilder();
		int colourCounter = 0;
		Random generator = new Random();
		colourCounter = generator.nextInt(colors.length - 1);
		
		for (char thisChar : thisText.toCharArray()) {
			returnMe.append(colors[colourCounter]);
			returnMe.append(ChatColor.ITALIC);
			returnMe.append(thisChar);
			colourCounter++;
			if (colourCounter > (colors.length - 1)) {
				colourCounter = 0;
			}
		}		
		
		return returnMe.toString();
		
	}
    
    
    

}
