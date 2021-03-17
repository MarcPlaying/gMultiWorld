package spigot.mrtg.gmultiworld.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
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
		
		File folder = getServer().getWorldContainer();
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if(file.isDirectory()) {
		    	
		    	if(!(file.getName().contains("world")) || !!(file.getName().contains("world_nether")) || !(file.getName().contains("world_the_end")) || !(file.getName().contains("logs")) || !(file.getName().contains("plugins"))) {
		    		u.s.sendMessage(file.getAbsolutePath());
		    		File f = new File(file.getPath() + "\region");
		    		if(f.exists()) {
		    			u.s.sendMessage(file.getName());
		    			World loader = new WorldCreator(file.getName()).createWorld();
			    		u.s.sendMessage(u.p + "loaded world " + loader.getName());
		    		}
		    	}
		    }else {
		    	
		    }
		}

		registry();
	}
	
	public void registry() {
		Bukkit.getPluginManager().registerEvents(new onWorldLoad(), this);
		this.getCommand("world").setExecutor(new cmdWorld());
	}

}
