package com.common; 

/**
 * 功能的设备状态（为了整合连接安防服务器的不同接口）。
 * @author LinChaoYu
 * 
 */
public class JsonDeviceStatus {

	/**
	 * 设备标识（信息标识），例如数据库相应信息的主键。
	 */
	private int id;

	/**
	 * 设备接入服务器标识(包括监控平台)
	 */
	private int dasId;

	/**
	 * 设备名称
	 */
	private String name;

	/**
	 * 所属区域
	 */
	public String area;
	
	/**
	 * 单位编号
	 */
	public String domainNumber;
	
	/**
	 * 设备单位
	 */
	private String domainName;
	
	/**
	 * 设备类型
	 */
	private int type;

	/**
	 * 设备类型名称
	 */
	private String typeName;
	
	/**
	 * 设备状态值
	 */
	private int status;

	/**
	 * 设备状态名称
	 */
	private String statusName;
	
	/**
	 * 设备描述
	 */
	private String description;
	
	/**
	 * 获取类型名称
	 * @param type
	 * @return
	 */
	public void setTypeName(int type){
		this.type = type;
		if(type == 1){
			this.typeName = "报警主机";
		}else if(type == 2){
			this.typeName =  "门禁主机";
		}else if(type == 3){ 
			this.typeName = "巡更主机";
		}else if(type == 4){ 
			this.typeName = "对讲主机";
		}else if(type == 777){ 
			this.typeName = "监控设备";
		}else if(type == 5){ 
			this.typeName = "LED";
		}else if(type == 6){ 
			this.typeName = "短信猫";
		}else if(type == 7){ 
			this.typeName = "广播主机";
		}else if(type == 8){ 
			this.typeName = "高压电网";
		}else if(type == 10){ 
			this.typeName = "智能主机";
		}else if(type == 32){ 
			this.typeName = "科达编码器(视频监控设备)";
		}else if(type == 33){ 
			this.typeName = "科达解码器(视频监控设备)";
		}else if(type == 34){ 
			this.typeName = "科达电视墙";
		}else if(type == 35){ 
			this.typeName = "科达电视墙预案";
		}else{
			this.typeName = "未知类型："+type;
		}
	}
	
	public void setStatusName(String status){
		if("ONLINE".equals(status)){
			this.statusName =  "在线";
		}else if("UNLINE".equals(status)){
			this.statusName =  "离线";
		}else{
			this.statusName =  status;
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDasId() {
		return dasId;
	}
	public void setDasId(int dasId) {
		this.dasId = dasId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDomainNumber() {
		return domainNumber;
	}
	public void setDomainNumber(String domainNumber) {
		this.domainNumber = domainNumber;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
