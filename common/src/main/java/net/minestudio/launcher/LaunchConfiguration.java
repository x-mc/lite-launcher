package net.minestudio.launcher;

public class LaunchConfiguration {
  private final LaunchVersion version;
  private final LaunchEnvironment environment;

  public LaunchConfiguration(LaunchVersion version, LaunchEnvironment environment) {
    this.version = version;
    this.environment = environment;
  }

  public LaunchVersion getVersion() {
    return version;
  }

  public LaunchEnvironment getEnvironment() {
    return environment;
  }

  public boolean isForge() {
    return environment.equals(LaunchEnvironment.FORGE);
  }
}
