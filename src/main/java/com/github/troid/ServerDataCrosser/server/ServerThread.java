package com.github.troid.ServerDataCrosser.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.github.troid.ServerDataCrosser.packets.IPacket;


public class ServerThread extends Thread {
  public Socket socket = null;
  public DataOutputStream out = null;
  public DataInputStream in = null;
  public String name = "";
  public String longname = "";
  public String host;

  public ServerThread(Socket socket) {
    this.socket = socket;
    host = socket.getInetAddress().getCanonicalHostName() + ":" + socket.getPort();
  }

  public void write(IPacket packet) {
    try {
      packet.write(out);
    } catch (IOException e) {
      System.out.println("Error writing packet " + packet.getClass());
    }
  }

  public boolean loop(DataInputStream in, DataOutputStream out) throws Exception {
    short type = in.readShort();
    IPacket packet = (IPacket) IPacket.knownPackets[type].newInstance();
    packet.read(in);
    Server.broadcast(packet);
    return true;
  }

  public void run() {
    try {
      out = new DataOutputStream(socket.getOutputStream());
      in = new DataInputStream(socket.getInputStream());
      while (loop(in, out));
    } catch (EOFException e) {
      Server.threads.remove(this);
      return;
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } finally {
      try {
        if (out != null) out.close();
        if (in != null) in.close();
        socket.close();

        Server.threads.remove(this);
      } catch (IOException e) {}
    }
  }

}
