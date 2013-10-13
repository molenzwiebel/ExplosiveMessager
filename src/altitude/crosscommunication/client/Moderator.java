package altitude.crosscommunication.client;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import altitude.crosscommunication.packets.AdminChatPacket;
import altitude.crosscommunication.packets.IPacket;
import altitude.crosscommunication.packets.InfractionPacket;

public class Moderator extends JavaPlugin implements Listener {
    private static Moderator instance;
    
    //START OF CROSS SERVER STUFF
    public static ClientThread thread;
    public static void connect(String hostName) {
        thread = new ClientThread(instance, hostName);
    }

    public static void sendPacket(IPacket p) {
        thread.write(p);
    }
    //END OF CROSS SERVER STUFF

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        connect("localhost");
    }

    @EventHandler
    public void onPacketReceive(PacketReceivedEvent e) {
        switch (e.getPacketId()) {
        case 0:
            AdminChatPacket p = (AdminChatPacket) e.getPacket();
            //Handle packet
            break;
        case 1:
            InfractionPacket p2 = (InfractionPacket) e.getPacket();
            //Handle packet
            break;    
        }
    }
}