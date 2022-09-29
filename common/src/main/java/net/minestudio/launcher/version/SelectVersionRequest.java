package net.minestudio.launcher.version;

import net.minestudio.launcher.credentials.LaunchCredentials;

public class SelectVersionRequest {
  private String branch, state;
  private LaunchCredentials launchCredentials;

  public SelectVersionRequest(
    String branch,
    String state,
    LaunchCredentials launchCredentials
  ) {
    this.branch = branch;
    this.state = state;
    this.launchCredentials = launchCredentials;
  }


  public static SelectVersionRequest fromCredentials(LaunchCredentials credentials) {
    return new SelectVersionRequest(
      credentials.getBranch(),
      credentials.getState(),
      credentials
    );
  }

}
