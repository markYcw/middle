package keda.common.errcode.util;

import keda.common.exception.IllegalParamException;
import keda.common.exception.KedacommonException;
import keda.common.exception.KeyRepeatException;
import keda.common.exception.LoacErrorCodeException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

public enum ErrcodeManager {
    INSTANCE;
    private Hashtable<String, Properties> datas = new Hashtable<String, Properties>();
    private final String UNKNOW_ERRCODE_DESCRIPTION = "未知错误";

    /**
     * 加载错误码文件
     *
     * @param key  关键字，必须是唯一，以区分不同应用领域的错误码
     * @param file 错误码文件不能为空
     * @throws IllegalParamException
     * @throws KeyRepeatException
     * @throws KeyRepeatException
     * @throws LoacErrorCodeException
     */
    public synchronized void loadErrCode(String key, String file) throws IllegalParamException, KeyRepeatException, LoacErrorCodeException {
        if (key == null || "".equals(key)) {
            throw new IllegalParamException("关键字不能为空");
        }
        if (datas.containsKey(key)) {
            throw new KeyRepeatException("key :" + key + "关键字不能重复");
        }
        if (file == null || "".equals(file)) {
            throw new IllegalParamException("错误码文件不能为空");
        }
        File codeFile = new File(file);
        if (!codeFile.exists()) {
            throw new LoacErrorCodeException("错误码文件未找到");
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(codeFile);
            Properties p = new Properties();
            p.load(is);
            datas.put(key, p);
        } catch (Exception ex) {
            throw new LoacErrorCodeException("加载错误码文件出错", ex);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
    }

    /**
     * 获取错误码对应的文字信息
     *
     * @param key      错误码领域
     * @param codeCode 错误码
     * @return 文字信息
     * @throws IllegalParamException 参数不合法异常
     */
    public String getErrCodeDes(String key, String codeCode) throws IllegalParamException {
        if (key == null || "".equals(key)) {
            throw new IllegalParamException("关键字不能为空");
        }
        if (codeCode == null || "".equals(codeCode)) {
            throw new IllegalParamException("错误码不能为空");
        }
        Properties p = datas.get(key);
        if (p != null) {
            String des = (String) p.get(codeCode);
            if (des != null)
                return des;
        }
        return UNKNOW_ERRCODE_DESCRIPTION;
    }

    public static void main(String[] args) throws IllegalParamException, KedacommonException {
        ErrcodeManager.INSTANCE.loadErrCode("KDV2000B", "/root/Desktop/cmserrorstr.properties");
        String str = ErrcodeManager.INSTANCE.getErrCodeDes("KDV2000B", "14001");
        System.out.println("str = " + str);
        str = ErrcodeManager.INSTANCE.getErrCodeDes("KDV2000B", "14001");
        System.out.println("str = " + str);
        str = ErrcodeManager.INSTANCE.getErrCodeDes("KDV2000B", "14030");
        System.out.println("str = " + str);
        str = ErrcodeManager.INSTANCE.getErrCodeDes("KDV2000B", "11014");
        System.out.println("str = " + str);
    }
}
