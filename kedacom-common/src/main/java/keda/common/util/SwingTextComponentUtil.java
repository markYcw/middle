package keda.common.util;

import javax.swing.*;
import java.awt.im.InputMethodRequests;

/**
 * 解决智能ABC与swing的冲突,项目中暂时只用于空的构造函数，如有需求，请自行增加
 * 解决方法，只需复盖getInputMethodRequests方法并返回null即可
 * @author root
 *
 */
public class SwingTextComponentUtil {
	public static JTextField createTextField(){
		return new JTextField(){
			private static final long serialVersionUID = 361979479791362228L;
			@Override
			public InputMethodRequests getInputMethodRequests() {
				return null;
			}
		};
	}
	public static JTextField createTextField(int columns){
		return new JTextField(columns){
			private static final long serialVersionUID = 361979479791362228L;
			@Override
			public InputMethodRequests getInputMethodRequests() {
				return null;
			}
		};
	}
	public static JTextField createTextField(String text){
		return new JTextField(text){
			private static final long serialVersionUID = 361979479791362228L;
			@Override
			public InputMethodRequests getInputMethodRequests() {
				return null;
			}
		};
	}
	public static JTextArea createTextArea(){
		return new JTextArea(){
			private static final long serialVersionUID = -5929282900543530333L;
			@Override
			public InputMethodRequests getInputMethodRequests() {
				return null;
			}
		};
	}
	public static JTextArea createTextArea(int rows, int columns){
		return new JTextArea(rows, columns){
			private static final long serialVersionUID = -5929282900543530333L;
			@Override
			public InputMethodRequests getInputMethodRequests() {
				return null;
			}
		};
	}
	public static JTextPane createTextPane(){
		return new JTextPane(){
			private static final long serialVersionUID = 3501740254877236374L;
//			@Override
//			public InputMethodRequests getInputMethodRequests() {
//				return null;
//			}
		};
	}
}
