package com.explosivenetwork.ExplosiveMessenger.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import com.explosivenetwork.ExplosiveMessenger.Packets.IPacket;

public class Server {
	public static ArrayList<ServerThread> threads;
	public static ServerSocket serverSock = null;

	public static BufferedWriter general_log;
	public static BufferedWriter chat_log;

	public static void start() throws IOException {
		int port = 51326;
		threads = new ArrayList<ServerThread>();

		System.out.println("ExplosiveMessenger Server");
		System.out.println("Server Started on port " + port);

		try {
			serverSock = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port " + port);
			System.exit(-1);
		}

		System.out.println("Listening on port " + port);
		new ListenThread().start();

	}

	public static void broadcast(IPacket packet) {
		for (ServerThread thread : threads) {
			thread.write(packet);
		}
	}

	public static void stop() {
		try {
			System.out.println("Server is shutting down NOW!");
			System.out.println("WARNING: Threads may take longer to close");
			serverSock.close();
			for (ServerThread thread : threads) {
				thread.socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
