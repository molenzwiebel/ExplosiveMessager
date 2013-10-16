package com.github.troid.ServerDataCrosser.packets;

/**
 * Enum to differentiate between packets with ease
 * 
 * @author neiljohari
 * 
 */
public enum PacketType {
  CHAT, PUNISHMENT, REPORT;


  private PacketType() {}

  public short getId() {
    return Short.parseShort(String.valueOf(ordinal()));
  }
}
