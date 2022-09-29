package net.minestudio.launcher.version;

import lombok.*;

@Getter
public class VersionInfoResponse {
  private boolean successful;
  private VersionInfo info;
}
