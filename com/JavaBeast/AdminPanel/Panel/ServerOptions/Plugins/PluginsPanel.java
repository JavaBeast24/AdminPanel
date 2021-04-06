package com.JavaBeast.AdminPanel.Panel.ServerOptions.Plugins;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class PluginsPanel {

    public PluginsPanel(Player player){
        player.openInventory(create_PluginPanel());
    }

    private Inventory create_PluginPanel(){

        int plugins_amount = Bukkit.getServer().getPluginManager().getPlugins().length;

        int rows = plugins_amount /  9;
        if(plugins_amount%9 > 0){
            rows++;
        }

        Inventory inventory = Bukkit.createInventory(null, 9*rows, "§bPlugins");

        for(Plugin plugin:Bukkit.getServer().getPluginManager().getPlugins()){
            if(plugin.isEnabled()){
                inventory.addItem(create_ActivePlugin(plugin.getName()));
            }else{
                inventory.addItem(create_InactivePlugin(plugin.getName()));
            }
        }

        return inventory;
    }

    public ItemStack create_ActivePlugin(String name){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§b"+name);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public ItemStack create_InactivePlugin(String name){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§b"+name);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
