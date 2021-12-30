package com.kedacom.middleware.gk;

import keda.common.util.ATaskThread;


import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
* GK连接监控线程
* @author LinChaoYu
*
*/
public class GKConnMonitorThread extends ATaskThread {
   private static final Logger log = Logger.getLogger(GKConnMonitorThread.class);

   private GKClient client;

   private Set<String> ids = new HashSet<String>();

   private int idleCount = 0;//空闲周期

   public GKConnMonitorThread (GKClient client){
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
               log.error("(gkId="+id+")"+e.getMessage(), e);
           }

           if(!ret){
               GKSession session = client.getSessionManager().getSessionByID(id);
               if(session != null){
                   session.setStatus(GKSessionStatus.FAILED);
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

   public void addGKId(String id){
       synchronized (ids) {
           this.ids.add(id);
       }
   }

   public void removeGKId(String id){
       synchronized (ids) {
           this.ids.remove(id);
       }
   }
}