package com.JavaBeast.AdminPanel;

import com.JavaBeast.AdminPanel.Panel.AdminCommand;
import com.JavaBeast.AdminPanel.Panel.AdminPanel;
import com.JavaBeast.AdminPanel.Panel.ChatOptions.ChatOptionsPanel;
import com.JavaBeast.AdminPanel.Panel.GameOptions.GameOptionsPanel;
import com.JavaBeast.AdminPanel.Panel.ServerOptions.Plugins.PluginsPanel;
import com.JavaBeast.AdminPanel.Panel.ServerOptions.ServerOptionsPanel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private static ConsoleCommandSender cmd;
    private static Main instance;

    public static boolean isChatDisabled = false;

    @Override
    public void onEnable() {
        Main.instance = this;
        Main.cmd = Bukkit.getConsoleSender();

        getCommand("admin").setExecutor(new AdminCommand());
        getCommand("admin").setTabCompleter(new AdminCommand());

        Bukkit.getPluginManager().registerEvents(new InvClickEvent(), this);

        FileConfiguration config = getConfig();
        long startTime = 10L;
        if(config.contains("startTime")){
            startTime = config.getLong("startTime");
        }else{
            config.set("startTime", 10L);
        }

        if(config.contains("isChatDisabled")){
            Main.isChatDisabled = config.getBoolean("isChatDisabled");
        }else{
            config.set("isChatDisabled", false);
        }

        saveConfig();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            List<String> disabledPlugins = config.getStringList("disabledPlugins");

            for(Plugin plugin:Bukkit.getPluginManager().getPlugins()){
                if(disabledPlugins.contains(plugin.getName())){
                    Bukkit.getPluginManager().disablePlugin(plugin);
                }
            }

        }, startTime);

        Main.cmd.sendMessage("§7[§bAdminPanel§7] §aStarted.");
    }

    public static ConsoleCommandSender getCmd(){
        return Main.cmd;
    }

    public static Main getInstance(){
        return instance;
    }
}

