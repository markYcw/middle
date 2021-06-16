package com.kedacom.middleware.rk100;

import com.alibaba.fastjson.JSON;
import com.kedacom.middleware.KM;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.rk100.domain.RK;
import com.kedacom.middleware.rk100.response.GetAllLiveStateDetailResponse;
import com.kedacom.middleware.rk100.response.ListRkResponse;
import keda.common.util.ATaskThread;

import java.util.List;

/**
 * @ClassName RkDemo
 * @Description 测试
 * @Author zlf
 * @Date 2021/6/10 9:44
 */
public class RkDemo {

    public static void main(String[] args) throws KMException {
        KM km = new KM();
        km.setServerIP("172.16.129.214");
        km.setServerPort(45670);
        km.start();
        //状态监听
        km.getRkClient().addListener(new RkNotifyListenerAdpater() {
            @Override
            public void onRKOffLine(String rkId, String rkIp) {
                System.out.println("====> onRKOffLine rkId=" + rkId + " ,rkIp=" + rkIp);
            }
        });
        //应用示例
        send(km);
    }

    static void send(KM km) throws KMException {
        final RkClient rkClient = km.getRkClient();

        String id = "1";
        String ip = "172.16.129.214";
        int port = 1883;

        final RK rk = new RK();
        rk.setId(id);
        rk.setIp(ip);
        rk.setPort(port);

        rkClient.addRK(rk);

        ATaskThread thread = new ATaskThread() {
            @Override
            public void doWork() throws Exception {
//                ListRkResponse listRkResponse = rkClient.listRkInfos(rk);
//                System.out.println("返回值：" + listRkResponse);

//                List<GetAllLiveStateDetailResponse> allLiveState = rkClient.getAllLiveState(rk, "1");
//                System.out.println("返回值：" + JSON.toJSONString(allLiveState));

                int on = rkClient.devOnOff(rk, "1", "on", "2");
                System.out.println("返回值：" + on);
            }
        };
        thread.setName("RK-work");
        thread.setTimeout(20000);
        thread.start();
    }

}
