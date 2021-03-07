package spigot.mrtg.gmultiworld.cmd;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import spigot.mrtg.gmultiworld.util.*;

public class cmdWorld implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("gMultiWorld.world") || p.hasPermission("gMultiWorld.admin")) {
				WorldAPI api = new WorldAPI();
				if (args.length == 0) {
					p.sendMessage("§8--- §aGMultiWorld §8---");
					p.sendMessage("§2/world import <name> §7- §8Import a Map Folder");
					p.sendMessage("§2/world tp <name> <Player> §7- §8Teleport a Player to a World");
					p.sendMessage("§2/world tp <name> §7- §8Teleport you to a World");
					p.sendMessage("§2/world delete <name> §7- §8Delete a World");
					p.sendMessage("§2/world list§7- §8Show all worlds");
					p.sendMessage("§2/world setspawn §7- §8Set a Spawn for a World");
					p.sendMessage("§2/world spawn §7- §8Go back to World spawn");
					p.sendMessage("§2/world create <name> §7- §8Create a World");
					return true;
				} else {
					if (args[0].equalsIgnoreCase("import")) {
						
						if(args.length == 2) {
							api.importWorld(args[1], p);
							p.sendMessage(u.p + "Imported World §a"+args[1]);
							return true;
						}else {
							p.sendMessage(u.p + "That is not a correct World /world import <Name>");
							return true;
						}
					
					} else if(args[0].equalsIgnoreCase("tp")) {
						if(args.length == 3) {
							World tele = Bukkit.getWorld(args[1]);
							Player t = Bukkit.getPlayer(args[2]);
							try {
								t.isOnline();
							}catch(Exception e) {
								p.sendMessage(u.p + "The requested Player is not Online or doesen't exist");
								return true;
							}
							if((tele == null)) {
								p.sendMessage(u.p + "The requested World doesen't exist");
								return true;
							}
							t.teleport(tele.getSpawnLocation());
							p.sendMessage(u.p + "Teleported §a"+t.getName()+"§7 to §a"+tele.getName());
							return true;
						} else {
							World tele = Bukkit.getWorld(args[1]);
							if((tele == null)) {
								p.sendMessage(u.p + "The requested World doesen't exist");
								return true;
							}
							p.teleport(tele.getSpawnLocation());
							p.sendMessage(u.p + "Teleported to §a"+tele.getName());
							return true;
							}
					} else if(args[0].equalsIgnoreCase("delete")){
						World target = Bukkit.getWorld(args[1]);
						if(target == null) { p.sendMessage(u.p + "The requested World doesen't exist"); return true; }
						for(Player t2 : Bukkit.getOnlinePlayers()) {
							if(t2.getWorld() == target) {
								t2.teleport(Bukkit.getWorld("world").getSpawnLocation());
							}
						}
						api.unLoadWorld(target.getName(), sender);
						try {
							FileUtils.deleteDirectory(target.getWorldFolder());
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(u.p + "Deleted World §a"+target.getName());
						return true;
					} else if(args[0].equalsIgnoreCase("create")) {
						api.create(args.length < 2 ? "" : args[1], p);
						return true;
					} else if(args[0].equalsIgnoreCase("list")) {
						String[] worldNames = new String[Bukkit.getServer().getWorlds().size()];
						int count = 0;
						for(World w : Bukkit.getServer().getWorlds()){
						worldNames[count] = w.getName();
						count++;
						}
						for(String s : worldNames){
						p.sendMessage(u.p + "World: §a"+s);
						}
						return true;
					}else if(args[0].equalsIgnoreCase("setspawn")) {
						p.getWorld().setSpawnLocation(p.getLocation());
						p.sendMessage(u.p + "The Spawn Location of §a"+p.getWorld().getName()+ "§7 was set to§a "+p.getLocation().getBlockX() +"," + p.getLocation().getBlockY()+"," + p.getLocation().getBlockZ());
						return true;
					}else if(args[0].equalsIgnoreCase("spawn")){
						p.teleport(p.getWorld().getSpawnLocation());
						p.sendMessage(u.p + "You are now at the World Spawn");
						return true;
					}else {
						p.sendMessage(u.p + "§cWrong Syntax: All Commands:");
						p.sendMessage("§8--- §aGMultiWorld §8---");
						p.sendMessage("§2/world import <name> §7- §8Import a Map Folder");
						p.sendMessage("§2/world tp <name> <Player> §7- §8Teleport a Player to a World");
						p.sendMessage("§2/world tp <name> §7- §8Teleport you to a World");
						p.sendMessage("§2/world create <name> §7- §8Create a World");
						p.sendMessage("§2/world delete <name> §7- §8Delete a World");
						p.sendMessage("§2/world list§7- §8Show all worlds");
						p.sendMessage("§2/world setspawn §7- §8Set a Spawn for a World");
						p.sendMessage("§2/world spawn §7- §8Go back to World spawn");
						return true;
					}
					
				
			}
			}else {
				p.sendMessage(u.p+"§cYou don't have Permissions to Execute that Command");
				return true;
			}
		}else {
			u.s.sendMessage(u.p + "§cThe Console can't user /"+label);
			return true;
		}
		
	}
	

}
