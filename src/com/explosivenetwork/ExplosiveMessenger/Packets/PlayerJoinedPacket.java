package com.explosivenetwork.ExplosiveMessenger.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerJoinedPacket implements IPacket {
	public static final short id = 1;

	public String player = null;
	public String server = "";

	public PlayerJoinedPacket() {
	}

	/**
	 * Represents a packet sent when a certain user joined the game.
	 * Intended for admins, but can be used for friend join messages.
	 * [Server] player joined the game
	 * @param player
	 * @param server
	 */
	public PlayerJoinedPacket(String player, String server) {
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