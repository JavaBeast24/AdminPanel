package com.JavaBeast.AdminPanel.Panel.GameOptions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameOptionsPanel {

    public GameOptionsPanel(Player player){
        player.openInventory(create_GameOptions(player));
    }

    private Inventory create_GameOptions(Player player){
        Inventory inventory = Bukkit.createInventory(null, 9, "§bGame Options");

        if(player.hasPermission("ap.panel.weatherchange")){
            inventory.setItem(0, create_WeatherChange());
        }

        if(player.hasPermission("ap.panel.changetime")){
            inventory.setItem(8, create_TimeChange(player));
        }

        if(player.hasPermission("ap.panel.stoptime")){
            inventory.setItem(7, create_TimeStop(player));
        }

        return inventory;
    }

    public static ItemStack create_WeatherChange(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§bClear Weather");
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack create_TimeStop(Player player){
        if(player.getWorld().getGameRuleValue("doDaylightCycle") == "true"){
            ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§bdoDaylightCycle");
            itemStack.setItemMeta(meta);

            return itemStack;
        }else{
            ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§bdoDaylightCycle");
            itemStack.setItemMeta(meta);

            return itemStack;
        }
    }

    public static ItemStack create_TimeChange(Player player){
        if(player.getLocation().getWorld().getTime() <= 13000){
            ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§bChange Time");
            itemStack.setItemMeta(meta);

            return itemStack;
        }else{
            ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§bChange Time");
            itemStack.setItemMeta(meta);

            return itemStack;
        }
    }

}
