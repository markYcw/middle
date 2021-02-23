package com.kedacom.middleware.cu;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.cu.devicecache.CuDeviceCache;
import com.kedacom.middleware.cu.domain.*;
import com.kedacom.middleware.cu.notify.GetTvWallNotify;
import com.kedacom.middleware.cu.notify.GetTvWallSchemeNotify;
import com.kedacom.middleware.exception.KMException;
import keda.common.util.TimeUtil;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 测试
 *
 * @author TaoPeng
 */
public class CuDemo {

    private static final Logger log = Logger.getLogger(CuDemo.class);

    public static void main(String[] args) {
        demo();
    }

    public static void demo() {
        try {
            init_KM();
            init_Cu();
        } catch (KMException e) {
            e.printStackTrace();
        }
    }

    /**
     * 示例程序代码
     *
     * @throws KMException
     */
    private static void init_KM() throws KMException {

        //开始连接中间件（进程的整个生命周期，此逻辑只需要被调用一次）
        KM km = KM.getInstance();
//		KM km = new KM();
//		m.setServerIP("172.16.128.80");
        km.setServerIP("172.16.129.214");
//		km.setServerIP("127.0.0.1");
        km.setServerPort(45670);
        km.start();

        CuClient cuClient = km.getCuClient();
        cuClient.addListener(new CuNotifyListenerAdpater() {

            @Override
            public void onGps(String puid, List<Gps> gpsList) {
                for (Gps s : gpsList) {
//					JSONObject obj = new JSONObject(s);
                    log.debug("GPS: puid=" + puid + ", gps=" + s);
                }
            }

            @Override
            public void onDeviceStatus(String puid, boolean online) {
                log.debug("设备状态: puid=" + puid + ", online=" + online);
            }

            @Override
            public void onDeviceOut(int cuId, String puid) {
                log.debug("设备退网: puid=" + puid);
            }

            @Override
            public void onCuOffine(int cuId) {
                log.debug("监控平台下线: cuId=" + cuId);
            }

            @Override
            public void onChannelStatus(String puid, List<PChannelStatus> status) {
                for (PChannelStatus s : status) {
//					JSONObject obj = new JSONObject(s);
                    log.debug("视频源状态: puid=" + puid + ", status=" + s);
                }

            }
        });
    }

