package com.explosivenetwork.ExplosiveMessenger.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerBroadcastPacket implements IPacket {
	public static final short id = 4;
	
	public String server;
	public String message;
	
	public ServerBroadcastPacket() {
	}
	
	/**
	 * Packet sent when a command needs to be broadcasted. Contains server variable to make sure only the correct server responds.
	 * @param user
	 * @param command
	 */
	public ServerBroadcastPacket(String server, String message) {
		this.server = server;
		this.message = message;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		server = in.readUTF();
		message = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeShort(id);
		out.writeUTF(server);
		out.writeUTF(message);
		out.flush();
	}
}
