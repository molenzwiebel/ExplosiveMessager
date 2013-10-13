package com.explosivenetwork.ExplosiveMessenger.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerCommandPacket implements IPacket {
	public static final short id = 3;
	
	public String server;
	public String command;
	
	public ServerCommandPacket() {
	}
	
	/**
	 * A packet sent when a server needs to execute a command as console. User can be Console when sent by the server.
	 * @param user
	 * @param command
	 */
	public ServerCommandPacket(String server, String command) {
		this.server = server;
		this.command = command;
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		server = in.readUTF();
		command = in.readUTF();
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeShort(id);
		out.writeUTF(server);
		out.writeUTF(command);
		out.flush();
	}
}
