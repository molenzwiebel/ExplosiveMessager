package com.explosivenetwork.ExplosiveMessenger.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerLeftPacket implements IPacket {
	public static final short id = 2;

	public String player = null;
	public String server = "";

	public PlayerLeftPacket() {
	}

	/**
	 * Represents a packet sent when a certain user leaves the game.
	 * Intended for admins, but can be used for friend join messages.
	 * [Server] player left the game
	 * @param player
	 * @param server
	 */
	public PlayerLeftPacket(String player, String server) {
		this.player = player;
		this.server = server;
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeShort(id);
		out.writeUTF(player);
		out.writeUTF(server);
		out.flush();
	}

	public void read(DataInputStream in) throws IOException {
		player = in.readUTF();
		server = in.readUTF();
	}

}
