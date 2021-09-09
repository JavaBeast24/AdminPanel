package com.beastdevelopment.adminpanel.javabeast.webpanel;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class Account {

    public static boolean login(String usr, String pw) {
        FileConfiguration configuration = Main.instance.getConfig();
        if(configuration.contains("usr."+usr+".pw")) {
            return configuration.get("usr."+usr+".pw").equals(pw);
        }else
            return false;
    }

}
