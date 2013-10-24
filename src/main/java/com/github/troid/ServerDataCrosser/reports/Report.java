package com.github.troid.ServerDataCrosser.reports;



/**
 * Represents a report
 * 
 * @author neiljohari
 * 
 */
public class Report {
  private String reporter;
  private String victim;
  private String report;
  private String server;

  /**
   * Constructor for Report
   * 
   * @param reporter The reporter
   * @param victim The victim
   * @param report The report message
   * @param server The server origin of this report
   */
  public Report(String reporter, String victim, String report, String server) {
    this.reporter = reporter;
    this.victim = victim;
    this.report = report;
    this.server = server;
  }

  /**
   * Gets the reporter
   * 
   * @return The reporter
   */
  public String getReporter() {
    return reporter;
  }

  /**
   * Gets the report
   * 
   * @return The report
   */
  public String getReport() {
    return report;
  }

  /**
   * Gets the server
   * 
   * @return The server
   */
  public String getServer() {
    return server;
  }

  public String toString() {
    return "Report{reporter=\"" + reporter + "\",report=\"" + report + "\",server=\"" + "\'" + '}';
  }

  public String getVictim() {
    return victim;
  }
}
