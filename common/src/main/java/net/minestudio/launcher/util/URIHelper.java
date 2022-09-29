package net.minestudio.launcher.util;

import java.io.File;

public class URIHelper {

  public static String slashify(String path, boolean isDirectory) {
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
}
