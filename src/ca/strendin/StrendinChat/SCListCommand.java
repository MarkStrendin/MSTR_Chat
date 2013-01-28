package ca.strendin.StrendinChat;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SCListCommand implements CommandExecutor {
    @SuppressWarnings("unused")
    private final StrendinChat plugin;
    
    public SCListCommand(StrendinChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2,
            String[] arg3) {
        
        if (sender instanceof Player) {
            SCComms.sendOnlineList((Player)sender);
            SCComms.sendOfflineList((Player)sender);
        } else {
            sendOnlineListToConsole(sender);
        }

        return true;
    }
    
    private void sendOnlineListToConsole(CommandSender sender) {
        Server server = sender.getServer();
        
        StringBuilder onlineList = new StringBuilder();       
                
        
        onlineList.append("Online (" + server.getOnlinePlayers().length + "): ");        
        for (Player thisPlayer : server.getOnlinePlayers()) {
            if (thisPlayer.isOp()) {
                onlineList.append("[OP]");                
            }
            onlineList.append(thisPlayer.getDisplayName());
            onlineList.append(", ");
        }
        
        onlineList.deleteCharAt(onlineList.length()-1);
        onlineList.deleteCharAt(onlineList.length()-1);
        
        
        StringBuilder offlineList = new StringBuilder();
        offlineList.append("Offline: ");
        
        for (OfflinePlayer thisPlayer : server.getOfflinePlayers()) {
            if (!thisPlayer.isOnline()) {
	            offlineList.append(thisPlayer.getName());
	            offlineList.append(", ");          
            }            
        }
        
        offlineList.deleteCharAt(offlineList.length()-1);
        offlineList.deleteCharAt(offlineList.length()-1);
       
         
        System.out.println(onlineList.toString());
        System.out.println(offlineList.toString());
    }

}
