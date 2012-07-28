package ca.strendin.StrendinChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SCAdminCommands implements CommandExecutor {
    private final StrendinChat plugin;
    
    public SCAdminCommands(StrendinChat plugin) {
        this.plugin = plugin;
    }
    
    private void sendUsage(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SCComms.sendInfo(player, "Usage:");
            SCComms.sendInfo(player, " /sc reload");
            SCComms.sendInfo(player, " /sc version");            
        } else {
            SCComms.sendConsole("Usage: ");
            SCComms.sendConsole(" /sc reload");
            SCComms.sendConsole(" /sc version");            
        }                
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String label,
            String[] args) {
        
        if (sender instanceof Player) {
            if (!SCPermissions.canAdmin((Player)sender)) {
                SCComms.permDenyMsg((Player)sender);
                return true;
            }
        }
        
        if (args.length > 0) {
            String param = args[0].toLowerCase();
            if (param.equals("reload")) {
                handleReloadCommand(sender,args);
            } else if (param.equals("version")) {
                handleVersionCommand(sender,args);                        
            } else {
                sendUsage(sender);
            }
        } else {
            sendUsage(sender);                                        
        }
            
        return true;
    }
    
    private void handleReloadCommand(CommandSender sender, String[] args) {
        SCComms.logThis("Reloading config...");
        try {
            plugin.loadMOTDFile();
        } 
        catch (Exception ex) {
            if (sender instanceof Player) {
                SCComms.sendError((Player)sender, "Error reloading config file");
                SCComms.sendError((Player)sender, "See server log for details");
            }
            
            SCComms.logThis("Error reloading config file:");
            SCComms.logThis(ex.getStackTrace().toString());
        }
        finally {
            if (sender instanceof Player) {
                SCComms.sendInfo((Player)sender, "Config file reloaded");
            } else {
                SCComms.logThis("Config file reloaded");                
            }
        }
    }
    
    private void handleVersionCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            SCComms.sendInfo((Player)sender,plugin.getDescription().getName() + " version " + plugin.getDescription().getVersion());
        } else {
            System.out.println(plugin.getDescription().getName() + " version " + plugin.getDescription().getVersion());            
        }
    }

}
