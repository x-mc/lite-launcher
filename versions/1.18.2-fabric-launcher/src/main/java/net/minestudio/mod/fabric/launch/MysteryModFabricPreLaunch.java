package net.minestudio.mod.fabric.launch;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.launch.common.FabricLauncherBase;
import net.minestudio.launcher.LaunchConfiguration;
import net.minestudio.launcher.LaunchEnvironment;
import net.minestudio.launcher.LaunchService;
import net.minestudio.launcher.LaunchVersion;
import net.minestudio.launcher.util.URIHelper;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class MysteryModFabricPreLaunch implements PreLaunchEntrypoint {

  @Override
  public void onPreLaunch() {
    try {
      LaunchService launchService = createLaunchService();

      launchService.initialize(path -> {
        ClassLoader targetClassLoader = FabricLauncherBase.getLauncher().getTargetClassLoader();
        injectLiteVersionIntoTargetClassLoader(targetClassLoader);
        setupLiteVersion(targetClassLoader);
      });
    } catch (Exception ignored) {
    }
  }

  private static final LaunchVersion LAUNCH_VERSION = LaunchVersion.MC_1_18_2;
  private static final LaunchEnvironment LAUNCH_ENVIRONMENT = LaunchEnvironment.FABRIC;

  private LaunchService createLaunchService() {
    LaunchConfiguration launchConfiguration = new LaunchConfiguration(
      LAUNCH_VERSION, LAUNCH_ENVIRONMENT
    );
    return new LaunchService(launchConfiguration);
  }

  private void injectLiteVersionIntoTargetClassLoader(ClassLoader targetClassLoader) {
    try {
      Method addURL = targetClassLoader.getClass().getDeclaredMethod("addUrlFwd", URL.class);
      addURL.setAccessible(true);
      File file = new File("MysteryMod/internal/1.18.2-fabric.jar");
      URL url = new URL("file", "", URIHelper.slashify(file.getAbsolutePath(), file.isDirectory()));
      addURL.invoke(targetClassLoader, url);
    } catch (IllegalAccessException | InvocationTargetException |
             MalformedURLException | NoSuchMethodException ignored) {
    }
  }

  private static final String SETUP_CLASS = "net.mysterymod.mod.version_specific.setup.MysteryModSetup";

  private void setupLiteVersion(ClassLoader targetClassLoader) {
    try {
      // Please NEVER change the class reference.
      Class<?> aClass = Class.forName(SETUP_CLASS, true, targetClassLoader);
      Object setupClass = aClass.newInstance();

      // Please NEVER change the method name.
      Method preLaunchMethod = aClass.getMethod("onPreLaunch");
      preLaunchMethod.invoke(setupClass);

    } catch (Exception ignored) {
    }
  }
}
