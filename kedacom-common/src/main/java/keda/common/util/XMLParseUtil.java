package keda.common.util;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLParseUtil {
	public static final String NODE_DEFAULT_VALUE_NAME = "value";
	public static final String NODE_RESP = "resp";
	public static final String NODE_COMMAND = "command";
	public static final String NODE_SESSION = "session";
	public static final String NODE_RET = "ret";
	public static final String NODE_ERR = "err";
	public static final String NODE_ERRdESC = "errdesc";
	public static final String NODE_PARAM = "param";
	
	/**
	 * 将xml字符串解析成Document
	 * @param xml
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Document parseXML(String xml) throws ParserConfigurationException, SAXException, IOException{
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory ch = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = ch.newDocumentBuilder() ;
		return db.parse(is) ;
	}
	/**
	 *  将流解析成Document
	 * @param is 输入流
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Document parseXML(InputStream is) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory ch = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = ch.newDocumentBuilder() ;
		return db.parse(is) ;
	}
	
	/**
	 * 返回文档中指定名称的节点,
	 * @param dom 文档
	 * @param tagName 字点名称
	 * @return　如果有多个同名的节点，返回首先找到的节点；如果没有定样的节点则返回null。
	 */
	public static Node getUniqueNode(Document dom, String tagName){
		NodeList list = dom.getElementsByTagName(tagName);
		int l = list.getLength();
		return l < 0 ? null : list.item(0);
	}
	
	/**
	 * 返回节点的指定子节点。
	 * @param parent 父节点
	 * @param tagName 子节点的名称
	 * @return 如果存在多个定样的节点，返回第一个；如果不存在定样的节点，返回null;
	 */
	public static Node getUniqueChildNode(Node parent, String tagName){
		NodeList chlds = parent.getChildNodes();
		for(int j2 = 0 ; j2 < chlds.getLength(); j2 ++){
			Node node = chlds.item(j2);
			String name = node.getNodeName();
			if(name != null && name.equals(tagName)){
				return node;
			}
		}
		return null;
	}
	/**
	 * 返回节点的指定子节点列表。
	 * @param parent 父节点
	 * @param tagName 子节点的名称
	 * @return 
	 */
	public static List<Node> getChildNodes(Node parent, String tagName){
		NodeList chlds = parent.getChildNodes();
		List<Node> list = new ArrayList<Node>(chlds.getLength());
		for(int j2 = 0 ; j2 < chlds.getLength(); j2 ++){
			Node node = chlds.item(j2);
			String name = node.getNodeName();
			if(name != null && name.equals(tagName)){
				list.add(node);
			}
		}
		return list;
	}
	public static String getTextContent(Node node) {
		if(node != null){
			return node.getTextContent();
		}
		return null;
	}
	/**
	 * 返回节点的属性值
	 * @param node　节点
	 * @return
	 */
	public static String getAttribute(Node node){
		return getAttribute(node, NODE_DEFAULT_VALUE_NAME);
	}
	/**
	 * 返回节点的属性值
	 * @param node　节点
	 * @param attribute　属性名称
	 * @return
	 */
	public static String getAttribute(Node node, String attributeName){
		NamedNodeMap attrs = node.getAttributes();
		Node attrNode = attrs.getNamedItem(attributeName);
		return attrNode != null ? attrNode.getNodeValue() : null;
	}
	/**
	 * 返回节点的属性值(数值)
	 * @param node　节点
	 * @param attribute　属性名称
	 * @return
	 */
	public static int getIntAttribute(Node node, String attributeName){
		String v = getAttribute(node, attributeName);
		if("null".equalsIgnoreCase(v)){
			return 0;
		}
		return v!= null ? Integer.parseInt(v) : 0;
	}
	/**
	 * 返回节点的属性值(数值)
	 * @param node　节点
	 * @return
	 */
	public static long getLongAttribute(Node node){
		return getLongAttribute(node, NODE_DEFAULT_VALUE_NAME);
	}
	/**
	 * 返回节点的属性值(数值)
	 * @param node　节点
	 * @param attribute　属性名称
	 * @return
	 */
	public static long getLongAttribute(Node node, String attributeName){
		String v = getAttribute(node, attributeName);
		if("null".equalsIgnoreCase(v)){
			return 0;
		}
		return v!= null ? Long.parseLong(v) : 0L;
	}
	/**
	 * 返回节点的属性值(数值)
	 * @param node　节点
	 * @return
	 */
	public static int getIntAttribute(Node node){
		return getIntAttribute(node, NODE_DEFAULT_VALUE_NAME);
	}
}
