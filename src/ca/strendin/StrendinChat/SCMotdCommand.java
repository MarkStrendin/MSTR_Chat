package ca.strendin.StrendinChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SCMotdCommand implements CommandExecutor {
    @SuppressWarnings("unused")
    private final StrendinChat plugin;
    
    public SCMotdCommand(StrendinChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2,
            String[] arg3) {
        if (sender instanceof Player) {
            SCComms.sendMOTD((Player)sender);
        } else {
            SCComms.logThis("Sorry, player command only");
        }
        
        return true;
    }

}
