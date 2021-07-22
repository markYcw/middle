package com.kedacom.middleware.epro;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.epro.domain.EPro;
import com.kedacom.middleware.exception.KMException;
import keda.common.util.ATaskThread;

/**
 * @ClassName ProDemo
 * @Description 测试
 * @Author ycw
 * @Date 2021/06/30 16:14
 */
public class EProDemo {

    public static void main(String[] args) throws KMException {
        KM km = new KM();
        km.setServerIP("172.16.129.214");
        km.setServerPort(45670);
        km.start();
        //状态监听
        km.getEProClient().addListener(new EProNotifyListenerAdpater() {
            @Override
            public void onEProOffLine(String eProId, String eProIp) {
                System.out.println("====> onEProOffLine rkId=" + eProId + " ,rkIp=" + eProIp);
            }
        });
        //应用示例
        send(km);
    }

    static void send(KM km) throws KMException {
      final EProClient eProClient = km.getEProClient();

        String id = "1";
        String ip = "172.16.129.221";
        int port = 8888;

        final EPro ePro = new EPro();
        ePro.setId(id);
        ePro.setIp(ip);
        ePro.setPort(port);

        eProClient.addEPro(ePro);

        ATaskThread thread = new ATaskThread() {
            @Override
            public void doWork() throws Exception {
                int login = eProClient.login(ip, port);
                System.out.println("==================登录E10Rro成功：" + login);
            }
        };
        thread.setName("EPro-work");
        thread.setTimeout(20000);
        thread.start();
    }

}
