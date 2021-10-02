package com.beastdevelopment.adminpanel.javabeast.utils.permsys;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class Group {

    public final String name;
    public final List<String> addPermissions;
    public final List<String> removePermissions;

    public Group(String name, List<String> addPermissions, List<String> removePermissions) {
        this.name = name;
        this.addPermissions = addPermissions;
        this.removePermissions = removePermissions;
    }

    public static Group loadGroup(String name) {
        String prefix = "group."+name;
        FileConfiguration config = Main.instance.getConfig();
        if(config.contains(prefix+".addPerms")&&config.contains(prefix+".removePerms")) {

            List<String> addPerms = config.getStringList(prefix+".addPerms");
            List<String> removePerms = config.getStringList(prefix+".removePerms");

            return new Group(name, addPerms, removePerms);

        }else
            return null;
    }

    public static String getGroup(Player player) {
        FileConfiguration configuration = Main.instance.getConfig();

        String group = "player";

        if(configuration.contains(player.getUniqueId()+".group")) {

            group = configuration.getString(player.getUniqueId()+".group");

        }else {
            configuration.set(player.getUniqueId()+".group", group);
            Main.instance.saveConfig();
        }

        return group;
    }

    public static void setGroup(Player player, String groupName) {
        FileConfiguration configuration = Main.instance.getConfig();
        configuration.set(player.getUniqueId()+".group", groupName);
        Main.instance.saveConfig();
    }

    public void save() {
        FileConfiguration config = Main.instance.getConfig();
        config.set("group."+name+".addPerms", addPermissions);
        config.set("group."+name+".removePerms", removePermissions);
        Main.instance.saveConfig();
    }

}
