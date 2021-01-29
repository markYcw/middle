package keda.common.util;

import keda.common.exception.runtime.NotNormalEndException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RuntimeUtil {
	private static final Logger log = Logger.getLogger(RuntimeUtil.class);
	public static void exec(String path) throws IOException{
		Runtime.getRuntime().exec(path);
	}
	/**
	 * 运行系统命令或shell脚本
	 * @param cmd 系统命令或shell脚本
	 * @param syn 同步或异步，true：同步输出；false：异步输出
	 * @param endFlag 结束标记，只有当syn为true时才会判断，如果为NULL时则不判断
	 * @throws IOException 远行异常
	 * @throws InterruptedException 
	 */
	public static void exec(String cmd, boolean syn, String endFlag) throws IOException, InterruptedException, NotNormalEndException {
		Runtime r = Runtime.getRuntime();
		String command[] = new String[]{"/bin/sh","-c", cmd};
		Process proc = r.exec(command);
		try{
			if(syn){
				InputStream in = proc.getInputStream();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while((line =br.readLine())!=null){
					log.debug("do cmd : " + cmd + " output : " + line);
					if(line.equals(endFlag)){
						break;
					}
				}
				in.close();
				isr.close();
				br.close();
				if(endFlag != null && !endFlag.equals(line)){
					throw new NotNormalEndException("调用命令"+cmd+"，未正常结束。");
				}
				int resultInt = proc.waitFor();
				if(resultInt != 0){
					InputStream errorIS = proc.getErrorStream();
					InputStreamReader errorISR = new InputStreamReader(errorIS);
					BufferedReader errorBR = new BufferedReader(errorISR);
					log.error("recover to database is error:");
					while((line =errorBR.readLine())!=null){
						log.error(line);
						if(line.indexOf("inflating")>0){
							line = line.trim();
							break;
						}
					}
					errorBR.close();
					throw new IOException("调用命令或脚本出错， cmd :" + cmd);
				}
			}
		}finally{
			proc.destroy();
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException, NotNormalEndException {
		exec("/root/Desktop/test.sh", true, "reset database end.");
	}
}
