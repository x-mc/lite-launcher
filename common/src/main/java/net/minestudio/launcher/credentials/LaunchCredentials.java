package net.minestudio.launcher.credentials;

import lombok.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * The LaunchCredentials are used for internal employees to check internal versions.
 *
 * @author Manuel Kollus
 */
@Getter
@AllArgsConstructor
public class LaunchCredentials {
    private String name, password, branch, state;

    public boolean intern() {
        return name.isEmpty() && password.isEmpty();
    }

    private static final Path LAUNCH_CREDENTIALS_PATH = Paths.get("MysteryMod/launch-credentials.properties");

    public static LaunchCredentials fromDefaultPath() {
        return fromPath(LAUNCH_CREDENTIALS_PATH);
    }

    /**
     * Read your own Launch Credentials file - launch-credentials.properties (not from Minecraft!)
     *
     * @return credentials data
     */
    private static LaunchCredentials fromPath(Path launchPath) {
        if (Files.notExists(launchPath)) return production();
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader(launchPath.toFile())) {
            properties.load(fileReader);
        } catch (IOException readFailure) {
            return production();
        }

        return new LaunchCredentials(
                properties.getProperty("name"),
                properties.getProperty("password"),
                properties.getProperty("branch", "dev"),
                properties.getProperty("state"));
    }

    /**
     * This is the standard version credentials for normal players
     *
     * @return standard version credentials
     */
    private static LaunchCredentials production() {
        return new LaunchCredentials("", "", "dev", "EXTERNAL");
    }

}
