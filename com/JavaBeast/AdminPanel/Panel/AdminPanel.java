package com.JavaBeast.AdminPanel.Panel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminPanel {

    public AdminPanel(Player player){
        player.openInventory(create_AdminPanel_Main(player));
    }

    public static Inventory create_AdminPanel_Main(Player player){
        Inventory inventory = Bukkit.createInventory(null, 9, "§bAdminPanel");

        if(player.hasPermission("ap.panel.serveroptions")){
            inventory.setItem(0, create_ServerOptions());
        }

        if(player.hasPermission("ap.panel.gameoptions")){
            inventory.setItem(4, create_GameOptions());
        }

        if(player.hasPermission("ap.panel.chatoptions")){
            inventory.setItem(8, create_ChatOptions());
        }

        return inventory;
    }

    public static ItemStack create_ServerOptions(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§8ServerOptions");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack create_GameOptions(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 9);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§3GameOptions");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static  ItemStack create_ChatOptions(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§eChatOptions");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
