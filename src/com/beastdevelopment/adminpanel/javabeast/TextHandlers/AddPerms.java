package com.beastdevelopment.adminpanel.javabeast.TextHandlers;

import com.beastdevelopment.adminpanel.javabeast.utils.permsys.PermissionLoader;
import com.beastdevelopment.adminpanel.javabeast.webpanel.AreaTextHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AddPerms implements AreaTextHandler {
    @Override
    public String generateText(String areaID, String[] args) {

        String player = "";

        for(String arg:args) {
            if(arg.startsWith("player")) {
                player = arg.split("=")[1];
            }
        }

        StringBuilder content = new StringBuilder();

        if(!player.equals("")) {
            Player p = Bukkit.getPlayer(player);
            if(p != null) {
                for(String addPerm: PermissionLoader.loadAddPermissions(p)) {
                    content.append(addPerm).append(",\n");
                }
            }
        }

        return content.toString();
    }
}