class InvClickEvent implements Listener{

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(Main.isChatDisabled && !event.getPlayer().hasPermission("ap.chat.bypass")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        try{

            if(event.getClickedInventory().getTitle().equals("§bAdminPanel")){

                event.setCancelled(true);

                if(event.getCurrentItem() != null){
                    if(event.getCurrentItem().equals(AdminPanel.create_ServerOptions())){
                        if(event.getWhoClicked().hasPermission("ap.panel.serveroptions")){

                            event.getWhoClicked().closeInventory();
                            new ServerOptionsPanel((Player) event.getWhoClicked());

                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to open the serveroptions!");
                        }
                    }else if(event.getCurrentItem().equals(AdminPanel.create_ChatOptions())){
                        if(event.getWhoClicked().hasPermission("ap.panel.chatoptions")){

                            event.getWhoClicked().closeInventory();
                            new ChatOptionsPanel((Player) event.getWhoClicked());

                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to open chat options!");
                        }
                    }else if(event.getCurrentItem().equals(AdminPanel.create_GameOptions())){
                        if(event.getWhoClicked().hasPermission("ap.panel.gameoptions")){

                            event.getWhoClicked().closeInventory();
                            new GameOptionsPanel((Player) event.getWhoClicked());

                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to open Gameoptions!");
                        }
                    }
                }

            }else if(event.getClickedInventory().getTitle().equals("§bServerOptions")){
                event.setCancelled(true);

                if(event.getCurrentItem() != null){

                    if(event.getCurrentItem().equals(ServerOptionsPanel.create_Reload())){

                        if(event.getWhoClicked().hasPermission("ap.panel.reload")){

                            Bukkit.getServer().reload();
                            event.getWhoClicked().sendMessage("§7[§bAdminPanel§7] §aThe server was reloaded.");

                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to reload the server!");
                        }

                    }else if(event.getCurrentItem().equals(ServerOptionsPanel.create_Stop())){
                        if(event.getWhoClicked().hasPermission("ap.panel.stop")){
                            Bukkit.getServer().shutdown();
                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to stop the server!");
                        }
                    }else if(event.getCurrentItem().equals(ServerOptionsPanel.create_Plugins())){
                        if(event.getWhoClicked().hasPermission("ap.panel.plugins")){

                            event.getWhoClicked().closeInventory();
                            new PluginsPanel((Player) event.getWhoClicked());

                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to manage plugins!");
                        }
                    }

                }

            }else if(event.getClickedInventory().getTitle().equals("§bPlugins")){
                event.setCancelled(true);

                if(event.getCurrentItem() != null){

                    if(event.getWhoClicked().hasPermission("ap.panel.manageplugin")){

                        String pluginName = event.getCurrentItem().getItemMeta().getDisplayName().replace("§b", "");

                        List<String> disabledPlugins = new ArrayList<>();

                        for(Plugin plugin:Bukkit.getPluginManager().getPlugins()){
                            if(plugin.getName().equals(pluginName)){
                                if(plugin.isEnabled() && plugin != Main.getInstance()){
                                    Bukkit.getPluginManager().disablePlugin(plugin);
                                    event.getWhoClicked().sendMessage("§7[§bAdminPanel§7] §bThe plugin "+pluginName+" was disabled!");
                                }else{
                                    Bukkit.getPluginManager().enablePlugin(plugin);
                                    event.getWhoClicked().sendMessage("§7[§bAdminPanel§7] §bThe plugin "+pluginName+" was enabled!");
                                }
                            }

                            if(!plugin.isEnabled()){
                                disabledPlugins.add(plugin.getName());
                            }
                        }

                        event.getWhoClicked().closeInventory();
                        new PluginsPanel((Player) event.getWhoClicked());

                        FileConfiguration config = Main.getInstance().getConfig();
                        config.set("disabledPlugins", disabledPlugins);
                        Main.getInstance().saveConfig();


                    }else{
                        ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                " §4You were kicked for bugusing.\n" +
                                " If this is an error please report it.\n" +
                                "§bhttps://discord.gg/Wv3kk3BSRM");

                        Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                ""+event.getWhoClicked().getName()+"" +
                                " tried to manage plugins!");
                    }

                }

            }else if(event.getClickedInventory().getTitle().equals("§bChat Options")){
                event.setCancelled(true);

                if(event.getCurrentItem().equals(ChatOptionsPanel.create_ChatClear())){

                    if(event.getWhoClicked().hasPermission("ap.panel.chatclear")){

                        for(int i = 0; i < 100; i++){
                            Bukkit.broadcastMessage("");
                        }

                    }else{
                        ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                " §4You were kicked for bugusing.\n" +
                                " If this is an error please report it.\n" +
                                "§bhttps://discord.gg/Wv3kk3BSRM");

                        Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                ""+event.getWhoClicked().getName()+"" +
                                " tried to clear the chat!");
                    }

                }else if(event.getCurrentItem().equals(ChatOptionsPanel.create_ChatDisable())){

                    if(event.getWhoClicked().hasPermission("ap.panel.chatdisable")){

                        Main.isChatDisabled = !Main.isChatDisabled;
                        event.getWhoClicked().sendMessage("§7[§bAdminPanel§7] §bChatting enabled: "+!Main.isChatDisabled);

                        event.getWhoClicked().closeInventory();
                        new ChatOptionsPanel((Player) event.getWhoClicked());

                        Main.getInstance().getConfig().set("isChatDisabled", Main.isChatDisabled);
                        Main.getInstance().saveConfig();

                    }else{
                        ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                " §4You were kicked for bugusing.\n" +
                                " If this is an error please report it.\n" +
                                "§bhttps://discord.gg/Wv3kk3BSRM");

                        Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                ""+event.getWhoClicked().getName()+"" +
                                " tried to disable/enable the chat!");
                    }

                }

            }else if(event.getClickedInventory().getTitle().equals("§bGame Options")){
                event.setCancelled(true);

                if(event.getCurrentItem() != null){

                    Player player = (Player) event.getWhoClicked();

                    if(event.getCurrentItem().equals(GameOptionsPanel.create_TimeChange((Player) event.getWhoClicked()))){


                        if(player.hasPermission("ap.panel.changetime")) {

                            if (player.getLocation().getWorld().getTime() <= 13000) {
                                player.getWorld().setTime(18000);
                                player.sendMessage("§7[§bAdminPanel§7] §aChanged time to night.");
                            } else {
                                player.getWorld().setTime(6000);
                                player.sendMessage("§7[§bAdminPanel§7] §aChanged time to day.");
                            }

                            event.getWhoClicked().closeInventory();
                            new GameOptionsPanel(player);
                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to change the time!");
                        }
                    }else if(event.getCurrentItem().equals(GameOptionsPanel.create_TimeStop(player))){

                        if(player.hasPermission("ap.panel.stoptime")){

                            if(player.getWorld().getGameRuleValue("doDaylightCycle") == "true"){
                                player.getWorld().setGameRuleValue("doDaylightCycle", "false");
                                player.sendMessage("§7[§bAdminPanel§7] §aDoDaylightCycle = false");
                            }else{
                                player.getWorld().setGameRuleValue("doDaylightCycle", "true");
                                player.sendMessage("§7[§bAdminPanel§7] §aDoDaylightCycle = true");
                            }

                            player.closeInventory();
                            new GameOptionsPanel(player);

                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to activate/deactivate daylightCycle!");
                        }

                    }else if(event.getCurrentItem().equals(GameOptionsPanel.create_WeatherChange())){

                        if(player.hasPermission("ap.panel.weatherchange")){

                            player.getWorld().setStorm(false);
                            player.getWorld().setThundering(false);

                            player.sendMessage("§7[§bAdminPanel§7] §aThe weather was cleared!");

                        }else{
                            ((Player) event.getWhoClicked()).kickPlayer("§7[§bAdminPanel§7]\n" +
                                    " §4You were kicked for bugusing.\n" +
                                    " If this is an error please report it.\n" +
                                    "§bhttps://discord.gg/Wv3kk3BSRM");

                            Main.getCmd().sendMessage("§7[§bAdminPanel§7] §4ATTENTION! Player " +
                                    ""+event.getWhoClicked().getName()+"" +
                                    " tried to clear the weather!");
                        }

                        event.getWhoClicked().closeInventory();
                        new GameOptionsPanel((Player) event.getWhoClicked());

                    }

                }

            }

        }catch(NullPointerException exception){ }
    }
}
