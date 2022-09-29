package net.minestudio.launcher.mod;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModId {
  private String modId;

  public static ModId of(String name) {
    return new ModId(name);
  }

  private static final String UNKNOWN = "unknown";

  public static ModId unknown() {
    return new ModId(UNKNOWN);
  }
}
