package com.lucasallegri.launcher.settings;

import com.lucasallegri.launcher.LanguageManager;
import com.lucasallegri.launcher.ProgressBar;
import com.lucasallegri.util.DateUtil;
import com.lucasallegri.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static com.lucasallegri.launcher.settings.Log.log;

public class GameSettings {

  public static void load() {
    try {

      ProgressBar.showBar(true);
      ProgressBar.showState(true);
      ProgressBar.setBarMax(1);
      ProgressBar.setBarValue(0);
      ProgressBar.setState(LanguageManager.getValue("m.apply"));

      /**
       * Back up the current extra.txt if there's no back up already.
       * This is useful if an user installs Knight Launcher and had already
       * made it's own extra.txt, this way it won't get deleted forever, just renamed.
       */
      if(!FileUtil.fileExists("old-extra.txt")) {
        FileUtil.rename(new File("extra.txt"), new File("old-extra.txt"));
      }

      PrintWriter writer = new PrintWriter("extra.txt", "UTF-8");

      if (Settings.gameUseStringDeduplication) writer.println("-XX:+UseStringDeduplication");
      if (Settings.gameDisableExplicitGC) writer.println("-XX:+DisableExplicitGC");

      if (Settings.gameUseCustomGC) {
        if (Settings.gameGarbageCollector.equals("ParallelOld")) {
          writer.println("-XX:+UseParallelGC");
          writer.println("-XX:+Use" + Settings.gameGarbageCollector + "GC");
        } else {
          writer.println("-XX:+Use" + Settings.gameGarbageCollector + "GC");
        }
      }

      if (Settings.gameUndecoratedWindow) writer.println("-Dorg.lwjgl.opengl.Window.undecorated=true");

      if (Settings.gameGarbageCollector.equals("G1")) {
        writer.println("-Xms" + Settings.gameMemory + "M");
        writer.println("-Xmx" + Settings.gameMemory + "M");
      } else {
        writer.println("-Xms512M");
        writer.println("-Xmx" + Settings.gameMemory + "M");
      }

      writer.println(Settings.gameAdditionalArgs);
      writer.close();

      ProgressBar.setBarValue(1);
      ProgressBar.showBar(false);
      ProgressBar.showState(false);
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      log.error(e);
    }
  }

}
