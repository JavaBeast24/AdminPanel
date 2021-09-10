package com.beastdevelopment.adminpanel.javabeast.commands;

import com.beastdevelopment.adminpanel.javabeast.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.net.Socket;

public class PanelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player) {

            FileConfiguration config = Main.instance.getConfig();
            if(config.contains("usr."+sender.getName()+".pw")){

                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("google.com", 80));
                    String ip = socket.getLocalAddress().toString();
                    TextComponent message = new TextComponent("§7[§cAdminPanel§7]§2 ↠ panel ↞");
                    String url = "http:/"+ip+":"+config.get("webPort")+"/?usr="+sender.getName()+"&pw="+config.getString("usr."+sender.getName()+".pw");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                    ((Player) sender).spigot().sendMessage(message);
                }catch (Exception ignored) { }
            }else
                sender.sendMessage("§7[§cAdminPanel§7]§4 You have no registered account. (config.yml)");

        }else
            sender.sendMessage("§7[§cAdminPanel§7]§4 This command can only be used by a player.");

        return false;
    }
}
