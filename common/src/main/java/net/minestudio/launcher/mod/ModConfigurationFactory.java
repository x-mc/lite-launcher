package net.minestudio.launcher.mod;

import net.minestudio.launcher.mod.menu.MenuSettings;
import net.minestudio.launcher.mod.menu.MenuVisibilityPolicy;
import net.minestudio.launcher.mod.menu.PlayerVisibilityState;

public final class ModConfigurationFactory {
  private ModConfigurationFactory() {
  }

  private static final int DEFAULT_ID = -1;

  public static ModConfiguration createDefault() {
    return createDefault(DEFAULT_ID);
  }

  public static ModConfiguration createDefault(long modId) {
    return ModConfiguration.create(
      ModId.of(modId),
      MenuSettings.of(MenuVisibilityPolicy.ALL, PlayerVisibilityState.ACTIVE)
    );
  }

  public static ModConfiguration createWithMenuSettings(MenuSettings settings) {
    return createWithMenuSettings(DEFAULT_ID, settings);
  }

  public static ModConfiguration createWithMenuSettings(long modId, MenuSettings settings) {
    return ModConfiguration.create(
      ModId.of(modId),
      settings
    );
  }
}
