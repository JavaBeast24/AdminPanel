package com.beastdevelopment.adminpanel.javabeast.Listeners;

import com.beastdevelopment.adminpanel.javabeast.utils.permsys.PermissionLoader;
import com.beastdevelopment.adminpanel.javabeast.webpanel.TextListener;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddPerms implements TextListener {
    @Override
    public void onListen(String listener, String text, HashMap<String, String> args) {
        String[] lines = text.split("!LINE!");
        String pName = args.get("player");
        Player player = Bukkit.getPlayer(pName);

        if(player == null) {
            for(OfflinePlayer p:Bukkit.getOfflinePlayers()) {
                if(p.getName().equals(pName)) {
                    player = p.getPlayer();
                    break;
                }
            }
        }

        if(player == null) {
            return;
        }

        List<String> perms = Arrays.asList(lines);
        if(perms.contains("_")) {
            perms = new ArrayList<>();
        }

        PermissionLoader.setPermissions(player, perms, PermissionLoader.loadRemovePermissions(player));
        PermissionLoader.loadPermissions(player);
        player.sendMessage("§7[§2AdminPanel§7]§a your permissions are loaded!");
    }
}
