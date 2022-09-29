package net.minestudio.launcher.version;


public class VersionInfoResponse {
  private boolean successful;
  private VersionInfo info;

  public boolean isSuccessful() {
    return successful;
  }

  public VersionInfo getInfo() {
    return info;
  }
}
