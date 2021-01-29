package com.kedacom.middleware.svr;

import keda.common.util.ATaskThread;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
* SVR连接监控线程
* @author DengJie
*
*/
public class SVRConnMonitorThread extends ATaskThread {

   private static final Logger log = Logger.getLogger(SVRConnMonitorThread.class);

   private SVRClient client;
   private Set<String> ips = new HashSet<String>();
   private int idleCount = 0;//空闲周期

   public SVRConnMonitorThread (SVRClient client){
       this.client = client;
   }
   @Override
   public void doWork() throws Exception {
       Set<String> _ips;
       synchronized(this.ips){
           _ips = new HashSet<String>(this.ips);
       }
       Set<String> success = new HashSet<String>();
       for(String ip : _ips){
           boolean ret = false;
           try{
               int ssid = client.login(ip);
               if(ssid >= 0){
                   success.add(ip);
                   ret = true;
               }
           }catch(Exception e){
               log.error("(svrIp="+ip+")"+e.getMessage(), e);
           }

           if(!ret){
               SVRSession session = client.getSessionManager().getSessionByIP(ip);
               if(session != null){
                   session.setStatus(SVRSessionStatus.FAILED);
               }
           }
       }

       synchronized(this.ips){
           this.ips.removeAll(success);
           if(this.ips.size() <= 0){
               //全部连接完成
               idleCount ++;
               if(idleCount >= 3){
                   //空闲三个周期，自动退出
                   super.stop();
               }
           }else{
               idleCount = 0;
           }
       }
   }

   public void addSVRByIp(String ip){
       synchronized (ips) {
           this.ips.add(ip);
       }
   }

   public void removeSVRByIp(String ip){
       synchronized (ips) {
           this.ips.remove(ip);
       }
   }
}