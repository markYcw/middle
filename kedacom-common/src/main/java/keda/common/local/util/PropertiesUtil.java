package keda.common.local.util;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;


/***
 *
 * 多语言操作工具类
 * @author yrj
 *
 */
public class PropertiesUtil {
    /**
     * 日志工具类
     **/
    private static Logger log = Logger.getLogger(PropertiesUtil.class);
    /// 配置文件资源
    private Properties settingpros;
    /// 配置多语言文件名称
    private String settingpropertiesName = "";
    //// 默认语言类型
    public static final String DEFAULTLANGUAGE = "zh_CN";
    //中文标识
    public static final String LANGUAGE_CN = DEFAULTLANGUAGE;
    //英文标识
    public static final String LANGUAGE_EN = "en_US";
    /// 配置支持语言类型
    private String[] setttinglanguages = null;
    /*	/// 配置传递参数分割符前缀
        private static String parameter_prefix = "";
        /// 配置传递参数分割符后缀
        private static String parameter_suffix = "";*/
    /// 配置文件与语言分割符
    private String separator = "_";
    /// 多语言支持集合
    private Map<String, Properties> props = new HashMap<String, Properties>();
    private String local = null;
    /***引用类**/
	/*private Class<?> classroot = PropertiesUtil.class;
	
	public void setCalssroot(Class<?> classroot) {
		this.classroot = classroot;
	}*/
    private volatile static PropertiesUtil put;
    private static Object instanceLock = new Object();

    /**
     * 调用类对象
     *
     * @param cls
     * @return
     */
    public static PropertiesUtil getPropertiesUtil(Class<?> cls) {
        /// cls暂时保留
        return getInstance();
    }

    public static PropertiesUtil getPropertiesUtil() {
        return getPropertiesUtil(null);
    }

    private PropertiesUtil() {
    }

    ;

    private static PropertiesUtil getInstance() {
        if (put == null) {
            synchronized (instanceLock) {
                if (put == null) {
                    PropertiesUtil p = new PropertiesUtil();
                    p.init();

                    put = p;
                }
            }
        }
        return put;
    }

