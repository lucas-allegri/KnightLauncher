package com.lucasallegri.launcher.mods;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lucasallegri.launcher.FontManager;
import com.lucasallegri.launcher.LanguageManager;
import com.lucasallegri.launcher.LauncherApp;
import com.lucasallegri.launcher.LauncherConstants;
import com.lucasallegri.launcher.LauncherGUI;
import com.lucasallegri.logging.Logging;
import com.lucasallegri.util.ColorUtil;
import com.lucasallegri.util.DesktopUtil;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import mdlaf.utils.MaterialBorders;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class ModListGUI {

	private static LauncherApp app;
	public static JFrame modListGUIFrame;
	public static List modListContainer;
	public static JLabel labelModCount;
	private JLabel labelModCountText;
	private JButton refreshButton;
	private JButton enableButton;
	private JButton disableButton;
	private JLabel labelName;
	private JLabel labelDescription;
	private JLabel labelVersion;
	private JLabel labelCompatibility;
	private JLabel labelAuthor;
	
	int pY, pX;

	public ModListGUI(LauncherApp app) {
		initialize();
	}
	
	@SuppressWarnings("static-access")
	public void switchVisibility() {
		this.modListGUIFrame.setVisible(this.modListGUIFrame.isVisible() ? false : true);
	}

	private void initialize() {
		modListGUIFrame = new JFrame();
		modListGUIFrame.setVisible(false);
		modListGUIFrame.setTitle(LanguageManager.getValue("t.mods"));
		modListGUIFrame.setBounds(100, 100, 385, 400);
		modListGUIFrame.setResizable(false);
		modListGUIFrame.setUndecorated(true);
		modListGUIFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		modListGUIFrame.getContentPane().setLayout(null);
		
		modListContainer = new List();
		modListContainer.setBounds(10, 26, 162, 326);
		modListContainer.setFont(FontManager.fontMed);
		modListContainer.setBackground(ColorUtil.getBackgroundColor());
		modListContainer.setForeground(ColorUtil.getForegroundColor());
		modListContainer.setFocusable(false);
		modListGUIFrame.getContentPane().add(modListContainer);
		for(Mod mod : ModList.installedMods) { modListContainer.add(mod.getDisplayName()); }
		modListContainer.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				labelName.setText(ModList.installedMods.get(modListContainer.getSelectedIndex()).getDisplayName());
				labelDescription.setText("<html>" + ModList.installedMods.get(modListContainer.getSelectedIndex()).getDescription() + "</html>");
				labelVersion.setText(LanguageManager.getValue("m.mod_version", ModList.installedMods.get(modListContainer.getSelectedIndex()).getVersion()));
				labelCompatibility.setText(LanguageManager.getValue("m.mod_compatibility", ModList.installedMods.get(modListContainer.getSelectedIndex()).getCompatibilityVersion()));
				labelAuthor.setText(LanguageManager.getValue("m.mod_author", ModList.installedMods.get(modListContainer.getSelectedIndex()).getAuthor()));
				enableButton.setEnabled(true);
				disableButton.setEnabled(true);
			}
		});
		
		labelModCount = new JLabel(String.valueOf(ModList.installedMods.size()));
		labelModCount.setHorizontalAlignment(SwingConstants.CENTER);
		labelModCount.setBounds(178, 44, 188, 40);
		labelModCount.setFont(FontManager.fontMedGiant);
		modListGUIFrame.getContentPane().add(labelModCount);
		
		labelModCountText = new JLabel(LanguageManager.getValue("m.mods_installed"));
		labelModCountText.setHorizontalAlignment(SwingConstants.CENTER);
		labelModCountText.setBounds(178, 93, 188, 14);
		labelModCountText.setFont(FontManager.fontReg);
		modListGUIFrame.getContentPane().add(labelModCountText);
		
		refreshButton = new JButton(LanguageManager.getValue("b.refresh"));
		refreshButton.setBounds(9, 370, 89, 23);
		refreshButton.setFont(FontManager.fontMed);
		refreshButton.setFocusPainted(false);
		refreshButton.setFocusable(false);
		refreshButton.setToolTipText(LanguageManager.getValue("b.refresh"));
		modListGUIFrame.getContentPane().add(refreshButton);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				ModListEventHandler.refreshEvent(_action);
			}
		});
		
		JButton modFolderButton = new JButton(LanguageManager.getValue("b.open_mods_folder"));
		modFolderButton.setBounds(104, 370, 136, 23);
		modFolderButton.setFont(FontManager.fontMed);
		modFolderButton.setFocusPainted(false);
		modFolderButton.setFocusable(false);
		modFolderButton.setToolTipText(LanguageManager.getValue("b.open_mods_folder"));
		modListGUIFrame.getContentPane().add(modFolderButton);
		modFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				DesktopUtil.openDir(LauncherConstants.USER_DIR + "/mods");
			}
		});
		
		JButton getModsButton = new JButton(LanguageManager.getValue("b.get_mods"));
		getModsButton.setBounds(245, 370, 126, 23);
		getModsButton.setFont(FontManager.fontMed);
		getModsButton.setFocusPainted(false);
		getModsButton.setFocusable(false);
		getModsButton.setToolTipText(LanguageManager.getValue("b.get_mods"));
		modListGUIFrame.getContentPane().add(getModsButton);
		getModsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent _action) {
				ModListEventHandler.getModsEvent(_action);
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setBounds(178, 130, 195, 2);
		modListGUIFrame.getContentPane().add(separator);
		
		labelName = new JLabel("");
		labelName.setFont(FontManager.fontMed);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setBounds(178, 148, 188, 14);
		modListGUIFrame.getContentPane().add(labelName);
		
		labelAuthor = new JLabel("");
		labelAuthor.setFont(FontManager.fontReg);
		labelAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		labelAuthor.setBounds(178, 165, 188, 14);
		modListGUIFrame.getContentPane().add(labelAuthor);
		
		labelDescription = new JLabel("");
		labelDescription.setFont(FontManager.fontReg);
		labelDescription.setHorizontalAlignment(SwingConstants.LEADING);
		labelDescription.setVerticalAlignment(SwingConstants.TOP);
		labelDescription.setBounds(188, 196, 178, 70);
		modListGUIFrame.getContentPane().add(labelDescription);
		
		labelVersion = new JLabel("");
		labelVersion.setFont(FontManager.fontReg);
		labelVersion.setBounds(188, 274, 178, 14);
		modListGUIFrame.getContentPane().add(labelVersion);
		
		labelCompatibility = new JLabel("");
		labelCompatibility.setFont(FontManager.fontReg);
		labelCompatibility.setBounds(188, 295, 178, 14);
		modListGUIFrame.getContentPane().add(labelCompatibility);
		
		enableButton = new JButton(LanguageManager.getValue("b.enable"));
		enableButton.setFont(FontManager.fontMed);
		enableButton.setForeground(ColorUtil.getGreenForegroundColor());
		enableButton.setEnabled(false);
		enableButton.setFocusable(false);
		enableButton.setFocusPainted(false);
		enableButton.setBounds(183, 326, 89, 23);
		modListGUIFrame.getContentPane().add(enableButton);
		enableButton.setVisible(false);
		
		disableButton = new JButton(LanguageManager.getValue("b.disable"));
		disableButton.setFont(FontManager.fontMed);
		disableButton.setForeground(ColorUtil.getRedForegroundColor());
		disableButton.setEnabled(false);
		disableButton.setFocusable(false);
		disableButton.setFocusPainted(false);
		disableButton.setBounds(281, 326, 89, 23);
		modListGUIFrame.getContentPane().add(disableButton);
		disableButton.setVisible(false);
		
		JPanel titleBar = new JPanel();
		titleBar.setBounds(0, 0, modListGUIFrame.getWidth(), 20);
		titleBar.setBackground(ColorUtil.getTitleBarColor());
		modListGUIFrame.getContentPane().add(titleBar);
		
		
		/*
		 * Based on Paul Samsotha's reply @ StackOverflow
		 * link: https://stackoverflow.com/questions/24476496/drag-and-resize-undecorated-jframe
		 */
		titleBar.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	
		        pX = me.getX();
		        pY = me.getY();
		    }
		});
		titleBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				
		        pX = me.getX();
		        pY = me.getY();
		    }
		
		    @Override 
		    public void mouseDragged(MouseEvent me) {
		
		    	modListGUIFrame.setLocation(modListGUIFrame.getLocation().x + me.getX() - pX,
		    	modListGUIFrame.getLocation().y + me.getY() - pY);
		    }
		});
		titleBar.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent me) {
		
				modListGUIFrame.setLocation(modListGUIFrame.getLocation().x + me.getX() - pX,
				modListGUIFrame.getLocation().y + me.getY() - pY);
		    }
		
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// Auto-generated method stub
			}
		});
		titleBar.setLayout(null);
		
		JLabel windowTitle = new JLabel(LanguageManager.getValue("t.mods"));
		windowTitle.setFont(FontManager.fontMed);
		windowTitle.setBounds(10, 0, modListGUIFrame.getWidth() - 100, 20);
		titleBar.add(windowTitle);
		
		Icon closeIcon = IconFontSwing.buildIcon(FontAwesome.TIMES, 14, ColorUtil.getForegroundColor());
		JButton closeButton = new JButton(closeIcon);
		closeButton.setBounds(modListGUIFrame.getWidth() - 20, 1, 20, 21);
		closeButton.setToolTipText(LanguageManager.getValue("b.close"));
		closeButton.setFocusPainted(false);
		closeButton.setFocusable(false);
		closeButton.setBorder(MaterialBorders.roundedLineColorBorder(ColorUtil.getTitleBarColor(), 0));
		closeButton.setFont(FontManager.fontMed);
		titleBar.add(closeButton);
		closeButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	modListGUIFrame.dispose();
		    }
		});
		
		Icon minimizeIcon = IconFontSwing.buildIcon(FontAwesome.CHEVRON_DOWN, 14, ColorUtil.getForegroundColor());
		JButton minimizeButton = new JButton(minimizeIcon);
		minimizeButton.setBounds(modListGUIFrame.getWidth() - 40, 1, 20, 21);
		minimizeButton.setToolTipText(LanguageManager.getValue("b.minimize"));
		minimizeButton.setFocusPainted(false);
		minimizeButton.setFocusable(false);
		minimizeButton.setBorder(MaterialBorders.roundedLineColorBorder(ColorUtil.getTitleBarColor(), 0));
		minimizeButton.setFont(FontManager.fontMed);
		titleBar.add(minimizeButton);
		minimizeButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	modListGUIFrame.setState(Frame.ICONIFIED);
		    }
		});
		
		modListGUIFrame.setLocationRelativeTo(null);
		
		modListGUIFrame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosed(WindowEvent windowEvent) {
		        LauncherGUI.modButton.setEnabled(true);
		    }
		});
		
	}
}
