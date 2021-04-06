package com.JavaBeast.AdminPanel.Panel.ServerOptions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServerOptionsPanel {

    public ServerOptionsPanel(Player player){
        player.openInventory(create_ServerOptionsPanel(player));
    }

    private Inventory create_ServerOptionsPanel(Player player){
        Inventory inventory = Bukkit.createInventory(null, 9, "§bServerOptions");

        if(player.hasPermission("ap.panel.plugins")){
            inventory.setItem(0, create_Plugins());
        }

        if(player.hasPermission("ap.panel.reload")){
            inventory.setItem(8, create_Reload());
        }

        if(player.hasPermission("ap.panel.stop")){
            inventory.setItem(7, create_Stop());
        }

        return inventory;
    }

    public static ItemStack create_Plugins(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§bPlugins");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack create_Reload(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§aReload");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack create_Stop(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§cStop");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
