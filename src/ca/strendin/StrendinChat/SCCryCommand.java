package ca.strendin.StrendinChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SCCryCommand implements CommandExecutor {
    private final StrendinChat plugin;
    
    public SCCryCommand(StrendinChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {
        if (sender instanceof Player) {
            handleCryCommand((Player) sender,args);
        } else {
            SCComms.logThis("Sorry, player command only");
        }
        return true;
    }    
    
    public void handleCryCommand(Player player, String[] args) {
        // Get a list of nearby players
        
        if (args.length > 0) {
            SCComms.sendEmote(player, "*" + "You cry on " + args[0] + "'s shoulder :(");                        
        } else {
            SCComms.sendEmote(player, "*" + "You cry :(");            
        }
        
        
        for (Entity nearbyEntity : player.getNearbyEntities(plugin.emoteDistance, plugin.emoteDistance, plugin.emoteDistance)) {
            // Check to see if the nearby entity is a player
            if (nearbyEntity instanceof Player) {
                Player nearbyPlayer = (Player)nearbyEntity;                
                // Check to see if the sender used a name as a parameter
                if (args.length > 0) {
                    // Check to see if the arg matches the player's name
                    if (args[0].compareToIgnoreCase(nearbyPlayer.getDisplayName()) == 0) {
                        SCComms.sendEmote(nearbyPlayer, "*" + player.getDisplayName() + " cries on your shoulder :(");                            
                    } else {
                        SCComms.sendEmote(nearbyPlayer, "*" + player.getDisplayName() + " cries on " + args[0] + "'s shoulder :(");
                    }                       
                } else {
                    SCComms.sendEmote(nearbyPlayer, "*" + player.getDisplayName() + " cries :(");                    
                }
            }
        }  
    }    
}
