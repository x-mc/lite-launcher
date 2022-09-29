package net.minestudio.launcher.mod;

public class ModId {
  private final long modId;

  private ModId(long modId) {
    this.modId = modId;
  }

  public long modId() {
    return modId;
  }

  public static ModId of(long modId) {
    return new ModId(modId);
  }
}
