package net.minestudio.launcher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LaunchConfiguration {
  private final LaunchVersion version;
  private final LaunchEnvironment environment;

  public boolean isForge() {
    return environment.equals(LaunchEnvironment.FORGE);
  }
}
