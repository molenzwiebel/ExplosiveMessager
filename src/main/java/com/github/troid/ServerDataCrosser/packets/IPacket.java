package com.github.troid.ServerDataCrosser.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Interface that represents a Packet
 * 
 * @author molenzwiebel
 * 
 */
public interface IPacket {
  /**
   * Array of all known packets
   */
  public static Class<?>[] knownPackets = new Class<?>[] {AdminChatPacket.class,
      InfractionPacket.class, ReportPacket.class};

  /**
   * Reads input from a DataInputStream
   * 
   * @param in DataInputStream stram
   * @throws IOException
   */
  public void read(DataInputStream in) throws IOException;

  /**
   * Writes out data through a DataOutputStream
   * 
   * @param out DataOutputStream stream
   * @throws IOException
   */
  public void write(DataOutputStream out) throws IOException;
}
