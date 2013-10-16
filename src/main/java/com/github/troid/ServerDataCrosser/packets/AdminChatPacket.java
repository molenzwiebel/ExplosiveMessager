package com.github.troid.ServerDataCrosser.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A player chat packet, which represents a chat line.
 * 
 * @author molenzwiebel
 * 
 */
// The chat line is not a command!
public class AdminChatPacket implements IPacket {

  public static final PacketType packet = PacketType.CHAT;
  public String player;
  public String message;
  public String server;

  /**
   * Default constructor
   */
  public AdminChatPacket() {

  }

  /**
   * Used for example admin chat, this has all the data to send the following: [Server] player:
   * message
   * 
   * @param player
   * @param server
   * @param message
   */
  public AdminChatPacket(String player, String from, String message) {
    this.player = player;
    this.server = from;
    this.message = message;
  }

  public void write(DataOutputStream out) throws IOException {
    out.writeShort(packet.getId());
    out.writeUTF(player);
    out.writeUTF(server);
    out.writeUTF(message);
    out.flush();
  }

  public void read(DataInputStream in) throws IOException {
    player = in.readUTF();
    server = in.readUTF();
    message = in.readUTF();
  }

  public String toString() {
    return "Packet{name=\"AdminChatPacket\",sender=\"" + player + "\",server=\"" + server
        + "\",message=\"" + message + "\"" + '}';
  }

}
