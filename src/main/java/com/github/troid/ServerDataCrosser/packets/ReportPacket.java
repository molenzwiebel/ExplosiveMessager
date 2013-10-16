package com.github.troid.ServerDataCrosser.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.ChatColor;

import com.github.troid.ServerDataCrosser.reports.Report;

public class ReportPacket implements IPacket {
  public static final PacketType packet = PacketType.REPORT;
  public Report report;

  /**
   * Default blank constructor to initialize a packet with null data
   */
  public ReportPacket() {

  }

  /**
   * Constructor for ReportPacket
   * 
   * @param report
   */
  public ReportPacket(Report report) {
    this.report = report;
  }

  public void write(DataOutputStream out) throws IOException {
    out.writeShort(packet.ordinal());
    out.writeUTF(report.getReporter());
    out.writeUTF(report.getVictim());
    out.writeUTF(report.getReport());
    out.writeUTF(report.getServer());
    out.flush();
  }

  public void read(DataInputStream in) throws IOException {
    String reporter = in.readUTF();
    String victim = in.readUTF();
    String reportMessage = in.readUTF();
    String server = in.readUTF();
    report = new Report(reporter, victim, reportMessage, server);
  }

  public String toString() {
    return "Packet{name=\"ReportPacket\",sender=\"" + report.getReporter() + "\",victim=\""
        + report.getVictim() + "\",server=\"" + report.getReporter() + "\",message=\""
        + report.getReport() + "\"" + '}';
  }
}
