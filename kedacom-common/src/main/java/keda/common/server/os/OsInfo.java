package keda.common.server.os;

/**
 * 操作系统信息
 * @author wjs
 *
 */
public class OsInfo {
	//操作系统名称
	private String name;
	//操作系统内核类型，如：i386、486、586、x86等
	private String arch;
	//操作系统版本号
	private String version;
	//操作系统描述
	private String des;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArch() {
		return arch;
	}
	public void setArch(String arch) {
		this.arch = arch;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	public void print(){
		System.out.println("-------------------操作系统信息------------------");
		System.out.println("操作系统名称：" + name);
		System.out.println("操作系统内核：" + arch);
		System.out.println("操作系统版本：" + version);
		System.out.println("操作系统描述：" + des);
		System.out.println("----------------------------------------------");
	}
}
