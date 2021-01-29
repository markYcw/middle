package keda.common.gui;

import keda.common.local.util.PropertiesUtil;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class MessageDialog extends JDialog {
	/**多语言工具类***/
	private static PropertiesUtil put = PropertiesUtil.getPropertiesUtil();
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Applet applet;
	private JLabel mesLabel;
	private int width = 250,height = 40;
	


	public MessageDialog(Applet applet){
		super(GUITools.getFrame(applet));
		this.setModal(false);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.applet = applet;
		this.setTitle(put.getMessage("MessageDialog.java.baocun"));
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int tWidth = (int)d.getWidth();
		int tHeight = (int)d.getHeight();
		int centerX = (tWidth - width)/2;
		int centerY = (tHeight - height)/2;
		this.setSize(new Dimension(width,height));
		this.setLocation(new Point(centerX,centerY));
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		mesLabel=new JLabel(put.getMessage("MessageDialog.java.jiazaizhong"),JLabel.CENTER);
		 
		getContentPane().add(mesLabel,BorderLayout.CENTER);
	}
	

	public void showMessage(String message){
		showMessage(message,Color.BLACK);
	}
	public void showMessage(String message,Color foreColor){
		mesLabel.setText(message);
		mesLabel.setForeground(foreColor);
		setVisible(true);
	}
	public void hiddenDialog(){
		setVisible(false);
	}
}
