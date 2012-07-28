package ca.strendin.StrendinChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class StrendinChat extends JavaPlugin {        
    private static String motdFileName = "motd.txt";
    public static ArrayList<String> messageOfTheDay = new ArrayList<String>();
    public static Properties configSettings = new Properties();
    public final int emoteDistance = 20;
    

    @Override
    public void onDisable() {
        System.out.println(this.getDescription().getName() + " disabled");
    }

    @Override
    public void onEnable() {
        System.out.println("Enabling " + this.getDescription().getName() + " v" + this.getDescription().getVersion() + "...");
         
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new SCPlayerListener(this), this);
        
        //pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener, Priority.Normal, this);
        //pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener, Priority.Normal, this);
        //pm.registerEvent(Event.Type.PLAYER_KICK, this.playerListener, Priority.Normal, this);        
        //pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Normal, this);        
       
        try {
            loadMOTDFile();
        } 
        catch(Exception ex) {
            SCComms.logThis("Could not load MOTD file");
            SCComms.logThis("Attempting to create new one...");            
            try {
                createMOTDFile();
                SCComms.logThis("New MOTD file created successfully");
                try {
                    loadMOTDFile();
                }
                catch (Exception ex3) {
                    SCComms.logThis("Fatal error loading MOTD file... ");
                }
            }
            catch (Exception ex2) {
                SCComms.logThis("Failed to create new MOTD file");                
            }            
        }
        
        // Commands
        getCommand("motd").setExecutor(new SCMotdCommand(this));
        getCommand("strendinchat").setExecutor(new SCAdminCommands(this));        
        getCommand("who").setExecutor(new SCListCommand(this));
        getCommand("wave").setExecutor(new SCWaveCommand(this));
        getCommand("cheer").setExecutor(new SCCheerCommand(this));
        getCommand("cry").setExecutor(new SCCryCommand(this));        
        getCommand("em").setExecutor(new SCEmoteCommand(this));
        
        
        System.out.println(" " + this.getDescription().getName() + " v" + this.getDescription().getVersion() + " enabled");
    }
   
    // Parse a string, adding formatting to it
    public String parseFormattedString(String input) {
        String returnMe = input;
        returnMe = returnMe.replaceAll("`r",ChatColor.RED.toString());
        returnMe = returnMe.replaceAll("`R",ChatColor.DARK_RED.toString());
        returnMe = returnMe.replaceAll("`y",ChatColor.YELLOW.toString());
        returnMe = returnMe.replaceAll("`Y",ChatColor.GOLD.toString());
        returnMe = returnMe.replaceAll("`g",ChatColor.GREEN.toString());
        returnMe = returnMe.replaceAll("`G",ChatColor.DARK_GREEN.toString());
        returnMe = returnMe.replaceAll("`c",ChatColor.AQUA.toString());
        returnMe = returnMe.replaceAll("`C",ChatColor.DARK_AQUA.toString());
        returnMe = returnMe.replaceAll("`b",ChatColor.BLUE.toString());
        returnMe = returnMe.replaceAll("`B",ChatColor.DARK_BLUE.toString());
        returnMe = returnMe.replaceAll("`p",ChatColor.LIGHT_PURPLE.toString());
        returnMe = returnMe.replaceAll("`P",ChatColor.DARK_PURPLE.toString());
        returnMe = returnMe.replaceAll("`0",ChatColor.BLACK.toString());
        returnMe = returnMe.replaceAll("`1",ChatColor.DARK_GRAY.toString());
        returnMe = returnMe.replaceAll("`2",ChatColor.GRAY.toString());
        returnMe = returnMe.replaceAll("`w",ChatColor.WHITE.toString());
        return returnMe;
    }
    
    public void loadMOTDFile() throws IOException {                
        File motdFile = new File(this.getDataFolder(), motdFileName);
        FileReader motdFileReader = new FileReader(motdFile);
                
                
        // Reset the motd
        messageOfTheDay.clear();
        
        // Load the new motd
        try {
            BufferedReader input = new BufferedReader(motdFileReader);
            try {                
                String line = null;
                while ((line = input.readLine()) != null) {
                    // Ignore comment lines
                    if (!(line.startsWith("#") || (line.startsWith(":")))) {
                        messageOfTheDay.add(parseFormattedString(line));
                    }                   
                }
            }
            finally {
                input.close();
            }
        }
        catch (Exception ex) {
            SCComms.logThis("Error opening message of the day file");
            SCComms.logThis(ex.getStackTrace().toString());
        }        
    }
    
    public final void createMOTDFile() throws IOException {        
        // Check to see if the directory is there
        
        File pluginDirectory = this.getDataFolder(); 
        
        if (pluginDirectory.exists() != true) {        
            pluginDirectory.mkdir();           
        }
        
        File motdFile = new File(pluginDirectory, motdFileName);
        
        if (motdFile.exists() != true) {
            motdFile.createNewFile();
        }
        
        BufferedWriter fB = new BufferedWriter(new FileWriter(motdFile));            
        fB.write("# Automatically generated MOTD file");fB.newLine();            
        fB.write("#  `r is the color code for red");fB.newLine();
        fB.write("#  `R is the color code for dark red");fB.newLine();
        fB.write("#  `y is the color code for yellow");fB.newLine();
        fB.write("#  `Y is the color code for dark yellow (gold)");fB.newLine();
        fB.write("#  `g is the color code for green");fB.newLine();
        fB.write("#  `G is the color code for dark green");fB.newLine();
        fB.write("#  `c is the color code for cyan/aqua");fB.newLine();
        fB.write("#  `C is the color code for dark cyan/aqua");fB.newLine();
        fB.write("#  `b is the color code for blue");fB.newLine();
        fB.write("#  `B is the color code for dark blue");fB.newLine();
        fB.write("#  `p is the color code for purple");fB.newLine();
        fB.write("#  `P is the color code for dark purple");fB.newLine();
        fB.write("#  `0 is the color code for black");fB.newLine();
        fB.write("#  `1 is the color code for dark grey");fB.newLine();
        fB.write("#  `2 is the color code for light grey");fB.newLine();
        fB.write("#  `w is the color code for white");fB.newLine();
        fB.write("#");fB.newLine();
        fB.write("`gWelcome to the server!");fB.newLine();
        fB.close();                   
    }
}
