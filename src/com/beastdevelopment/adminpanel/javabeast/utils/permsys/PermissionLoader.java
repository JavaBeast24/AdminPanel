package com.beastdevelopment.adminpanel.javabeast.utils.permsys;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;

public class PermissionLoader {

    public static void loadPermissions(Player player) {
        String groupName = Group.getGroup(player);
        Group  group     = Group.loadGroup(groupName);

        List<String> addPermissions = new ArrayList<>();
        List<String> removePermissions = new ArrayList<>();


        if(group != null) {
            addPermissions    = group.addPermissions;
            removePermissions = group.removePermissions;
        }

        for(String perm:loadAddPermissions(player)) {
            addPermissions.add(perm);
            removePermissions.remove(perm);
        }

        for(String perm:loadRemovePermissions(player)) {
            addPermissions.remove(perm);
            removePermissions.add(perm);
        }

        PermissionAttachment att = player.addAttachment(Main.instance);

        for(String perm:addPermissions) {
            att.setPermission(perm, true);
        }

        for(String perm:removePermissions) {
            att.setPermission(perm, false);
        }

    }

    public static List<String> loadRemovePermissions(Player player) {
        FileConfiguration configuration = Main.instance.getConfig();
        if(configuration.contains(player.getUniqueId()+".removePerms")) {
            return configuration.getStringList(player.getUniqueId()+".removePerms");
        }else
            return new ArrayList<>();
    }

    public static List<String> loadAddPermissions(Player player) {
        FileConfiguration configuration = Main.instance.getConfig();
        if(configuration.contains(player.getUniqueId()+".addPerms")) {

            return configuration.getStringList(player.getUniqueId()+".addPerms");

        }else
            return new ArrayList<>();
    }

    public static void setPermissions(Player player, List<String> addPermissions, List<String> removePermissions) {
        FileConfiguration configuration = Main.instance.getConfig();
        configuration.set(player.getUniqueId()+".addPerms", addPermissions);
        configuration.set(player.getUniqueId()+".removePerms", removePermissions);
        Main.instance.saveConfig();
    }

}
