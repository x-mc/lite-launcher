package net.minestudio.launcher;

/**
 * Defines the target minecraft version e.g 1.8.9, 1.18.2, 1.19.2
 */
public enum LaunchVersion {
    MC_1_8_9,
    MC_1_12_2,
    MC_1_16_5,
    MC_1_18_2,
    MC_1_19;

    /**
     * Format the enum to minecraft version
     *
     * @param version the given version enum
     * @return formatted minecraft version
     */
    public String name(LaunchVersion version) {
        return version.name().replace("MC_", "").replace("_", ".");
    }
}
