package com.github.troid.ServerDataCrosser.packets;

import com.github.troid.ServerDataCrosser.packets.IPacket;
import com.github.troid.ServerDataCrosser.packets.PacketType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A packet that represents a infraction.
 *
 * @author molenzwiebel
 *
 */
public class InfractionPacket implements IPacket {

    public static enum PunishmentType {
        WARN(1, "Warn"), KICK(2, "Kick"), BAN(3, "Temporary Ban"), PERMANANT_BAN(4, "Permanent Ban");

        private int tier;
        private String displayName;

        private PunishmentType(int tier, String displayName) {
            this.tier = tier;
            this.displayName = displayName;
        }

        public int getTier() {
            return tier;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String readUTF) {
            displayName = readUTF;
        }
    }

    private static final PacketType packet = PacketType.PUNISHMENT;
    private String player;
    private String victim;
    private PunishmentType type;
    private String message;

    /**
     * Default constructor
     */
    public InfractionPacket() {

    }

    /**
     * Constructor for an Infraction Packet
     *
     * @param player The one giving a infraction
     * @param victim The one getting a infraction
     * @param type The type of the infraction (kick, ban, permban)
     * @param message The message
     */
    public InfractionPacket(String player, String victim, PunishmentType type, String message) {
        this.player = player;
        this.victim = victim;
        this.type = type;
        this.message = message;
    }

    public String getPlayer() {
        return player;
    }

    public String getVictim() {
        return victim;
    }

    public PunishmentType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeShort(packet.getId());
        out.writeUTF(player);
        out.writeUTF(victim);
        out.writeInt(type.ordinal());
        out.writeUTF(message);
        out.flush();
    }

    public void read(DataInputStream in) throws IOException {
        player = in.readUTF();
        victim = in.readUTF();
        type = PunishmentType.values()[in.readInt()];
        message = in.readUTF();
    }

    public String toString() {
        return "Packet{name=\"InfractionPacket\",punisher=\"" + player + "\",victim=\"" + victim
                + "\",message=\"" + message + "\"" + '}';
    }

}
