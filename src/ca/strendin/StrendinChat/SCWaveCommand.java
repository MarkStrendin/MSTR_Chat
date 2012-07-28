package ca.strendin.StrendinChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SCWaveCommand implements CommandExecutor {
    private final StrendinChat plugin;
    
    public SCWaveCommand(StrendinChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {
        if (sender instanceof Player) {
            handleWaveCommand((Player) sender,args);
        } else {
            SCComms.logThis("Sorry, player command only");
        }
        return true;
    }    
    
    public void handleWaveCommand(Player player, String[] args) {
        // Get a list of nearby players
        
        if (args.length > 0) {
            SCComms.sendEmote(player, "*You wave at " + args[0] + "!");                        
        } else {
            SCComms.sendEmote(player, "*You wave!");            
        }
        
        
        for (Entity nearbyEntity : player.getNearbyEntities(plugin.emoteDistance, plugin.emoteDistance, plugin.emoteDistance)) {
            // Check to see if the nearby entity is a player
            if (nearbyEntity instanceof Player) {
                Player nearbyPlayer = (Player)nearbyEntity;                
                // Check to see if the sender used a name as a parameter
                if (args.length > 0) {
                    // Check to see if the arg matches the player's name
                    if (args[0].compareToIgnoreCase(nearbyPlayer.getDisplayName()) == 0) {
                        SCComms.sendEmote(nearbyPlayer, "*" + player.getDisplayName() + " waves at you!");                            
                    } else {
                        SCComms.sendEmote(nearbyPlayer, "*" + player.getDisplayName() + " waves at " + args[0] + "!");
                    }                       
                } else {
                    SCComms.sendEmote(nearbyPlayer, "*" + player.getDisplayName() + " waves!");                    
                }
            }
        }        
    }    
}
