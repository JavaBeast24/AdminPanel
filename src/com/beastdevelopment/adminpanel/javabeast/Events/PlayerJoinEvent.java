package com.beastdevelopment.adminpanel.javabeast.Events;

import com.beastdevelopment.adminpanel.javabeast.utils.permsys.PermissionLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e)  {
        Player p = e.getPlayer();
        PermissionLoader.loadPermissions(p);
    }

}
