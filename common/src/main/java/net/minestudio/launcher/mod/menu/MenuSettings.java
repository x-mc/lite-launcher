package net.minestudio.launcher.mod.menu;


public class MenuSettings {
  private final MenuVisibilityPolicy visibilityPolicy;
  private final PlayerVisibilityState playerState;

  public MenuSettings(MenuVisibilityPolicy visibilityPolicy, PlayerVisibilityState playerState) {
    this.visibilityPolicy = visibilityPolicy;
    this.playerState = playerState;
  }

  public MenuVisibilityPolicy getVisibilityPolicy() {
    return visibilityPolicy;
  }

  public PlayerVisibilityState getPlayerState() {
    return playerState;
  }

  public static MenuSettings of(
    MenuVisibilityPolicy visibilityPolicy,
    PlayerVisibilityState visibilityState
  ) {
    return new MenuSettings(visibilityPolicy, visibilityState);
  }
}
