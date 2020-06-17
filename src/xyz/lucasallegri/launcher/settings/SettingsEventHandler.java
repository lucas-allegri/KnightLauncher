package xyz.lucasallegri.launcher.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import xyz.lucasallegri.dialog.DialogWarning;
import xyz.lucasallegri.launcher.Language;
import xyz.lucasallegri.launcher.mods.ModLoader;

public class SettingsEventHandler {
	
	public static void platformChangeEvent(ItemEvent event) {
		Settings.gamePlatform = (String)SettingsGUI.choicePlatform.getSelectedItem();
		SettingsProperties.setValue("game.platform", (String)SettingsGUI.choicePlatform.getSelectedItem());
	}
	
	public static void rebuildsChangeEvent(ActionEvent event) {
		Settings.doRebuilds = SettingsGUI.switchCleaning.isSelected();
		SettingsProperties.setValue("launcher.rebuilds", SettingsGUI.switchCleaning.isSelected() ? "true" : "false");
	}

	public static void keepOpenChangeEvent(ActionEvent event) {
		Settings.keepOpen = SettingsGUI.switchKeepOpen.isSelected();
		SettingsProperties.setValue("launcher.keepOpen", SettingsGUI.switchKeepOpen.isSelected() ? "true" : "false");
	}
	
	public static void forceRebuildEvent() {
		
		ModLoader.startFileRebuild();
		
	}
	
	public static void createShortcutChangeEvent(ActionEvent event) {
		Settings.createShortcut = SettingsGUI.switchShortcut.isSelected();
		SettingsProperties.setValue("launcher.createShortcut", SettingsGUI.switchShortcut.isSelected() ? "true" : "false");
	}
	
	public static void languageChangeEvent(ItemEvent event) {
		Settings.lang = Language.getLangCode((String)SettingsGUI.choiceLanguage.getSelectedItem());
		SettingsProperties.setValue("launcher.lang", Language.getLangCode((String)SettingsGUI.choiceLanguage.getSelectedItem()));
		DialogWarning.pushTranslated(Language.getValue("m.prompt_restart_required"));
	}
	
	public static void useStringDeduplicationChangeEvent(ActionEvent action) {
		Settings.gameUseStringDeduplication = SettingsGUI.switchStringDedup.isSelected();
		SettingsProperties.setValue("game.useStringDeduplication", SettingsGUI.switchStringDedup.isSelected()  ? "true" : "false");
	}

	public static void useG1GCChangeEvent(ActionEvent action) {
		Settings.gameUseG1GC = SettingsGUI.switchUseG1GC.isSelected();
		SettingsProperties.setValue("game.useG1GC", SettingsGUI.switchUseG1GC.isSelected()  ? "true" : "false");
	}
	
	public static void disableExplicitGCChangeEvent(ActionEvent action) {
		Settings.gameDisableExplicitGC = SettingsGUI.switchExplicitGC.isSelected();
		SettingsProperties.setValue("game.disableExplicitGC", SettingsGUI.switchExplicitGC.isSelected()  ? "true" : "false");
	}
	
	public static void undecoratedWindowChangeEvent(ActionEvent action) {
		Settings.gameUndecoratedWindow = SettingsGUI.switchUndecoratedWindow.isSelected();
		SettingsProperties.setValue("game.undecoratedWindow", SettingsGUI.switchUndecoratedWindow.isSelected()  ? "true" : "false");
	}
	
	public static void saveAdditionalArgs() {
		Settings.gameAdditionalArgs = SettingsGUI.argumentsPane.getText();
		SettingsProperties.setValue("game.additionalArgs", SettingsGUI.argumentsPane.getText());
	}
	
	public static void memoryChangeEvent(ItemEvent event) {
		Settings.gameMemory = SettingsGUI.parseSelectedMemoryAsInt();
		SettingsProperties.setValue("game.memory", String.valueOf(SettingsGUI.parseSelectedMemoryAsInt()));
		SettingsGUI.choiceMemory.setToolTipText((String)SettingsGUI.choiceMemory.getSelectedItem());
	}
	
	public static void styleChangeEvent(ItemEvent event) {
		SettingsProperties.setValue("launcher.style", SettingsGUI.choiceStyle.getSelectedIndex() == 0 ? "dark" : "light");
		DialogWarning.pushTranslated(Language.getValue("m.prompt_restart_required"));
	}
	
	public static void ingameRPCChangeEvent(ActionEvent action) {
		Settings.useIngameRPC = SettingsGUI.switchUseIngameRPC.isSelected();
		SettingsProperties.setValue("launcher.useIngameRPC", SettingsGUI.switchUseIngameRPC.isSelected()  ? "true" : "false");
	}

}
