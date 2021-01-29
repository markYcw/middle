package com.kedacom.middleware.cu;

import keda.common.util.ATaskThread;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
* 监控平台连接监控线程
* @author TaoPeng
*
*/
public class CuConnMonitorThread extends ATaskThread {

   private static final Logger log = Logger.getLogger(CuConnMonitorThread.class);

   private CuClient client;
   private Set<Integer> ids = new HashSet<Integer>();
   private int idleCount = 0;//空闲周期

   public CuConnMonitorThread (CuClient client){
       this.client = client;
   }
   @Override
   public void doWork() throws Exception {
       Set<Integer> _ids;
       synchronized(this.ids){
           _ids = new HashSet<Integer>(this.ids);
       }
       Set<Integer> success = new HashSet<Integer>();
       for(int id : _ids){
           boolean isLogin = client.isLogin(id);
           if(isLogin){
               //已连接，不再连接
               continue;
           }
           boolean ret = false;
           Exception exp = null;
           try{
               int ssid = client.login(id);
               if(ssid >= 0){
                   success.add(id);
                   ret = true;
               }
           }catch(Exception e){
               exp = e;
               log.error("登录监控平台失败：(cuId="+id+")" + e.getMessage(), e);
           }

           CuSession session = client.getSessionManager().getSessionByCuID(id);
           if(session != null){
               if(!ret){
                   //登录失败
                   session.setStatus(CuSessionStatus.FAILED);
                   session.setException(exp);
                   this.onConnectStatus(id, CuSessionStatus.FAILED);
               }else{
                   //登录成功
                   session.setStatus(CuSessionStatus.CONNECTED);
                   session.setException(null);
                   this.onConnectStatus(id, CuSessionStatus.CONNECTED);
               }
           }else{
               if(!ret){
                   //2018-11-22 LinChaoYu session为空，说明登录失败，抛出异常，有的系统客户端需要感知每次连接状态
                   this.onConnectStatus(id, CuSessionStatus.FAILED);
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

   public void addMcuId(int id){
       synchronized (ids) {
           this.ids.add(id);
       }
   }

   public void removeMcuId(int id){
       synchronized (ids) {
           this.ids.remove(id);
       }
   }

   private void onConnectStatus(int cuId, CuSessionStatus status){
       for( CuNotifyListener l : client.getAllListeners()){
           l.onCuConnectStatus(cuId, status);
       }
   }
}