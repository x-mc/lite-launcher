package net.minestudio.launcher.pid;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The PidService recognizes the current process key. This is used to make sure that two Lite environments are not started in parallel.
 * <p>
 * The problem is, should the testing procedure not happen, that there will be errors when launching with other mods and this could extend the launch time.
 *
 * @author Manuel Kollus
 */
public class PidService {
    private static final RuntimeMXBean RUNTIME_MX_BEAN = ManagementFactory.getRuntimeMXBean();

    private static final Path DEFAULT_PID_PATH = Paths.get("MysteryMod/processes");

    public static boolean check() throws Exception {
        if (Files.notExists(DEFAULT_PID_PATH)) {
            Files.createDirectories(DEFAULT_PID_PATH);
        }

        String rawPidName = RUNTIME_MX_BEAN.getName();
        String formattedPidName = rawPidName.split("@")[0];
        long parsedPid = Long.parseLong(formattedPidName);

        Path pidPath = DEFAULT_PID_PATH.resolve(parsedPid + "");
        if (Files.notExists(pidPath)) {
            // Creates a file with the pid name to make sure that no other mod starts with Lite
            Files.createFile(pidPath);
            return false;
        } else {
            // Another Lite version was detected
            return true;
        }
    }

}
