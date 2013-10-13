package altitude.crosscommunication.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;

import altitude.crosscommunication.packets.IPacket;


public class ClientThread extends Thread {
    public Socket socket = null;
    public DataOutputStream out = null;
    public DataInputStream in = null;
    public boolean connected = false;

    public Moderator plugin;
    private String hostName;

    public ClientThread(Moderator plugin, String hostname) {
        this.plugin = plugin;
        this.hostName = hostname;
        this.start();
    }

    public void run() {
        System.out.println("Attempting to connect to bungee server...");
        try {
            socket = new Socket(hostName, 51326);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.out.println("Chat server: Unknown host");
            Moderator.thread = null;
            return;
        } catch (IOException e) {
            System.out.println("Couldn't connect to chat server");
            Moderator.thread = null;
            return;
        }

        connected = true;
        System.out.println("Connected to chat server.");

        try {
            while (loop(in, out));
        } catch (Exception e) {
            System.out.println("Chat server: connection lost?");
            Moderator.thread = null;
            return;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
                socket.close();
                System.out.println("Disconnected from chat server.");
            } catch (IOException e) {
                System.out.println("Error closing socket");
            }
        }
        Moderator.thread = null;
    }

    public boolean loop(DataInputStream in, DataOutputStream out) throws Exception {
        short type = in.readShort();
        IPacket p = (IPacket) IPacket.knownPackets[type].newInstance();
        p.read(in);
        PacketReceivedEvent e = new PacketReceivedEvent(p, type);
        Bukkit.getPluginManager().callEvent(e);
        return true;
    }

    public void write(IPacket packet) {
        if (!connected)
            return;
        try {
            packet.write(out);
        } catch (IOException e) {
            System.out.println("Error writing packet " + packet.getClass() + ". Stack Trace Below:");
            System.out.println(e.getMessage());
            Moderator.thread = null;
            try {
                socket.close();
            } catch (IOException e1) {
            }
        }
    }
}
