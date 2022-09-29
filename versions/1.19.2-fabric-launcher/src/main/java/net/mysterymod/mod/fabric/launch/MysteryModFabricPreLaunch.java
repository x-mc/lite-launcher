package net.mysterymod.mod.fabric.launch;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.mysterymod.mod.launcher.LaunchConfiguration;
import net.mysterymod.mod.launcher.LaunchEnvironment;
import net.mysterymod.mod.launcher.LaunchService;
import net.mysterymod.mod.launcher.LaunchVersion;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class MysteryModFabricPreLaunch implements PreLaunchEntrypoint {

  private static String slashify(String path, boolean isDirectory) {
    String p = path;
    if (File.separatorChar != '/')
      p = p.replace(File.separatorChar, '/');
    if (!p.startsWith("/"))
      p = "/" + p;
    if (!p.endsWith("/") && isDirectory)
      p = p + "/";

    p = p.replace(" ", "%20");
    return p;
  }
  @Override
  public void onPreLaunch() {
    LaunchConfiguration launchConfiguration = new LaunchConfiguration(
      LaunchVersion.MC_1_19,
      LaunchEnvironment.FABRIC
    );
    LaunchService launchService = new LaunchService(launchConfiguration);

    try {
      launchService.initialize(path -> {
        ClassLoader targetClassLoader = FabricLauncherBase.getLauncher().getTargetClassLoader();

        try {
          Method addURL = targetClassLoader.getClass().getDeclaredMethod("addUrlFwd", URL.class);
          addURL.setAccessible(true);
          File file = new File("MysteryMod/internal/1.19-fabric.jar");
          try {
            URL url = new URL("file", "", slashify(file.getAbsolutePath(), file.isDirectory()));
            addURL.invoke(targetClassLoader, url);
          } catch (IllegalAccessException | InvocationTargetException |
                   MalformedURLException e) {
          }
        } catch (NoSuchMethodException e) {
        }

        try {
          Class<?> aClass = Class.forName("net.mysterymod.mod.version_specific.setup.MysteryModSetup", true, targetClassLoader);
          Object o = aClass.newInstance();
          aClass.getMethod("onPreLaunch").invoke(o);

        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    } catch (Exception e) {
      Log.error(LogCategory.LOG, e.getMessage(), e);
    }

  }
}
