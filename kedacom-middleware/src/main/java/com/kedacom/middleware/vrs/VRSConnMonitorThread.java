package com.kedacom.middleware.vrs;

import keda.common.util.ATaskThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
* 录播服务器 连接监控线程
* @author LinChaoYu
*
*/
public class VRSConnMonitorThread extends ATaskThread {
   private static final Logger log = LogManager.getLogger(VRSConnMonitorThread.class);

   private VRSClient client;

   private Set<String> ids = new HashSet<String>();

   private int idleCount = 0;//空闲周期

   public VRSConnMonitorThread (VRSClient client){
       this.client = client;
   }

   @Override
   public void doWork() throws Exception {
       Set<String> _ids;
       synchronized(this.ids){
           _ids = new HashSet<String>(this.ids);
       }
       Set<String> success = new HashSet<String>();
       for(String id : _ids){
           boolean ret = false;
           try{
               int ssid = client.login(id);
               if(ssid >= 0){
                   success.add(id);
                   ret = true;
               }
           }catch(Exception e){
               log.error("(vrsId="+id+")"+e.getMessage(), e);
           }

           if(!ret){
               VRSSession session = client.getSessionManager().getSessionByID(id);
               if(session != null){
                   session.setStatus(VRSSessionStatus.FAILED);
               }
           }
       }

       synchronized(this.ids){
           this.ids.removeAll(success);
           if(this.ids.size() <= 0){
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

   public void addVRSId(String id){
       synchronized (ids) {
           this.ids.add(id);
       }
   }

   public void removeVRSId(String id){
       synchronized (ids) {
           this.ids.remove(id);
       }
   }
}