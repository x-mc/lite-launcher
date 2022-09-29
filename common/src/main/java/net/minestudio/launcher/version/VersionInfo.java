package net.minestudio.launcher.version;

import lombok.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
@AllArgsConstructor
public class VersionInfo {
  public String branch;
  public String buildVersion;
  public Map<String, String> fabricVersions = new ConcurrentHashMap<>();
  public Map<String, String> forgeVersions = new ConcurrentHashMap<>();
}
