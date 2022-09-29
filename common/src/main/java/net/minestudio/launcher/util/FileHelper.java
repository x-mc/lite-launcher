package net.minestudio.launcher.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class FileHelper {
  public static String getSha1Digest(File file) {
    if (!file.exists()) {
      return "";
    }

    DigestInputStream stream = null;

    try {
      stream = new DigestInputStream(
        new BufferedInputStream(new FileInputStream(file), 4000), MessageDigest.getInstance("SHA-1"));

      final byte[] buffer = new byte[65536];
      int read;

      do {
        read = stream.read(buffer);
      } while (read > 0);
    } catch (Exception ignored) {
      return null;
    } finally {
      try {
        if (stream != null) {
          stream.close();
        }
      } catch (IOException ignored) {
      }
    }

    return String
      .format("%1$040x", new BigInteger(1, stream.getMessageDigest().digest()));
  }
}
