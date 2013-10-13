package altitude.crosscommunication.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IPacket {
	public static Class<?>[] knownPackets = new Class<?>[] { AdminChatPacket.class, InfractionPacket.class };
	public void read(DataInputStream in) throws IOException;
	public void write(DataOutputStream out) throws IOException;
}
