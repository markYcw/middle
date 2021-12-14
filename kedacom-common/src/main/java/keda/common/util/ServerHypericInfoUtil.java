package keda.common.util;

import keda.common.server.cpu.CpuInfo;
import keda.common.server.disk.DiskInfo;
import keda.common.server.mem.MemoryInfo;
import keda.common.server.net.NetInfo;
import keda.common.server.os.OsInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hyperic.sigar.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


/**
 * 查看服务器操作系统、物理硬件等信息
 * 现包括：操作系统信息、CPU信息、内存信息、磁盘信息、网卡信息
 * 使用说明，需要引入 sigar-1.6.4.jar和log4j-1.2.16.jar包，还需要将kedares工程下的so\sigar下的dll或so库导入到服务器默认系统库中，
 *如：服务器使用的是32位Linux操作系统，则需要将kedares\so\sigar\linux\32\libsigar-x86-linux.so导入到服务是的/usr/lib目录下
 *完成以上步骤后即可正常使用该类
 * @author wjs
 *
 */
public class ServerHypericInfoUtil {
	
	public final static Logger log = LogManager.getLogger(ServerHypericInfoUtil.class);
	
	
	/**
	 * 获取操作系统信息、CPU信息、内存信息、磁盘信息、网卡信息 
	 * @return 将所有信息以json字符串的格式保存到list集合中
	 */
	public static List<String> getInfo(){
		
		JSONObject osmap = new JSONObject();
		JSONObject cpumap = new JSONObject();
		JSONObject memmap = new JSONObject();
		JSONObject swapmemmap = new JSONObject();
		JSONObject diskmap = new JSONObject();
		JSONObject netmap = new JSONObject();
		
		List<String> list = new ArrayList<String>();
//		ServerHypericInfoUtil sys = new ServerHypericInfoUtil();
		try {
			//os信息
			osmap.put("name","osinfo");
			osmap.put("value",JSONObjectUtil.toJsonString(getOsInfo(), false));
			list.add(osmap.toString());
			
			//cpu信息
			cpumap.put("name","cpuinfo");
			cpumap.put("value",JSONObjectUtil.toJsonString(getCpuInfo(), false));
			list.add(cpumap.toString());
			
			//内存信息
			memmap.put("name","meminfo");
			memmap.put("value",JSONObjectUtil.toJsonString(getMemInfo(), false));
			list.add(memmap.toString());
			
			//交换内存信息
			swapmemmap.put("name","swapMeminfo");
			swapmemmap.put("value",JSONObjectUtil.toJsonString(getSwapMemInfo(), false));
			list.add(swapmemmap.toString());
			
			//disk信息 保留type=2类型的文件 其余的删除
			List<DiskInfo> disklist = getDiskInfo();
			Iterator<DiskInfo> it = disklist.iterator();
			while(it.hasNext()){
				DiskInfo info = it.next();
				if(info.getType() < 2 || info.getType() > 6){
					it.remove();
				}
			}
			diskmap.put("name","diskinfo");
			diskmap.put("value",JSONObjectUtil.toJsonString(disklist, false));
			list.add(diskmap.toString());
			
			//网卡信息 保留MAC地址：F8:BC:12:60:1F:5F的网卡信息 网卡信息保存在netInterface.properties配置文件中
			Properties props = new Properties();  
			InputStream in = ServerHypericInfoUtil.class.getClassLoader().getResourceAsStream("netInterface.properties");
			props.load(in);  
			String addr = props.getProperty("有线网卡MAC");
			
			List<NetInfo> nis = getNetInfo();
			Iterator<NetInfo> its = nis.iterator();
			while(its.hasNext()){
				NetInfo info = its.next();
				if(!info.getHwaddr().equals(addr)){
					its.remove();
				}
			}
			netmap.put("name","netinfo");
			netmap.put("value",JSONObjectUtil.toJsonString(nis, false));
			list.add(netmap.toString());
		} catch (SigarException e) {
			log.error("data fail:", e);
		} catch (IOException e) {
			log.error("data fail:", e);
		} catch (JSONException e) {
			log.error("data fail:", e);
		}
		return list;
	}
	/**
	 * 获取操作系统信息
	 * @return
	 * @throws SigarException
	 */
	public static OsInfo getOsInfo()  throws SigarException {
		OperatingSystem os = OperatingSystem.getInstance();
		OsInfo oi = new OsInfo();
		oi.setName(os.getVendorName());
		oi.setArch(os.getArch());
		oi.setVersion(os.getVersion());
		oi.setDes(os.getDescription());
		return oi;
	}
	
