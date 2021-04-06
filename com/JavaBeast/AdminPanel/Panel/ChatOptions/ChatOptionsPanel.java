package com.JavaBeast.AdminPanel.Panel.ChatOptions;

import com.JavaBeast.AdminPanel.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChatOptionsPanel {

    public ChatOptionsPanel(Player player){
        player.openInventory(create_ChatOptionsPanel(player));
    }

    private Inventory create_ChatOptionsPanel(Player player){
        Inventory inventory = Bukkit.createInventory(null, 9, "§bChat Options");

        if(player.hasPermission("ap.panel.chatclear")){
            inventory.setItem(0, create_ChatClear());
        }

        if(player.hasPermission("ap.panel.chatdisable")){
            inventory.setItem(8, create_ChatDisable());
        }

        return inventory;
    }

    public static ItemStack create_ChatClear(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§bClear chat");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack create_ChatDisable(){
        ItemStack itemStack;

        if(Main.isChatDisabled){
            itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§bChatting");
            itemStack.setItemMeta(meta);
        }else{
            itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§bChatting");
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

}
