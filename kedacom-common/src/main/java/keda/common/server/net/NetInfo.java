package keda.common.server.net;

/**
 * 网络信息
 * @author wjs
 *
 */
public class NetInfo {
	//设备名称
	private String name;
	//IP地址
	private String ip;
	//子网掩码
	private String mask;
	//网卡MAC地址
	private String hwaddr;
	//广播地址
	private String broadcast;
	//网卡描述信息
	private String description;
	//接收总包数
	private long rxPackets;
	//发送总包数
	private long txPackets;
	//接收总字节数
	private long rxBytes;
	//发送总字节数
	private long txBytes;
	//接收错误包数
	private long rxErrors;
	//发送错误包数
	private long txErrors;
	//接收丢弃包数
	private long rxDropped;
	//发送丢弃包数
	private long txDrooped;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getHwaddr() {
		return hwaddr;
	}
	public void setHwaddr(String hwaddr) {
		this.hwaddr = hwaddr;
	}
	public String getBroadcast() {
		return broadcast;
	}
	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getRxPackets() {
		return rxPackets;
	}
	public void setRxPackets(long rxPackets) {
		this.rxPackets = rxPackets;
	}
	public long getTxPackets() {
		return txPackets;
	}
	public void setTxPackets(long txPackets) {
		this.txPackets = txPackets;
	}
	public long getRxBytes() {
		return rxBytes;
	}
	public void setRxBytes(long rxBytes) {
		this.rxBytes = rxBytes;
	}
	public long getTxBytes() {
		return txBytes;
	}
	public void setTxBytes(long txBytes) {
		this.txBytes = txBytes;
	}
	public long getRxErrors() {
		return rxErrors;
	}
	public void setRxErrors(long rxErrors) {
		this.rxErrors = rxErrors;
	}
	public long getTxErrors() {
		return txErrors;
	}
	public void setTxErrors(long txErrors) {
		this.txErrors = txErrors;
	}
	public long getRxDropped() {
		return rxDropped;
	}
	public void setRxDropped(long rxDropped) {
		this.rxDropped = rxDropped;
	}
	public long getTxDrooped() {
		return txDrooped;
	}
	public void setTxDrooped(long txDrooped) {
		this.txDrooped = txDrooped;
	}
	
	public void print(){
		System.out.println("-------------------网络信息------------------");
		System.out.println("名称：" +name);
		System.out.println("IP地址：" +ip);
		System.out.println("子网掩码：" +mask);
		System.out.println("网卡MAC地址：" +hwaddr);
		System.out.println("广播地址：" +broadcast);
		System.out.println("网卡描述信息：" +description);
		System.out.println("接收总包数：" +rxPackets);
		System.out.println("发送总包数：" +txPackets);
		System.out.println("接收总字节数：" +(rxBytes/1024/1024) + "M");
		System.out.println("发送总字节数：" +(txBytes/1024/1024) + "M");
		System.out.println("接收错误包数：" +rxErrors);
		System.out.println("发送错误包数：" +txErrors);
		System.out.println("接收丢弃包数：" +rxDropped);
		System.out.println("发送丢弃包数：" +txDrooped);
		System.out.println("----------------------------------------------");
	}
}
