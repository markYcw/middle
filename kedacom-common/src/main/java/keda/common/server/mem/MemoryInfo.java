package keda.common.server.mem;

/**
 * 系统内存信息
 * @author wjs
 *
 */
public class MemoryInfo {
	//总量
	private long total;
	//当前使用量
	private long used;
	//当前剩余量
	private long free;
	//当前使用比例
	private double usedPercent;
	//当前剩余比例
	private double freePercent;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getUsed() {
		return used;
	}
	public void setUsed(long used) {
		this.used = used;
	}
	public long getFree() {
		return free;
	}
	public void setFree(long free) {
		this.free = free;
	}
	public double getUsedPercent() {
		return usedPercent;
	}
	public void setUsedPercent(double usedPercent) {
		this.usedPercent = usedPercent;
	}
	public double getFreePercent() {
		return freePercent;
	}
	public void setFreePercent(double freePercent) {
		this.freePercent = freePercent;
	}
	public void print(){
		System.out.println("-------------------内存信息------------------");
		System.out.println("总量：" + (total/1024/1024) + "M");
		System.out.println("当前使用量：" + (used/1024/1024) + "M");
		System.out.println("当前剩余量：" + (free/1024/1024) + "M");
		System.out.println("当前使用比例：" + usedPercent);
		System.out.println("当前剩余比例：" + freePercent);
		System.out.println("----------------------------------------------");
	}
	
}
