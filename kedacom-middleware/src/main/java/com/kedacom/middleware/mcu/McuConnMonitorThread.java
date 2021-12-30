package com.kedacom.middleware.mcu;

import com.kedacom.middleware.exception.RemoteException;
import keda.common.util.ATaskThread;


import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
* 会议平台连接监控线程
* @author TaoPeng
*
*/
public class McuConnMonitorThread extends ATaskThread {

   private static final Logger log = Logger.getLogger(McuConnMonitorThread.class);

   private McuClient client;
   private Set<String> ids = new HashSet<String>();
   private int idleCount = 0;//空闲周期

   public McuConnMonitorThread (McuClient client){
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
           } catch (RemoteException e){
               int errorCode = e.getErrorcode();
               if(errorCode==19002){
                   log.error("登录MCU失败用户名或密码错误(mcuId="+id+")"+e.getMessage(), e);
                   //如果是用户名密码错误则不再无限重连
                   super.stop();
               }
               log.error("登录MCU失败(mcuId="+id+")"+e.getMessage(), e);
           }catch(Exception e){
               log.error("登录MCU失败(mcuId="+id+")"+e.getMessage(), e);
           }

           if(!ret){
               McuSession session = client.getSessionManager().getSessionByID(id);
               if(session != null){
                   session.setStatus(McuSessionStatus.FAILED);
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

   public void addMcuId(String id){
       synchronized (ids) {
           this.ids.add(id);
       }
   }

   public void removeMcuId(String id){
       synchronized (ids) {
           this.ids.remove(id);
       }
   }
}