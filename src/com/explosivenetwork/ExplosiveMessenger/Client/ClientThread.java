package com.explosivenetwork.ExplosiveMessenger.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;

import com.explosivenetwork.ExplosiveMessenger.Packets.IPacket;


public class ClientThread extends Thread {
	public Socket socket = null;
	public DataOutputStream out = null;
	public DataInputStream in = null;
	public boolean connected = false;

	public ExplosiveMessenger plugin;
	private String hostName;

	public ClientThread(ExplosiveMessenger plugin, String hostname) {
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
			ExplosiveMessenger.thread = null;
			return;
		} catch (IOException e) {
			System.out.println("Couldn't connect to chat server");
			ExplosiveMessenger.thread = null;
			return;
		}

		connected = true;
		System.out.println("Connected to chat server.");

		try {
			while (loop(in, out));
		} catch (IOException e) {
			System.out.println("Chat server: connection lost?");
			ExplosiveMessenger.thread = null;
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
		ExplosiveMessenger.thread = null;
	}

	public boolean loop(DataInputStream in, DataOutputStream out) throws IOException {
		short type = in.readShort();
		IPacket p = IPacket.knownPackets[type];
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
			ExplosiveMessenger.thread = null;
			try {
				socket.close();
			} catch (IOException e1) {
			}
		}
	}
}
