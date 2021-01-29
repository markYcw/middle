package keda.common.server.cpu;

/**
 * cpu信息
 * @author wjs
 *
 */
public class CpuInfo {
	//处理器核心数编号
	private int id;
	//主频GHz
	private float GHz;
	//缓冲存储器数量
	private long cacheSize;
	//类别，如：celeron
	private String model;
	//厂商，如：Intel
	private String vendor;
	//用户使用率
	private double user;
	//系统使用率
	private double sys;
	//当前等待率
	private double wait;
	//错误率
	private double nice;
	//当前空闲率
	private double idle;
	//总的使用率
	private double combined;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public double getIdle() {
		return idle;
	}
	public void setIdle(double idle) {
		this.idle = idle;
	}
	public double getCombined() {
		return combined;
	}
	public void setCombined(double combined) {
		this.combined = combined;
	}
	public float getGHz() {
		return GHz;
	}
	public void setGHz(float GHz) {
		this.GHz = GHz;
	}
	public double getUser() {
		return user;
	}
	public void setUser(double user) {
		this.user = user;
	}
	public double getSys() {
		return sys;
	}
	public void setSys(double sys) {
		this.sys = sys;
	}
	public double getWait() {
		return wait;
	}
	public void setWait(double wait) {
		this.wait = wait;
	}
	public double getNice() {
		return nice;
	}
	public void setNice(double nice) {
		this.nice = nice;
	}
	public void print(){
		System.out.println("-------------------CPU" + id + "信息------------------");
		System.out.println("处理器核心数编号：" + id);
		System.out.println("主频GHz：" + GHz);
		System.out.println("缓冲存储器数量：" + cacheSize);
		System.out.println("类别：" + model);
		System.out.println("厂商：" + vendor);
		System.out.println("用户使用率：" + user);
		System.out.println("系统使用率：" + sys);
		System.out.println("当前等待率：" + wait);
		System.out.println("错误率：" + nice);
		System.out.println("当前空闲率：" + idle);
		System.out.println("总的使用率：" + combined);
		System.out.println("----------------------------------------------");
	}
}
