package spigot.mrtg.gmultiworld.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;

import spigot.mrtg.gmultiworld.main.MultiWorld;

public class WorldAPI {
	
	public void importWorld(String name, CommandSender p) {
		File file = new File(name + "/");
		if(!file.exists()) {
			p.sendMessage(u.p + "This world doesn't exist");
		}else {
			File f = new File(name+"/uid.dat");
			f.delete();
			MultiWorld.jp.getServer().createWorld(new WorldCreator(name));
			
		}
	}
	
	public void unLoadWorld(String world, CommandSender p) {
	    World w = Bukkit.getWorld(world);
	    if(!w.equals(null)) {
	        Bukkit.getServer().unloadWorld(w, true);
	        p.sendMessage(u.p + "This world ▇ §a"+w.getName()+" §7▇ is now disabled!");
	    }else {
	    	p.sendMessage(u.p + "This world don't exist");
	    }
	}
	
	public World create(String world, CommandSender p) {
		if(world.trim() == "")
			{ p.sendMessage(u.p + "Invalid Name!"); return null; }
		p.sendMessage(u.p + "Started create world! (This could take a while)");
		WorldCreator wc = new WorldCreator(world);
		World w = Bukkit.createWorld(wc);
		p.sendMessage(u.p + "Created World ▇§a "+w.getName());
		return w;
	}
	
	public void deleteWorld(String world, CommandSender p) {
		World w = Bukkit.getWorld(world);
	    if(!(w == null)) {
	        Bukkit.getServer().unloadWorld(w, true);
	        File deleteFolder = w.getWorldFolder();
	        Boolean delete = delete(deleteFolder);
	        if(delete == true) {
	        	p.sendMessage(u.p + "This world ▇ §a"+w.getName()+"§7 ▇ is now deleted!");
	        }else {
	        	p.sendMessage(u.p + "Can't disable World ▇ §a"+w.getName());
	        }
	    }else {
	    	p.sendMessage(u.p + "This world don't exist");
	    }
	}
	
	public void copyWorldAPI(World old, String neww, CommandSender p) {
		World w = old;
	    if(!w.equals(null)) {
	        File copyFolder = w.getWorldFolder();
	        File targetFolder = new File("new/");
	        copy(copyFolder, targetFolder);
	        importWorld(neww, p);
	        p.sendMessage(u.p + "Copied ▇ §a"+old.getName()+" ▇§7 to ▇§a" +neww);
	    }else {
	    	p.sendMessage(u.p + "This world doesn't exist");
	    }
	}
	
	//TODO: Fix ducking copy Worlds
	private void copy(File copyFolder, File targetFolder){
	    try {
	    	if (copyFolder.isDirectory()) {
		        copyDirectory(copyFolder, targetFolder);
		    } else {
		        copyFile(copyFolder, targetFolder);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void copyDirectory(File source, File target) throws IOException {
	    if (!target.exists()) {
	        target.mkdir();
	    }

	    for (String f : source.list()) {
	        copy(new File(source, f), new File(target, f));
	    }
	}

	private void copyFile(File source, File target) throws IOException {        
	    try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    ) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
	}

	
	private boolean delete(File f) {
		if(f.exists()) {
	          File files[] = f.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  delete(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(f.delete());
	}

}
