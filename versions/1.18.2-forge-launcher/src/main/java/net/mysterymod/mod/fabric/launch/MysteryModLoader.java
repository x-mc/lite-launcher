package net.mysterymod.mod.fabric.launch;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileModLocator;
import net.minecraftforge.forgespi.locating.IModLocator;
import net.mysterymod.mod.launcher.LaunchConfiguration;
import net.mysterymod.mod.launcher.LaunchEnvironment;
import net.mysterymod.mod.launcher.LaunchService;
import net.mysterymod.mod.launcher.LaunchVersion;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

@Mod("mysterymodloader")
public class MysteryModLoader extends AbstractJarFileModLocator implements IModLocator  {

  @Override
  public Stream<Path> scanCandidates() {
    LaunchConfiguration launchConfiguration = new LaunchConfiguration(
      LaunchVersion.MC_1_18_2,
      LaunchEnvironment.FORGE
    );
    LaunchService launchService = new LaunchService(launchConfiguration);
    try {
      launchService.initialize(path -> {
      });
    } catch (Exception e) {
    }
    return Stream.of(Paths.get("MysteryMod/internal/1.18.2-forge.jar"));
  }

  @Override
  public String name() {
    return "MysteryMod Loader";
  }

  @Override
  public void initArguments(Map<String, ?> arguments) {
  }


}
