package net.minestudio.mod.fabric.launch;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.launch.common.FabricLauncherBase;
import net.minestudio.launcher.LaunchConfiguration;
import net.minestudio.launcher.LaunchEnvironment;
import net.minestudio.launcher.LaunchService;
import net.minestudio.launcher.LaunchVersion;
import org.apache.logging.log4j.LogManager;

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
      LaunchVersion.MC_1_16_5,
      LaunchEnvironment.FABRIC
    );
    LaunchService launchService = new LaunchService(launchConfiguration);

    try {
      launchService.initialize(path -> {
        ClassLoader targetClassLoader = FabricLauncherBase.getLauncher().getTargetClassLoader();


        for (Method method : targetClassLoader.getClass().getMethods()) {
          LogManager.getLogger().info(method.getName());
        }
        try {
          Method addURL = targetClassLoader.getClass().getDeclaredMethod("addUrlFwd", URL.class);
          addURL.setAccessible(true);
          File file = new File("MysteryMod/internal/1.16.5-fabric.jar");
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
        }
      });
    } catch (Exception e) {
    }

  }
}
