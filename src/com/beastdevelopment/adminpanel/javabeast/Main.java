package com.beastdevelopment.adminpanel.javabeast;

import com.beastdevelopment.adminpanel.javabeast.Events.PlayerJoinEvent;
import com.beastdevelopment.adminpanel.javabeast.Listeners.AddPerms;
import com.beastdevelopment.adminpanel.javabeast.Listeners.RemovePerms;
import com.beastdevelopment.adminpanel.javabeast.commands.PanelCommand;
import com.beastdevelopment.adminpanel.javabeast.webpanel.AreaTextHandler;
import com.beastdevelopment.adminpanel.javabeast.webpanel.TextListener;
import com.beastdevelopment.adminpanel.javabeast.webpanel.WebPanel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends JavaPlugin {

    private static boolean webPanel = true;
    private static int webPort = 1997;

    private WebPanel _webPanel;

    public static Main instance;

    public void onEnable() {

        instance = this;

        FileConfiguration configuration = getConfig();
        // region <config>

        if(configuration.contains("webPanel")) {
            webPanel = configuration.getBoolean("webPanel");
        }else
            configuration.set("webPanel", webPanel);

        if(configuration.contains("webPort")) {
            webPort = configuration.getInt("webPort");
        }else
            configuration.set("webPort", webPort);

        if(!configuration.contains("panels")){
            configuration.set("panels", new ArrayList<String>());
        }

        saveConfig();

        // endregion
        loadPanels();

        getCommand("panel").setExecutor(new PanelCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), this);

        _webPanel.textHandler.put("addPerms", new com.beastdevelopment.adminpanel.javabeast.TextHandlers.AddPerms());
        _webPanel.textHandler.put("removePerms", new com.beastdevelopment.adminpanel.javabeast.TextHandlers.RemovePerms());

        _webPanel.listeners.put("addPerms", new AddPerms());
        _webPanel.listeners.put("removePerms", new RemovePerms());
    }

    public static void addTextHandler(String name, AreaTextHandler handler) {
        Main adminPanel = (Main) Bukkit.getPluginManager().getPlugin("AdminPanel");
        adminPanel._webPanel.textHandler.put(name, handler);
    }

    public static void addListener(String name, TextListener listener) {
        Main adminPanel = (Main)Bukkit.getPluginManager().getPlugin("AdminPanel");
        adminPanel._webPanel.listeners.put(name, listener);
    }

    public WebPanel getPanel() {
        return _webPanel;
    }

    private void loadPanels() {
        FileConfiguration configuration = instance.getConfig();

        try {
            if(webPanel) {
                String[] panels = new String[configuration.getStringList("panels").size()];
                _webPanel = new WebPanel(webPort, configuration.getStringList("panels").toArray(panels));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        _webPanel.getServer().stop(0);
    }
}
