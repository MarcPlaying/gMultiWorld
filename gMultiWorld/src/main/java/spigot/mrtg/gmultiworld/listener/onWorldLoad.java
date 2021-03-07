package spigot.mrtg.gmultiworld.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import spigot.mrtg.gmultiworld.util.u;

public class onWorldLoad implements Listener {
	
	@EventHandler
	public void onWorldL(WorldLoadEvent e) {
		u.s.sendMessage(u.p + "Load the World §a"+e.getWorld().getName());
	}

}
