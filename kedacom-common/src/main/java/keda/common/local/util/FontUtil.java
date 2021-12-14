package keda.common.local.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


/***
 * 
 * 字体操作工具类
 * 
 * @author yrj
 * 
 */
public class FontUtil {
	/** 日志工具类 **/
	private static Logger log = LogManager.getLogger(FontUtil.class);
	/** 系统支持字体 **/
	String fontList[] = null;

	public void setCalssroot(Class<?> classroot) {
	}

	/**
	 * 调用类对象
	 * 
	 * @param cls
	 * @return
	 */
	public static FontUtil getFontUtil(Class<?> cls) {
		FontUtil ft = new FontUtil();
		if (cls == null) {
			log.error("调用类对象为空");
		} else {
			ft.setCalssroot(cls);
		}
		ft.init();
		return ft;
	};

	public static FontUtil getFontUtil() {
		FontUtil ft = new FontUtil();
		ft.init();
		return ft;
	};

	private FontUtil() {
	};
	
	private void init() {
		UIDefaults ui = UIManager.getLookAndFeelDefaults();
		log.info(ui);
		// ui.put("TextArea.font", new FontUIResource("Menksoft  ",
		// Font.BOLD, 14));
		fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		if (fontList == null) {
			log.info("系统无字体请安装");
			return;
		}
		log.info("系统字体:");
		for (String fontname : fontList) {
			log.info(fontname);
		}
	}
	
	/**
	 * 返回字体
	 * @param args
	 */
	public Font getMenksoftHawangCN(){
		Font font = null;
		try {
			URL url = SettingLoader.getResource("com/keda/local/font/MenksoftHawang_cn.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException e) {
			log.error("读取字体异常：" +e);
		} catch (IOException e) {
			log.error("读取字体异常：" +e);
		}
		if(font == null){
			log.info("无MenksoftHawang_cn字体");
		}else{
			font = font.deriveFont(Font.LAYOUT_RIGHT_TO_LEFT, 19); 
		}
		return font;
	}
	public static void main(String[] args) {

		/*FontUtil ft = FontUtil.getFontUtil(FontUtil.class);
		Font f = ft.getMenksoftHawangCN();
		
		JFrame win = new JFrame();
		win.setLayout(new FlowLayout());
		JButton but = new JButton("aa");
		win.add(but);
		JButton but1 = new JButton("中文");
		win.add(but1);
	
		JButton but2 = new JButton(" ");
		but2.setPreferredSize(new Dimension(150, 30));
		but2.setFont(f);
		win.add(but2);
		
		JButton but3 = new JButton(" ");
		but3.setPreferredSize(new Dimension(150, 30));
		win.add(but3);
		
		win.setSize(300, 200);
		win.setVisible(true);*/
		
	}
}
