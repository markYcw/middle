package keda.common.gui.event;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;

public class TreePopMenuEvent {
	private ActionEvent ae;
	private DefaultMutableTreeNode node;
	public TreePopMenuEvent(){}
	public TreePopMenuEvent(ActionEvent ae, DefaultMutableTreeNode node){
		this.ae = ae;
		this.node = node;
	}
	public ActionEvent getAe() {
		return ae;
	}
	public void setAe(ActionEvent ae) {
		this.ae = ae;
	}
	public DefaultMutableTreeNode getNode() {
		return node;
	}
	public void setNode(DefaultMutableTreeNode node) {
		this.node = node;
	}
}
