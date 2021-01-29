package keda.common.util;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class UiUtil {

	/**
	 * 设置UI风格，windows系统风格，可显示中文字体
	 */
	@SuppressWarnings("rawtypes")
	public static void initGUIFont() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			UIDefaults defaultUI = UIManager.getDefaults();
			defaultUI.remove("SplitPane.border");
			defaultUI.remove("SplitPaneDivider.border");
			defaultUI.put("SplitPane.dividerSize", 8);
			
			Font f = new Font("宋体", Font.PLAIN, 12);
			Enumeration keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = UIManager.get(key);
				if (value instanceof javax.swing.plaf.FontUIResource) {
					UIManager.put(key, f);
				}
			}
			
		} catch (Exception e) {
		}
	}

}
