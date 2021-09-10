package com.beastdevelopment.adminpanel.javabeast;

import com.beastdevelopment.adminpanel.javabeast.commands.PanelCommand;
import com.beastdevelopment.adminpanel.javabeast.webpanel.WebPanel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends JavaPlugin {

    private static boolean webPanel = true;
    private static int webPort = 1997;

    private static WebPanel _webPanel;

    public static Main instance;

    public static boolean chatEnabled = true;

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

        try {
            if(webPanel) {
                String[] panels = new String[configuration.getStringList("panels").size()];
                _webPanel = new WebPanel(webPort, configuration.getStringList("panels").toArray(panels));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        getCommand("panel").setExecutor(new PanelCommand());

    }

    @Override
    public void onDisable() {
        _webPanel.getServer().stop(0);
    }
}
