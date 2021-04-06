package com.JavaBeast.AdminPanel.Panel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(args.length == 0){
            if(sender instanceof Player){
                Player player = (Player) sender;

                player.sendMessage("§7[§bAdminPanel§7] §aOpening AdminPanel.");
                new AdminPanel(player);

            }else{
                sender.sendMessage("§7[§bAdminPanel§7] §4You need to be a player to execute this command.");
            }
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("info")){

                sender.sendMessage("§b-----------> AdminPanel <-----------");
                sender.sendMessage("§bcommands:");
                sender.sendMessage("§b- /admin info");
                sender.sendMessage("§b  - display this info.");
                sender.sendMessage("");
                sender.sendMessage("§b- /admin");
                sender.sendMessage("§b  - open the AdminPanel.");

            }else{
                sender.sendMessage("§7[§bAdminPanel§7] §cUsage: /admin [info]");
            }
        }else{
            sender.sendMessage("§7[§bAdminPanel§7] §cUsage: /admin [info]");
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();

        if(strings.length == 1){
            list.add("info");
        }

        return list;
    }
}
