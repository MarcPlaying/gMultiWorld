package spigot.mrtg.gmultiworld.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import spigot.mrtg.gmultiworld.cmd.cmdWorld;
import spigot.mrtg.gmultiworld.listener.onWorldLoad;
import spigot.mrtg.gmultiworld.util.u;

public class MultiWorld extends JavaPlugin{
	
	public static JavaPlugin jp = null;
	
	@Override
	public void onEnable() {
		jp = this;
		u.s.sendMessage(u.p + "is now active");
		
		registry();
	}
	
	public void registry() {
		Bukkit.getPluginManager().registerEvents(new onWorldLoad(), this);
		this.getCommand("world").setExecutor(new cmdWorld());
	}

}
