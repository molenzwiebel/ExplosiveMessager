package com.github.troid.ServerDataCrosser.server;

import java.io.IOException;
import java.net.Socket;

public class ListenThread extends Thread {
  public void run() {
    try {
      while (true) {
        Socket sock = Server.serverSock.accept();
        ServerThread t = new ServerThread(sock);
        t.start();
        Server.threads.add(t);
        System.out.println("Connection accepted from "
            + sock.getInetAddress().getCanonicalHostName() + " (" + Server.threads.size() + ")");
      }
    } catch (IOException e) {
      System.out.println("Server socket closed.");
    }
  }
}
