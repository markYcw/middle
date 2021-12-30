package com.kedacom.middleware.epro;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.epro.domain.EPro;
import com.kedacom.middleware.epro.response.StopRecResponse;
import com.kedacom.middleware.exception.KMException;

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
        String ip = "172.16.128.106";
        int port = 8888;

        final EPro ePro = new EPro();
        ePro.setId(id);
        ePro.setIp(ip);
        ePro.setPort(port);

        eProClient.addEPro(ePro);
        eProClient.login(id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eProClient.openCamera(ePro);
        eProClient.startRec(ePro);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StopRecResponse response = eProClient.stopRec(ePro);
        String audio = response.getAudio();
        String video = response.getVideo();
        eProClient.getRec(ePro,audio);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eProClient.getRec(ePro,video);
    }

}
