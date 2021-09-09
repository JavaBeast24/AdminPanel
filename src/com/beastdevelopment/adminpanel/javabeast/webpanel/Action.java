package com.beastdevelopment.adminpanel.javabeast.webpanel;

import com.beastdevelopment.adminpanel.javabeast.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Action {

    public static void performCommand(String cmd, String usr) {
        new BukkitRunnable() {
            @Override
            public void run() {
                //
                //your async code
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getPlayer(usr), cmd.replace("_", " "));
                    }
                }.runTask(Main.instance);
            }
        }.runTaskAsynchronously(Main.instance);
    }

}
