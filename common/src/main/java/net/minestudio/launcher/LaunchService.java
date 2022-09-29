package net.minestudio.launcher;


import com.google.gson.Gson;
import net.minestudio.launcher.credentials.LaunchCredentials;
import net.minestudio.launcher.mod.ModConfiguration;
import net.minestudio.launcher.mod.ModConfigurationFactory;
import net.minestudio.launcher.pid.PidService;
import net.minestudio.launcher.util.FileHelper;
import net.minestudio.launcher.version.SelectVersionRequest;
import net.minestudio.launcher.version.VersionInfo;
import net.minestudio.launcher.version.VersionInfoResponse;
import net.minestudio.launcher.version.VersionService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;


public class LaunchService {
  private final LaunchConfiguration configuration;

  public LaunchService(LaunchConfiguration configuration) {
    this.configuration = configuration;
  }

  private static final Gson GSON = new Gson();

  public void applyLiteRunner(ModConfiguration modConfiguration) {
    // Don't change this property
    System.setProperty("mysterymod.launcher.runner", GSON.toJson(modConfiguration));
  }

  public void initialize(Consumer<Path> runnable) throws Exception {
    // Check if a Lite version is already running in parallel
    if (PidService.check()) {
      // Lite is not loaded because another Lite version was found
      return;
    }
    LaunchCredentials credentials = LaunchCredentials.fromDefaultPath();
    // Select the lite version
    VersionInfoResponse response = selectVersionInfo(credentials);

    if (!response.isSuccessful()) {
      // Lite is not loaded because the version is invalid.
      return;
    }

    LaunchVersion version = configuration.getVersion();
    String plainName = version.name(version);

    String versionName = plainName + "-" +
      configuration.getEnvironment().name().toLowerCase() + ".jar";

    boolean mustBeUpdated;

    Path versionPath = Paths.get("MysteryMod/internal/");
    Path versionFile = versionPath.resolve(versionName);

    // Check if there is already a Lite version available
    if (Files.notExists(versionPath) && Files.notExists(versionFile)) {
      mustBeUpdated = true;
      Files.createDirectories(versionPath);
    } else {
      String sha1 = FileHelper.getSha1Digest(versionFile.toFile());
      VersionInfo info = response.getInfo();
      String targetSha1 = configuration.isForge() ? info.forgeVersions.get(plainName) :
        info.fabricVersions.get(plainName);
      // Check if there is new Lite version
      mustBeUpdated = !sha1.equalsIgnoreCase(targetSha1);
    }

    if (mustBeUpdated) {
      // Download a new Lite version
      updateLiteEnvironment(
        response,
        plainName,
        credentials,
        versionFile
      );
    }

    runnable.accept(versionFile);
  }

  private static final String DEFAULT_DOWNLOAD_URL = "https://versions.mysterymod.net/api/v1/lightweight/file/version/select/";

  private void updateLiteEnvironment(
    VersionInfoResponse response,
    String plainName,
    LaunchCredentials credentials,
    Path versionFile) throws IOException {
    Files.deleteIfExists(versionFile);

    VersionInfo info = response.getInfo();
    String downloadUrl = DEFAULT_DOWNLOAD_URL
      + info.buildVersion
      + "/"
      + info.branch
      + "/"
      + plainName
      + "/"
      + configuration.getEnvironment().name();

    // This procedure is used to access internal versions for internal employees
    if (!credentials.intern()) {
      downloadUrl += "/" + credentials.getName() + "/" + credentials.getPassword();
    }

    download(downloadUrl, versionFile);
  }

  private VersionInfoResponse selectVersionInfo(LaunchCredentials credentials) {
    SelectVersionRequest versionRequest = SelectVersionRequest.fromCredentials(credentials);
    return VersionService.select(versionRequest);
  }

  private void download(String fileUrl, Path tmpPath) {
    try {
      URL url = new URL(fileUrl);
      URLConnection urlConnection = url.openConnection();
      urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

      ReadableByteChannel byteChannel = Channels.newChannel(urlConnection.getInputStream());

      try (FileOutputStream outputStream = new FileOutputStream(tmpPath.toFile())) {
        FileChannel channel = outputStream.getChannel();
        channel.transferFrom(byteChannel, 0, Long.MAX_VALUE);
      }

    } catch (IOException ignored) {
    }
  }
}
