package com.explosivenetwork.ExplosiveMessenger.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerMessagePacket implements IPacket {
	public static final short id = 5;
	
	public String player;
	public String message;
	
	public PlayerMessagePacket() {
	}
	
	/**
	 * Packet sent when a certain message needs to be sent to a user. Checking if the user is online is done by the client.
	 * @param user
	 * @param command
	 */
	public PlayerMessagePacket(String player, String message) {
		this.player = player;
		this.message = message;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		player = in.readUTF();
		message = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeShort(id);
		out.writeUTF(player);
		out.writeUTF(message);
		out.flush();
	}
}
