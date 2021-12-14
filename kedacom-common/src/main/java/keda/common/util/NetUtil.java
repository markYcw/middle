package keda.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtil {
	private static Logger log = LogManager.getLogger(NetUtil.class);
	/**
	# * 根据网卡取本机配置的IP
	# * 如果是双网卡的，则取出外网IP
	# * @return
	# */
	public static String getIp(){
		String localip=null;//本地IP，如果没有配置外网IP则返回它
		String netip=null;//外网IP
	 try {
				Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip = null;
				boolean finded=false;//是否找到外网IP
				while(netInterfaces.hasMoreElements() && !finded){
					NetworkInterface ni= netInterfaces.nextElement();
					Enumeration<InetAddress> address=ni.getInetAddresses();
					while(address.hasMoreElements()){
							ip=address.nextElement();
							if( !ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":")==-1){//外网IP
									netip=ip.getHostAddress();
									finded=true;
									break;
							}else if(ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":")==-1){//内网IP
									localip=ip.getHostAddress();
							}
					}
				}
		 } catch (SocketException e) {
			 log.error("get local ip failed",e);
		 }
		 if(netip!=null && !"".equals(netip)){
			 return netip;
		 }else{
			 return localip;
		 }
		 } 
}