    private static void init_Cu() {

        KM km = KM.getInstance();
        //KM km = new KM();
        final CuClient client = km.getCuClient();

        final int cuId = 81;
        Cu cu = new Cu();
        cu.setVersion(Cu.CU2);
        cu.setId(cuId);
        cu.setIp("172.16.231.38");
        cu.setPort(80);
        cu.setName("监控平台2.0");
        cu.setUsername("admin");
        cu.setPassword("kedacom#123");

        client.addCu(cu);  //相当于KplatformServer.regist()

        Thread thread = new Thread() {
            Object lock = new Object();

            @Override
            public void run() {
                while (true) {

                    if (client.isLogin(cuId)) {




                        testCu_base(cuId);
                        boolean isLoadComplete = client.getSessionManager().getSessionByCuID(cuId).getDeviceCache().isLoadComplete();
                        if (isLoadComplete) {
                            //testCu_device(cuId);
                        } else {
                            log.debug("平台设备正在加载中");
                        }

                        try {
                            boolean tvWall = client.getCuOperate().getTvWall(cuId);
                            try {
                                //睡眠0.2s
                                Thread.currentThread().sleep(200);
                            } catch (InterruptedException e) {
                                log.error("获取电视墙线程睡眠0.2秒失败");
                            }
                            List<TvWall> tvWalls = GetTvWallNotify.tvWalls;
                            if (tvWalls!=null) {
                                //testCu_device(cuId);
                            } else {
                                log.debug("平台设备正在加载中");
                            }
                        } catch (KMException e) {
                            e.printStackTrace();
                        }

                        try {
                            client.getCuOperate().loadTvWallScheme(cuId, "9c439fea5e5640609fe6911c3abde964@xinyangzhidui", "334");

                        } catch (KMException e) {
                            e.printStackTrace();
                        }

                    } else {
                        log.debug("平台未连接");
                    }

                    synchronized (lock) {
                        try {
                            lock.wait(15000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        thread.start();



    }

    private static void testCu_base(int cuId) {
        KM km = KM.getInstance();
        //KM km = new KM();
        CuClient client = km.getCuClient();
        CuOperate op = client.getCuOperate();

        log.debug("======================================");
        log.debug("监控平台ID：" + cuId);

        //平台域
        log.debug("----------------域信息------------------");
        try {
            String domain = op.getLocalDomain(cuId);
            log.debug("本级域：" + domain);
        } catch (KMException e) {
            log.error("获取平台域失败", e);
        }

        try {
            List<Domain> list = op.listDomain(cuId);
            for (Domain l : list) {
                log.debug("下级域：" + l.getDomainid());
            }
        } catch (KMException e) {
            log.error("获取本级、下级域...失败", e);
        }

        //平台时间
        log.debug("---------------------------------------");
        try {
            long time = op.getTime(cuId);
            log.debug("平台时间：" + new Date(time));

        } catch (KMException e) {
            log.error("获取平台时间失败", e);
        }

    }

    private static void testCu_device(int cuId) {

        KM km = KM.getInstance();
        //KM km = new KM();
        CuClient client = km.getCuClient();
        CuOperate op = client.getCuOperate();
        CuSession session = client.getSessionManager().getSessionByCuID(cuId);
        CuDeviceCache deviceCache = session.getDeviceCache();

        //设备列表
        log.debug("---------------------------------------");
        List<PGroup> groups = deviceCache.getGroups();
        for (PGroup g : groups) {
            log.debug("---------------------------------------");
            log.debug("平台分组：" + g.getName());
            List<PDevice> devices = deviceCache.getDeivcesByGroupId(g.getId());
            for (PDevice d : devices) {
                log.debug("\t puid: " + d.getPuid() + ", 设备名称：" + d.getName() + ", isOnline=" + d.isOnline());
                if (d.getPuid().equals("fa99275f2f8c463da6494bf1b4dd841d@kedacom115")) {
                    log.debug(23);
                }
                List<PChannel> channels = d.getChannels();
                for (PChannel chl : channels) {
                    log.debug("\t\t视频源： sn=" + chl.getSn() + "; name=" + chl.getName() + ", isOnline=" + chl.isOnline());
                }

            }
        }

        //2.0设备编号转1.0设备编号
        log.debug("---------------------------------------");
        //55010000000000000011100027800000@kedacom115 = 法庭102
        String puid1 = "55010000000000000011100027800000@kedacom115"; //TODO 调试的时候，请修改此参数的值
        try {
            String puid2 = op.getKdmno(puid1);
            log.debug("设备编号1.0：" + puid1 + "; 设备编号2.0：" + puid2);
        } catch (KMException e) {
            log.error("转换设置编号失败", e);
        }

        //按天的录像日历
        log.debug("---------------------------------------");
        String domain = null;
        String puid = puid1;    //TODO 调试的时候，请修改此参数的值
        int chnid = 0;            //TODO 调试的时候，请修改此参数的值
        int type = 1; //录像类型: 1平台录像, 2 前端录像
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        startDay = TimeUtil.getMonthStart(startDay);
        endDay = TimeUtil.getMonthEnd(endDay);
        try {
            int[] rets = op.queryRecdays(domain, puid, chnid, startDay.getTime(), endDay.getTime(), type);
            log.debug("录像日历：" + rets);
        } catch (KMException e) {
            log.error("查询录像日历失败", e);
        }


        //一天内的录像时间段
        log.debug("---------------------------------------");
        startDay = Calendar.getInstance();
        endDay = Calendar.getInstance();
        startDay = TimeUtil.getDayStart(startDay);
        endDay = TimeUtil.getDayEnd(endDay);

        try {
            log.debug("录像时间段：");
            List<Times> list = op.queryRecTime(domain, puid, chnid, startDay.getTime(), endDay.getTime(), type);
            for (Times t : list) {
                log.debug("\tstart=" + t.getStarttime() + "; end=" + t.getEndtime());
            }
        } catch (KMException e) {
            log.error("查询一天的录像失败", e);
        }
        try {
//			puid = puid;		//TODO 调试的时候，请修改此参数的值
//			chnid = chnid;		//TODO 调试的时候，请修改此参数的值
            op.ptz(puid, chnid, 0); //向左
            Thread.sleep(2000);        //等2秒后停止
            op.ptz(puid, chnid, 8); //停止
        } catch (Exception e) {
            log.error("ptz控制失败", e);
        }
    }
}
