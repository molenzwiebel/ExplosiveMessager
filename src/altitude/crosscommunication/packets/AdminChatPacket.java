package altitude.crosscommunication.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AdminChatPacket implements IPacket {

    public static final short id = 0;
    public String player;
    public String message;
    public String server;
    
    public AdminChatPacket() {
        
    }

    /**
     * A player chat packet, which represents a chat line. Not a command!
     * Used for example admin chat, this has all the data to send the following:
     * [Server] player: message
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
        out.writeShort(id);
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

}
