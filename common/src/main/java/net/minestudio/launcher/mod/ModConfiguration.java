package net.minestudio.launcher.mod;

import net.minestudio.launcher.mod.menu.MenuSettings;


public class ModConfiguration {
  private ModId modId;
  private MenuSettings menuSettings;

  protected ModConfiguration(ModId modId, MenuSettings menuSettings) {
    this.modId = modId;
    this.menuSettings = menuSettings;
  }

  public ModId getModId() {
    return modId;
  }

  public MenuSettings getMenuSettings() {
    return menuSettings;
  }

  protected static ModConfiguration create(ModId modId, MenuSettings menuSettings) {
    return new ModConfiguration(modId, menuSettings);
  }
}
