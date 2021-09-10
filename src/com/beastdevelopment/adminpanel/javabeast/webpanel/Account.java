package com.beastdevelopment.adminpanel.javabeast.webpanel;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class Account {

    public static boolean login(String usr, String pw, String panel) {
        FileConfiguration configuration = Main.instance.getConfig();
        if(configuration.contains("usr."+usr+".pw")) {
            if(configuration.get("usr."+usr+".pw").equals(pw)){
                if(configuration.contains("usr."+usr+".panels")){
                    return configuration.getStringList("usr." + usr + ".panels").contains(panel);
                }else
                    return false;
            }else
                return false;
        }else
            return false;
    }

}
