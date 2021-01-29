package keda.common.gui;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgressDialog extends JDialog {
	/**多语言工具类***/
//	private static PropertiesUtil put = PropertiesUtil.getPropertiesUtil();
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Applet applet;
	private String message;
	private int width = 250,height = 40;
	JProgressBar progressBar;

	public ProgressDialog(){
		this(null,null);
	}
	public ProgressDialog(String message){
		this(null,message);
	}
	public ProgressDialog(Applet applet,String message){
		super(GUITools.getFrame(applet));
		this.applet = applet;
		this.message = message;
		progressBar = new JProgressBar(0, 100){
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(ProgressDialog.this.message != null && !"".equals(ProgressDialog.this.message)){
					Graphics2D g2 = (Graphics2D)g;
					int messageWidth= SwingUtilities.computeStringWidth(g2.getFontMetrics(), ProgressDialog.this.message);
					int messageHeight = g2.getFont().getSize();
					g2.drawString(ProgressDialog.this.message, (width-messageWidth)/2, (height-messageHeight)/2+messageHeight);
				}
			}
		};
		progressBar.setIndeterminate(true); // 不确定的进度条
		this.setModal(false);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
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
		getContentPane().add(progressBar,BorderLayout.CENTER);
	}
	public void setMessage(String message){
		this.message = message;
	}
	public void showDialog(){
		setVisible(true);
	}
	public void hiddenDialog(){
		setVisible(false);
	}
	public static void main(String[] args){
		final ProgressDialog p = new ProgressDialog("上传笔录中...");
		JButton b = new JButton("退出");
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				p.hiddenDialog();
			}
		});
		p.add(b,BorderLayout.SOUTH);
		p.setVisible(true);
	}
}