	/**
	 * 获取处理器详细信息
	 * @return
	 * @throws SigarException
	 */
	public static List<CpuInfo> getCpuInfo() throws SigarException {
		Sigar sigar = null;
		List<CpuInfo> cis = new ArrayList<CpuInfo>();
		try{
			sigar = new Sigar();
			org.hyperic.sigar.CpuInfo[] cs = sigar.getCpuInfoList();
			CpuPerc[] percs = sigar.getCpuPercList();
			if(cs != null && percs != null && cs.length != 0){
				for(int index=0; index < cs.length; index++){
					org.hyperic.sigar.CpuInfo temp = cs[index];
					CpuPerc perc = percs[index];
					CpuInfo ci = new CpuInfo();
					ci.setId(index);
					ci.setCacheSize(temp.getCacheSize());
					ci.setModel(temp.getModel());
					ci.setVendor(temp.getVendor());
					//主频是x.xx GHz 格式
					DecimalFormat df = new DecimalFormat( "0.00 "); 
					ci.setGHz(Float.parseFloat(df.format(temp.getMhz()/1000.0)));
					if(perc != null){
						ci.setIdle(perc.getIdle());
						ci.setCombined(perc.getCombined());
						ci.setUser(perc.getUser());
						ci.setSys(perc.getSys());
						ci.setNice(perc.getNice());
						ci.setWait(perc.getWait());
					}
					cis.add(ci);
				}
			}
		}finally{
			if(sigar != null)
				sigar.close();
		}
		return cis;
	}
	/**
	 * 服务器物理内存使用情况
	 * @return
	 * @throws SigarException
	 */
	public static MemoryInfo getMemInfo() throws SigarException {
		Sigar sigar = null;		
		try{
			sigar = new Sigar();
			Mem mem = sigar.getMem();
			if(mem != null){
				MemoryInfo me = new MemoryInfo();
				me.setTotal(mem.getTotal());
				me.setUsed(mem.getActualUsed());
				me.setFree(mem.getActualFree());
				me.setUsedPercent(mem.getUsedPercent());
				me.setFreePercent(mem.getFreePercent());
				return me;
			}
		}finally{
			if(sigar != null)
				sigar.close();
		}
		return null;
	}
	/**
	 * 服务器交换内存使用情况
	 * @return
	 * @throws SigarException
	 */
	public static MemoryInfo getSwapMemInfo() throws SigarException {
		Sigar sigar = null;		
		try{
			sigar = new Sigar();
			Swap swap = sigar.getSwap();
			if(swap != null){
				MemoryInfo me = new MemoryInfo();
				me.setTotal(swap.getTotal());
				me.setUsed(swap.getUsed());
				me.setFree(swap.getFree());
				return me;
			}
		}finally{
			if(sigar != null)
				sigar.close();
		}
		return null;
	}
	
	/**
	 * 获取磁盘使用情况
	 * @return
	 * @throws SigarException
	 */
	public static List<DiskInfo> getDiskInfo() throws SigarException {
		Sigar sigar = null;
		List<DiskInfo> dis = new ArrayList<DiskInfo>();
		try{
			sigar = new Sigar();
			
			FileSystem[] fileSystems = sigar.getFileSystemList();			
			if(fileSystems != null && fileSystems.length > 0){
				for(int index=0; index<fileSystems.length; index++){
					FileSystem temp = fileSystems[index];
					
					DiskInfo di = new DiskInfo();
					di.setDirName(temp.getDirName());
					di.setDevName(temp.getDevName());
					di.setTypeName(temp.getTypeName());
					di.setSysTypeName(temp.getSysTypeName());
					di.setType(temp.getType());
					FileSystemUsage fsu = null;
					try{
						fsu = sigar.getFileSystemUsage(temp.getDirName());
					}catch(SigarException ex){//当temp.getType()为5时会出现异常，一般此时文件系统类型为光驱
						continue;
					}
					if(fsu != null){
						di.setTotal(fsu.getTotal());
						di.setFree(fsu.getFree());
						di.setUsed(fsu.getUsed());
						di.setUsePerce(fsu.getUsePercent());
						di.setAvial(fsu.getAvail());
						di.setReads(fsu.getDiskReadBytes());
						di.setWrites(fsu.getDiskWriteBytes());
					}
					dis.add(di);
				}
			}
		}finally{
			if(sigar != null)
				sigar.close();
		}
		return dis;
	}
	
	/**
	 * 获取网卡信息
	 * @return
	 * @throws SigarException
	 */
	public static List<NetInfo> getNetInfo() throws SigarException {
		Sigar sigar = null;
		List<NetInfo> nis = new ArrayList<NetInfo>();
		try{
			sigar = new Sigar();
			String[] ifNames = sigar.getNetInterfaceList();			
			if(ifNames != null && ifNames.length > 0){
				for(int index=0; index<ifNames.length; index++){
					NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ifNames[index]);
					NetInfo ni = new NetInfo();
					ni.setName(ifNames[index]);
					ni.setMask(ifConfig.getNetmask());
					ni.setIp(ifConfig.getAddress());
					ni.setHwaddr(ifConfig.getHwaddr());
					ni.setBroadcast(ifConfig.getBroadcast());
					ni.setDescription(ifConfig.getDescription());
					ifConfig.getBroadcast();
					if((ifConfig.getFlags() & 1L) <= 0L){
						continue;
					}
					NetInterfaceStat ifStat = sigar.getNetInterfaceStat(ifNames[index]);					
					ni.setRxPackets(ifStat.getRxPackets());
					ni.setTxPackets(ifStat.getTxPackets());
					ni.setRxBytes(ifStat.getRxBytes());
					ni.setTxBytes(ifStat.getTxBytes());
					ni.setRxErrors(ifStat.getRxErrors());
					ni.setTxErrors(ifStat.getTxErrors());
					ni.setRxDropped(ifStat.getRxDropped());
					ni.setTxDrooped(ifStat.getTxDropped());
					nis.add(ni);
				}
			}
		}finally{
			if(sigar != null)
				sigar.close();
		}
		return nis;
	}

}
