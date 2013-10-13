package com.explosivenetwork.ExplosiveMessenger.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IPacket {
	public static IPacket[] knownPackets = new IPacket[] { new PlayerChatPacket(), new PlayerJoinedPacket(), new PlayerLeftPacket(), new ServerCommandPacket(), new ServerBroadcastPacket(), new PlayerMessagePacket() };
	public void read(DataInputStream in) throws IOException;
	public void write(DataOutputStream out) throws IOException;
}
