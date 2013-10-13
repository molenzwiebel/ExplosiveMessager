package com.explosivenetwork.ExplosiveMessenger.Client;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.explosivenetwork.ExplosiveMessenger.Packets.IPacket;
import com.explosivenetwork.ExplosiveMessenger.Packets.PlayerChatPacket;
import com.explosivenetwork.ExplosiveMessenger.Packets.PlayerJoinedPacket;
import com.explosivenetwork.ExplosiveMessenger.Packets.PlayerLeftPacket;
import com.explosivenetwork.ExplosiveMessenger.Packets.PlayerMessagePacket;
import com.explosivenetwork.ExplosiveMessenger.Packets.ServerBroadcastPacket;
import com.explosivenetwork.ExplosiveMessenger.Packets.ServerCommandPacket;
import com.google.common.base.Joiner;

public class ExplosiveMessenger extends JavaPlugin implements Listener {
	public static ClientThread thread;
	private static ExplosiveMessenger instance;
	public static String NAME = "Lobby";

	public static void connect(String hostName) {
		thread = new ClientThread(instance, hostName);
	}

	public static void sendPacket(IPacket p) {
		thread.write(p);
	}

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		connect("localhost");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		PlayerJoinedPacket p = new PlayerJoinedPacket(e.getPlayer().getName(), NAME);
		sendPacket(p);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		PlayerLeftPacket p = new PlayerLeftPacket(e.getPlayer().getName(), NAME);
		sendPacket(p);
	}
	
	@EventHandler
	public void onPacketReceive(PacketReceivedEvent e) {
		switch (e.getPacketId()) {
		case 0:
			PlayerChatPacket p = (PlayerChatPacket) e.getPacket();
			Bukkit.broadcastMessage("["+p.server+"] "+p.player+":"+p.message.replace(p.server, ""));
			break;
		case 1:
			PlayerJoinedPacket p2 = (PlayerJoinedPacket) e.getPacket();
			Bukkit.broadcastMessage("["+p2.server+"] "+p2.player+" joined the game");
			break;
		case 2:
			PlayerLeftPacket p3 = (PlayerLeftPacket) e.getPacket();
			Bukkit.broadcastMessage("["+p3.server+"] "+p3.player+" left the game");
			break;
		case 3:
			ServerCommandPacket p4 = (ServerCommandPacket) e.getPacket();
			if (p4.server.equalsIgnoreCase(NAME) || p4.server.equalsIgnoreCase("all"))
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), p4.command);
			break;
		case 4:
			ServerBroadcastPacket p5 = (ServerBroadcastPacket) e.getPacket();
			if (p5.server.equalsIgnoreCase(NAME) || p5.server.equalsIgnoreCase("all"))
				Bukkit.broadcastMessage(p5.message);
			break;
		case 5:
			PlayerMessagePacket p6 = (PlayerMessagePacket) e.getPacket();
			if (Bukkit.getPlayerExact(p6.player) != null)
				Bukkit.getPlayerExact(p6.player).sendMessage(p6.message);
			break;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("a")) {
			PlayerChatPacket p = new PlayerChatPacket(sender.getName(), args[0], Joiner.on(" ").join(args));
			sendPacket(p);
		}
		if (label.equalsIgnoreCase("br")) {
			ServerBroadcastPacket p = new ServerBroadcastPacket("all", Joiner.on(" ").join(args));
			sendPacket(p);
		}
		if (label.equalsIgnoreCase("applyv")) {
			Player p = Bukkit.getPlayer(args[0]);
			double vel1 = Double.parseDouble(args[1]);
			double vel2 = Double.parseDouble(args[2]);
			double vel3 = Double.parseDouble(args[3]);
			Vector current = new Vector(vel1, vel2, vel3);
			p.setVelocity(current);
		}
		return true;
	}
	
	@EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if(event.getRegainReason() == RegainReason.SATIATED || event.getRegainReason() == RegainReason.REGEN)
            event.setCancelled(true);
    }
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() == DamageCause.FALL)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onIceMelt(BlockFadeEvent e) {
		e.setCancelled(true);
	}
}
