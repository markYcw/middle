package keda.common.gui;

import keda.common.gui.event.TreePopMenuEvent;
import keda.common.gui.event.TreePopMenuListener;
import keda.common.local.util.PropertiesUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;

public class JTreePopMenu extends JPopupMenu implements ActionListener{
	private static final long serialVersionUID = 737882309662251133L;
	/**多语言工具类***/
	protected final static PropertiesUtil put  = PropertiesUtil.getPropertiesUtil();
	//不能对任何节点生效
	protected final static String NONE = "none";
	//对任何节点都生效
	protected final static String ALL = "all";
	//对所有的非叶子节点生效
	protected final static String NONLEAF = "nonleaf";
	//对在线的非叶子节点生效
	protected final static String ONLINENONLEAF = "onlinenonleaf";
	//对不在线的非叶子节点生效
	protected final static String UNLINENONLEAF = "unlinenonleaf";
	//对所有叶子节点生效
	protected final static String LEAF = "leaf";
	//只对在线叶子节点生效
	protected final static String ONLINELEAF = "onlineleaf";
	//只对不在线叶子节点生效
	protected final static String UNLINELEAF = "unlineleaf";
	//监听器
	protected HashSet<TreePopMenuListener> listeners = new HashSet<TreePopMenuListener>();
	//全部展开
	protected JMenuItem expandAll;
	//全部收宿
	protected JMenuItem collapaseAll;
	//树
	protected JTree tree;
	//鼠标在树中点击的位置
	protected Point p;
	//自定义节点集合
	protected Hashtable<JMenuItem,String> menus = new Hashtable<JMenuItem,String>();
	//弹出菜单时的最后一个节点记录
	protected TreePath lastPopTreePath;
	public JTreePopMenu(){
		this(null,null);
	}
	/**
	 *用户自定义按钮构造函数
	 * @param menuItem
	 */
	public JTreePopMenu(List<String> menuItem,List<String> rights){
		super();
		initComponent(menuItem,rights);
		initEvent();
	}
	/**
	 * 初始化组件
	 * @param menuItem 用户自定义按钮集合
	 * @param rights	用户自定义按钮权限集合
	 */
	public void initComponent(List<String> menuItem,List<String> rights){
		expandAll = new JMenuItem(put.getMessage("qubuzhankai"));
		collapaseAll = new JMenuItem(put.getMessage("qubushousuo"));
		this.add(expandAll);
		this.add(collapaseAll);
		//创建用户自定义方法
		if(menuItem != null && menuItem.size()>0){
			for(int index = 0; index < menuItem.size(); index++){
				JMenuItem temp = new JMenuItem(menuItem.get(index));
				temp.addActionListener(this);
				this.add(temp);
				String right = rights.get(index);
				if(right == null || "".equals(right))
					right = NONE;
				menus.put(temp, right);
			}
		}
	}
	//初始化事件
	public void initEvent(){
		expandAll.addActionListener(this);
		collapaseAll.addActionListener(this);
	}
	//加入监听器
	public void addListener(TreePopMenuListener listener){
		synchronized(listeners){
			if(listener == null)
				return;
			listeners.add(listener);
		}
	}
	//移除监听器
	public void removeListener(TreePopMenuListener listener){
		synchronized(listeners){
			if(listener == null)
				return;
			listeners.remove(listener);
		}
	}
	//显示菜单
	public void show(JTree tree, Point p){
		this.tree = tree;
		this.p = p;
		this.remove(expandAll);
		this.remove(collapaseAll);
		setMenuEnabled(false);
		lastPopTreePath = tree.getPathForLocation(p.x, p.y);
		if(lastPopTreePath != null){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)lastPopTreePath.getLastPathComponent();
			//如果不为叶子节点
			if(!node.isLeaf()){
				this.add(expandAll);
				this.add(collapaseAll);
				setMenuEnabled(true);
			}
			//重新更改菜单中的状态
			updateMenuStatusByNode(node);
			tree.setSelectionPath(lastPopTreePath);
			if(this.getComponentCount()>0)
				super.show(tree, p.x, p.y);
		}
	}
	/**
	 * 根据节点更改菜单状态
	 * @param node 当前节点
	 */
	public void updateMenuStatusByNode(DefaultMutableTreeNode node){
		if(node == null)
			return;
		//如果是节子节点
		if(node.isLeaf()){
			Iterator<JMenuItem> it = menus.keySet().iterator();
			while(it.hasNext()){
				JMenuItem temp = it.next();
				String right = menus.get(temp);
				if(right.equals(ALL) || right.equals(LEAF))
					temp.setEnabled(true);
				else
					temp.setEnabled(false);
			}
		}else{	//如果是非叶子节点
			Iterator<JMenuItem> it = menus.keySet().iterator();
			while(it.hasNext()){
				JMenuItem temp = it.next();
				String right = menus.get(temp);
				if(right.equals(ALL) || right.equals(NONLEAF))
					temp.setEnabled(true);
				else
					temp.setEnabled(false);
			}
		}
	}
	//设置展开和收缩菜单按钮状态
	public void setMenuEnabled(boolean enabled){
		expandAll.setEnabled(enabled);
		collapaseAll.setEnabled(enabled);
	}
	//菜单按钮事件
	@Override
	public void actionPerformed(ActionEvent e) {
		if(tree == null || p == null){
			JOptionPane.showMessageDialog(tree, "weixuanzhong");//
			return;
		}
		if(e.getSource() == expandAll){
//			TreePath tp = tree.getPathForLocation(p.x, p.y);
			if(lastPopTreePath != null){
				nodeOperation(tree,lastPopTreePath,true);
			}
		}else if(e.getSource() == collapaseAll){
//			TreePath tp = tree.getPathForLocation(p.x, p.y);
			if(lastPopTreePath != null){
				nodeOperation(tree,lastPopTreePath,false);
			}
		}else{	//如果为用户自定义按钮，则返回给监听器
			if(lastPopTreePath != null){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)lastPopTreePath.getLastPathComponent();
				synchronized(listeners){
					TreePopMenuEvent event =new TreePopMenuEvent(e, node);
					for(TreePopMenuListener listener : listeners){
						listener.menuActionPerformed(event);
					}
				}
			}
		}
	}
	private void nodeOperation(JTree tree, TreePath path, boolean expand) {
		TreeNode node = (TreeNode)path.getLastPathComponent();
		if (node.getChildCount() > 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath childPath = path.pathByAddingChild(n);
				nodeOperation(tree, childPath, expand);
			}
		}
		if (expand) {
			tree.expandPath(path);
		} else {
			tree.collapsePath(path);
		}
	}
}
