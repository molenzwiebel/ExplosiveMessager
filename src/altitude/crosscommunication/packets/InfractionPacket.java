package altitude.crosscommunication.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class InfractionPacket implements IPacket {

    public static final short id = 1;
    public String player;
    public String victim;
    public String type;
    public String message;
    
    public InfractionPacket() {
        
    }

    /**
     * A packet that represents a infraction.
     * @param player The one giving a infraction
     * @param victim The one getting a infraction
     * @param type The type of the infraction (kick, ban, permban)
     * @param message The message
     */
    public InfractionPacket(String player, String victim, String type, String message) {
        this.player = player;
        this.victim = victim;
        this.type = type;
        this.message = message;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeShort(id);
        out.writeUTF(player);
        out.writeUTF(victim);
        out.writeUTF(type);
        out.writeUTF(message);
        out.flush();
    }

    public void read(DataInputStream in) throws IOException {
        player = in.readUTF();
        victim = in.readUTF();
        type = in.readUTF();
        message = in.readUTF();
    }

}
