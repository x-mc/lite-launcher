package net.minestudio.launcher.version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestudio.launcher.credentials.LaunchCredentials;

@Getter
@AllArgsConstructor
public class SelectVersionRequest {
  private String branch, state;
  private LaunchCredentials launchCredentials;

  public static SelectVersionRequest fromCredentials(LaunchCredentials credentials) {
    return new SelectVersionRequest(
      credentials.getBranch(),
      credentials.getState(),
      credentials
    );
  }

}
