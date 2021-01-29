package keda.common.server.disk;

/**
 * 磁盘信息 
 * @author wjs
 *
 */
public class DiskInfo {
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_NONE = 1;
	public static final int TYPE_LOCAL_DISK = 2;
	public static final int TYPE_NETWORK = 3;
	public static final int TYPE_RAM_DISK = 4;
	public static final int TYPE_CDROM = 5;
	public static final int TYPE_SWAP = 6;
	
	//文件系统类型
	private int type;
	//分区的盘符名称
	private String dirName;
	//分区的盘符名称
	private String devName;
	//文件系统类型名称，如本地硬盘、光盘、网络文件系统等
	private String typeName;
	//文件系统类型，如FAT32、NTFS等
	private String sysTypeName;
	//总大小
	private long total;
	//空闲大小
	private long free;
	//已使用大小
	private long used;
	//可用大小
	private long avial;
	//利用率
	private double usePerce;
	//读出数据大小
	private long reads;
	//写入数据大小
	private long writes;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSysTypeName() {
		return sysTypeName;
	}
	public void setSysTypeName(String sysTypeName) {
		this.sysTypeName = sysTypeName;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getFree() {
		return free;
	}
	public void setFree(long free) {
		this.free = free;
	}
	public long getUsed() {
		return used;
	}
	public void setUsed(long used) {
		this.used = used;
	}
	public long getAvial() {
		return avial;
	}
	public void setAvial(long avial) {
		this.avial = avial;
	}
	public double getUsePerce() {
		return usePerce;
	}
	public void setUsePerce(double usePerce) {
		this.usePerce = usePerce;
	}
	public long getReads() {
		return reads;
	}
	public void setReads(long reads) {
		this.reads = reads;
	}
	public long getWrites() {
		return writes;
	}
	public void setWrites(long writes) {
		this.writes = writes;
	}
	public void print(){
		System.out.println("-------------------磁盘信息------------------");
		System.out.println("存储设备类型：" + type);
		System.out.println("分区挂载点："+ dirName);
		System.out.println("分区盘符名称："+ devName);
		System.out.println("存储设备类型名称："+ typeName);
		System.out.println("文件系统类型："+ sysTypeName);
		System.out.println("总大小："+ (total/1024/1024) + "G");
		System.out.println("空闲大小："+ (free/1024/1024) + "G");
		System.out.println("已使用大小："+ (used/1024/1024) + "G");
		System.out.println("可用大小："+ (avial/1024/1024) + "G");
		System.out.println("利用率："+ usePerce);
		System.out.println("读出数据大小："+ (reads/1024/1024) + "M");
		System.out.println("写入数据大小："+( writes/1024/1024) + "M");
		System.out.println("----------------------------------------------");
	}
}
