package ca.strendin.StrendinChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SCEmoteCommand implements CommandExecutor {
    private final StrendinChat plugin;
    
    public SCEmoteCommand(StrendinChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {
        if (sender instanceof Player) {
            handleEmote((Player) sender,args);
        } else {
            SCComms.logThis("Sorry, player command only");
        }
        return true;
    }    
    
    public void handleEmote(Player player, String[] args) { 
        if (args.length > 0) {            
            String emoteString = "*" + player.getDisplayName();
            for (String thisString : args) {
                emoteString = emoteString + " " + thisString;
            }            
            SCComms.sendEmote(player,emoteString);
            for (Entity nearbyEntity : player.getNearbyEntities(plugin.emoteDistance, plugin.emoteDistance, plugin.emoteDistance)) {
                // Check to see if the nearby entity is a player
                if (nearbyEntity instanceof Player) {
                    Player nearbyPlayer = (Player)nearbyEntity;
                    SCComms.sendEmote(nearbyPlayer,emoteString);                                                
                }                
            }
        }
    }    
}