    private void init() {
        /// 多语言配置文件路径
        String setting = "localsetting.properties";
        log.info("多语言配置文件：" + setting);
        URL url = SettingLoader.getResource(setting);
        if (url == null) {
            log.error("多语言配置文件 localsetting.properties 未配置");
        }

        /// 取得配置文件流
        InputStream settingins = null;
        try {
            settingins = url.openStream();
        } catch (FileNotFoundException e1) {
            log.error("加载多语言配置文件:" + e1);
        } catch (IOException e) {
            log.error("加载多语言配置文件:" + e);
        }
        //log.info("多语言配置文件流：" + settingins);
        if (settingins == null) {
            log.error("多语言配置文件 localsetting.properties 未配置");
        }
        /// 初始化配置文件
        settingpros = new Properties();
        try {
            /// 加载配置文件
            settingpros.load(settingins);
            /// 读取多语言配置文件名称
            settingpropertiesName = settingpros.getProperty("local.filename");
            //log.info("多语言文件：" + settingpropertiesName);
            if (settingpropertiesName == null || settingpropertiesName.length() < 1) {
                try {
                    throw new Exception("检测语言包文件设置");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /// 读取多语言配置文件名称
            String languages = settingpros.getProperty("local.languages");
            log.info("支持语言：" + languages);
            if (languages == null || languages.length() < 2) {
                log.info("使用默认支持语言：" + DEFAULTLANGUAGE);
            }
            if (languages != null && languages.length() >= 2) {
                setttinglanguages = languages.split(",");
            }
            /// 加载多语言配置文件
            Properties prop = new Properties();
            /// 加载默认语言包
            String defaultpro = settingpropertiesName + ".properties";
            /// 取得配置文件流
            url = SettingLoader.getResource(defaultpro);
            InputStream ins = null;
            if (url == null) {
                log.info(defaultpro + "资源文件不存在");
            } else {
                ins = url.openStream();
            }
            try {
                /// 加载配置文件
                prop.load(ins);
                props.put(DEFAULTLANGUAGE, prop);
            } catch (Exception ex) {
                log.error(defaultpro + "资源文件不存在");
            }
            if (setttinglanguages != null) {
                for (String lang : setttinglanguages) {
                    /// 加载多语言配置文件
                    prop = new Properties();
                    /// 语言文件名称
                    String propname = settingpropertiesName + separator + lang + ".properties";
                    //log.info("加载资源文件名称：" +propname);
                    if (DEFAULTLANGUAGE.equals(lang)) {
                        continue;
                    }
                    /// 取得配置文件流
                    url = SettingLoader.getResource(propname);
                    ins = null;
                    if (url == null) {
                        log.info(propname + "资源文件不存在");
                    } else {
                        ins = url.openStream();
                    }
                    try {
                        /// 加载配置文件
                        prop.load(ins);
                        props.put(lang, prop);
                    } catch (Exception ex) {
                        log.info(propname + "资源文件不存在");
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 返回资源值
     *
     * @param key 资源键值
     * @return
     * @throws Exception
     */
    public String getMessage(String key) {
        if (local == null) {
            log.info("未设置Local 使用默认语言" + DEFAULTLANGUAGE);
            local = DEFAULTLANGUAGE;
        }
        //log.info("local:" + local +", key:"+ key);
        Properties prop = props.get(local);
        if (prop == null) {
            log.error(local + " 语言包不存在不存在，使用默认语言包" + DEFAULTLANGUAGE);
            local = DEFAULTLANGUAGE;
            prop = props.get(local);
        }
        String value = prop.getProperty(key);
        if (value == null || value.length() < 1) {
            //log.error(local +"包中"+key + "无对应值，请添加");
            return key;
        }
        return value;
    }

    /**
     * 返回资源值
     *
     * @param key      资源键值
     * @param paramers 键值参数
     * @return
     * @throws Exception
     */
    public synchronized String getMessage(String key, String paramers[]) {
        if (local == null) {
            log.info("未设置Local 使用默认语言" + DEFAULTLANGUAGE);
            local = DEFAULTLANGUAGE;
        }
        //log.info("local:" + local +", key:"+ key);
        Properties prop = props.get(local);
        if (prop == null) {
            log.error(local + " 语言包不存在不存在，使用默认语言包" + DEFAULTLANGUAGE);
            local = DEFAULTLANGUAGE;
            prop = props.get(local);
        }
        String value = prop.getProperty(key);
        if (value == null || value.length() < 1) {
            //log.error(local +"包中"+ key + "无对应值，请添加");
            return key;
        }
        value = MessageFormat.format(value, (Object[]) paramers);
        return value;
    }

    /**
     * 返回资源值
     *
     * @param key   资源键值
     * @param local 资源语言
     * @return
     * @throws Exception
     */
    public String getMessage(String key, String local) {
        //log.info("key:"+key + ", local:"+local);
        Properties prop = props.get(local);
        if (prop == null) {
            log.info(local + " 语言包不存在， 使用默认语言" + DEFAULTLANGUAGE + "包。");
            local = DEFAULTLANGUAGE;
            prop = props.get(local);
        }
        String value = prop.getProperty(key);
        if (value == null || value.length() < 1) {
            //log.error(local +"语言包中" + key + "无对应值，请添加");
            return key;
        }
        return value;
    }

    /**
     * 返回资源值
     *
     * @param key      资源键值
     * @param paramers 键值参数
     * @param local    资源语言
     * @return
     * @throws Exception
     */
    public synchronized String getMessage(String key, String paramers[], String local) {
        //log.info("key:"+key + ", local:"+local);
        //String local = System.getProperty(key);
        Properties prop = props.get(local);
        if (prop == null) {
            //log.error(local +" 语言包不存在，请添加");
            local = DEFAULTLANGUAGE;
            prop = props.get(local);
        }
        String value = prop.getProperty(key);
        if (value == null || value.length() < 1) {
            //log.error(key + "无对应值");
            return key;
        }
        value = MessageFormat.format(value, (Object[]) paramers);

        return value;
    }

    /**
     * 设置语言国家
     *
     * @param local
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * 获取本地语言国家
     *
     * @param args
     */
    public String getLocal() {
        if (local != null) {
            return local;
        } else {
            //获得系统默认的国家/语言环境
            Locale myLocale = Locale.getDefault();
            return myLocale.getLanguage() + "_" + myLocale.getCountry();
        }
    }

    public static void main(String[] args) {
		
		/*try {
			long time1= System.currentTimeMillis();
			//for(int i=0; i < 100000; i ++){
				String value="";
				value = PropertiesUtil.getMessage("login.js.tip.unlaodusbkeysucess");
				System.out.println(value);
				value = PropertiesUtil.getMessage("login.js.tip.unlaodusbkeysucess" , "en_US");
				System.out.println(value);
				value = PropertiesUtil.getMessage("login.java.test", new String[]{"A","B","C"});
				System.out.println(value);
				value = PropertiesUtil.getMessage("login.java.test", new String[]{"A","B","C"}, "en_US");
				System.out.println(value);
			//}
			long time2= System.currentTimeMillis();
			System.out.println("toast time is " + (time2-time1));
		} catch (Exception e) {
			e.printStackTrace();
		}*/

    }
}
