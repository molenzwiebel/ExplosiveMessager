package altitude.crosscommunication.client;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import altitude.crosscommunication.packets.IPacket;

public class PacketReceivedEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private IPacket packet;
	private short packetID;
	
	public PacketReceivedEvent(IPacket p, short packetId) {
		this.packet = p;
		this.packetID = packetId;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {       
		return handlers;   
	}
	
	public IPacket getPacket() {
		return packet;
	}
	
	public short getPacketId() {
		return packetID;
	}
}
