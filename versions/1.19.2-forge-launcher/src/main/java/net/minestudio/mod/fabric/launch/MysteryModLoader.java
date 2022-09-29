package net.minestudio.mod.fabric.launch;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileModLocator;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.minestudio.launcher.LaunchConfiguration;
import net.minestudio.launcher.LaunchEnvironment;
import net.minestudio.launcher.LaunchService;
import net.minestudio.launcher.LaunchVersion;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

@Mod("mysterymodloader")
public class MysteryModLoader extends AbstractJarFileModLocator implements IModLocator  {

  @Override
  public Stream<Path> scanCandidates() {
    LaunchConfiguration launchConfiguration = new LaunchConfiguration(
      LaunchVersion.MC_1_19,
      LaunchEnvironment.FORGE
    );
    LaunchService launchService = new LaunchService(launchConfiguration);
    try {
      launchService.initialize(path -> {
      });
    } catch (Exception e) {
    }
    return Stream.of(Paths.get("MysteryMod/internal/1.19-forge.jar"));
  }

  @Override
  public String name() {
    return "MysteryMod Loader";
  }

  @Override
  public void initArguments(Map<String, ?> arguments) {
  }


}
