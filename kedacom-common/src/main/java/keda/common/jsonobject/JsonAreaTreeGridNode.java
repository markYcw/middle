package keda.common.jsonobject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated 此类仅仅审讯业务系统可用，其它项目建议不要使用
 *  已知使用的类，kedaemap inquestweb
 */
public class JsonAreaTreeGridNode implements Serializable {
	private static final long serialVersionUID = 1L;
//	/**区域在线图片*/
//	public static final String PIC_AREA_ONLINE = "icon-area-tree-online";
//	/**区域不在线图片*/
//	public static final String PIC_AREA_OFFLINE = "icon-area-tree-offline";
//	/**区域状态在线标识*/
//	public static final int AREA_STATUS_ONLINE = 1;
//	/**区域状态不在线标识*/
//	public static final int AREA_STATUS_OFFLINE = 0;
	private String id;
	private String parentId;
	private String parentName;
	private String name;
	private String areaNumber;
	private String description;
	private Integer platformId;
	private String platformName;
	private String platformServer;
	private int platformPort;
	private String platformUsername;
	private String platformPassword;
	private boolean platformUseProxy;
	private String platformProxyServer;
	private Integer platformProxyPort;
	private String platformProxyUsername;
	private String platformProxyPassword;
	private String kdmno;
	private String iconCls;
	private int isuselds;
	private int status;
	private boolean isOnLine;	//在线与否的状态
	/**区域编号*/
	private String number;
	
	private JsonAreaTreeGridNode parent;
	
	private List<JsonAreaTreeGridNode> children = new ArrayList<JsonAreaTreeGridNode>();
	
	public JsonAreaTreeGridNode() {
		
	}
	
	public int getIsuselds() {
		return isuselds;
	}

	public void setIsuselds(int isuselds) {
		this.isuselds = isuselds;
	}

	public void setParent(JsonAreaTreeGridNode parent) {
		this.parent = parent;
	}

	public JsonAreaTreeGridNode getParent() {
		return parent;
	}

	public String getAreaNumber(){
		return areaNumber;
	}
	
	public void setAreaNumber(String areaNumber){
		this.areaNumber = areaNumber;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformServer() {
		return platformServer;
	}

	public void setPlatformServer(String platformServer) {
		this.platformServer = platformServer;
	}

	public int getPlatformPort() {
		return platformPort;
	}

	public void setPlatformPort(int platformPort) {
		this.platformPort = platformPort;
	}

	public String getPlatformUsername() {
		return platformUsername;
	}

	public void setPlatformUsername(String platformUsername) {
		this.platformUsername = platformUsername;
	}

	public String getPlatformPassword() {
		return platformPassword;
	}

	public void setPlatformPassword(String platformPassword) {
		this.platformPassword = platformPassword;
	}

	public boolean isPlatformUseProxy() {
		return platformUseProxy;
	}

	public void setPlatformUseProxy(boolean platformUseProxy) {
		this.platformUseProxy = platformUseProxy;
	}

	public String getPlatformProxyServer() {
		return platformProxyServer;
	}

	public void setPlatformProxyServer(String platformProxyServer) {
		this.platformProxyServer = platformProxyServer;
	}

	public Integer getPlatformProxyPort() {
		return platformProxyPort;
	}

	public void setPlatformProxyPort(Integer platformProxyPort) {
		this.platformProxyPort = platformProxyPort;
	}

	public String getPlatformProxyUsername() {
		return platformProxyUsername;
	}

	public void setPlatformProxyUsername(String platformProxyUsername) {
		this.platformProxyUsername = platformProxyUsername;
	}

	public String getPlatformProxyPassword() {
		return platformProxyPassword;
	}

	public void setPlatformProxyPassword(String platformProxyPassword) {
		this.platformProxyPassword = platformProxyPassword;
	}

	public List<JsonAreaTreeGridNode> getChildren() {
		return children;
	}

	public void setChildren(List<JsonAreaTreeGridNode> children) {
		this.children = children;
	}
	
	public String getKdmno() {
		return kdmno;
	}

	public void setKdmno(String kdmno) {
		this.kdmno = kdmno;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isOnLine() {
		return isOnLine;
	}

	public void setOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * @deprecated replace by{@link #getNumber()}
	 * @return
	 */
	public String getAreaCode() {
		return this.getNumber();
	}

	/**
	 * @deprecated replace by {@link #setNumber(String)}
	 * @param areaCode 
	 */
	public void setAreaCode(String areaCode) {
		this.setNumber(areaCode);
	}
	public void addChild(JsonAreaTreeGridNode area) {
		area.setParent(this);
		children.add(area);

/*		if(children.size() == 0)
			children.add(area);
		else {
			for(int i=0; i<children.size(); i++) {
				if(area.getName().compareToIgnoreCase(children.get(i).getName()) <= 0) {
					children.add(i, area);
					return;
				}
			}

			children.add(area);
		}*/
	}
}
