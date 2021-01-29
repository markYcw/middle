package keda.common.jsonobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 此类仅仅审讯业务系统可用，其它项目建议不要使用。
 * 	已知使用的类，kedaemap inquestweb
 */
public class JsonAreaTreeNode {
	private JsonAreaTreeNode parent;
	
	private String id;
	private String text;
	private String iconCls;
	private boolean isOnLine;
	private Map<String, String> attributes = new HashMap<String, String>();
	private List<JsonAreaTreeNode> children = new ArrayList<JsonAreaTreeNode>();
	
	public JsonAreaTreeNode getParent() {
		return parent;
	}
	public void setParent(JsonAreaTreeNode parent) {
		this.parent = parent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public List<JsonAreaTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<JsonAreaTreeNode> children) {
		this.children = children;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isOnline() {
		return isOnLine;
	}
	public void setOnline(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}
	public void addChild(JsonAreaTreeNode node) {
		node.setParent(this);
		
		if(children.size() == 0) {
			children.add(node);
		} else {
			boolean inserted = false;
			for(int i=0; i<children.size(); i++) {
				if(node.getText().compareToIgnoreCase(children.get(i).getText()) <= 0) {
					children.add(i, node);
					inserted = true;
					break;
				} 
			}
			
			if(!inserted) {
				children.add(node);
			}
		}
	}
	
	public void setArea(String areaId, String areaName, String icon, boolean isOnLine, String platformId, String platformName, String parentId) {
		this.setId(areaId);
		this.setText(areaName);
		this.setIconCls(icon);
		this.isOnLine = isOnLine;
		attributes.put("platformId", platformId);
		attributes.put("platformName", platformName);
		attributes.put("parentId", parentId);
	}
}
