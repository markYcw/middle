package keda.common.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;


/**
 * 提供properties文件的存取服务。
 * <pre>
 * 特色功能点：
 *    支持加载指定的文件；
 *    支持文件变更的监听；
 *    支持读取多种类型的属性；
 *    支持文件转存到指定目录。
 * </pre>
 * <pre>
 * 	基本构造（三步）：
 *     （1） 构造对象,  PropertiesFile file = new PropertiesFile();
 *     （2） 设置应用程序外部目录 file.setAppDataPath("/root/user/project/")
 *     （3） 加载配置文件 加载配置文件 file.loadFile("config.properties")
 * </pre>
 * @author TaoPeng
 *
 */
public class PropertiesFile {
	
	private static Logger log = Logger.getLogger(PropertiesFile.class);
	
	/**
	 * 应用程序根目录， 配置文件将被保存到此目录下
	 * @see #store()
	 */
	private File appDataPath;
	
	/**
	 * 用户修改过的配置文件名称
	 */
	private String configFile;
	
	public PropertiesFile(){
	}

	/**
	 * 设置应用程序工作目录
	 * @param path
	 * @see #setUserPath(File)
	 */
	public void setAppDataPath(String path){
		this.setAppDataPath(new File(path));
	}
	/**
	 * 设置应用程序工作目录，此目录也是配置文件保存时的输出目录。
	 * @param path
	 */
	public void setAppDataPath(File path){
		this.appDataPath = path;
	}
	/**
	 * 加载指定的文件。加载配置文件时，先加载classpath下默认的配置文件，再加载应用程序工作目录下的自定义的配置文件
	 * @param path 文件目录
	 * @param fileName 配置文件名称，位于classpath的根目录(默认包),（开发时，位于src根目录）
	 * @throws IOException 
	 */
	public void load(String fileName) throws IOException{
		this.configFile = fileName;
		loadDefaultFile();
	}
	
	private void loadDefaultFile() throws IOException{
			/*
			 * 由于版本升级的问题，默认配置可能会新增加或发生改变，如果只加载自定义配置，则可能会丢失这些信息。
			 */
			{
				//加载默认配置
				InputStream is = getDefaultFile();
				if(is != null){
					Properties p0 = loadFile(is);
					loadProperties(p0);
					is.close();
				}
			}
			
			{
				//加载自定义配置
				InputStream is = getUserFile();
				if(is != null){
					Properties p2 = loadFile(is);//注意：要新建一个对象
					is.close();
					//用自定义属性覆盖默认属性
					loadProperties(p2);
				}
			}
			
	}

	/**
	 * 加载配置文件
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static Properties loadFile(InputStream in) throws IOException{
		Properties p = new Properties();
		p.load(in);
		return p;
	}
	
	/**
	 * 加载一组属性（重复调用此方法，具有相同名称的属性将被覆盖）
	 * @param properties
	 */
	public void loadProperties(Properties properties){
		for(Object key : properties.keySet()){
			this.properties.put(key, properties.get(key));
		}
	}
	
	/**
	 * 清除所有属性
	 */
	public void clear(){
		properties.clear();
	}
	
	/**
	 * 获取默认配置文件的输入
	 * @return
	 */
	private InputStream getDefaultFile(){
		if(configFile != null){
			return PropertiesFile.class.getClassLoader().getResourceAsStream(configFile);
		}else{
			return null;
		}
	}

	/**
	 * 加载用户自定义的配置文件
	 * @return
	 * @throws FileNotFoundException
	 */
	private InputStream getUserFile() throws FileNotFoundException{
		File userfile = getuserFile0();
		if(userfile != null && userfile.exists()){
			return new FileInputStream(userfile);
		}
		return null;
	}
	
	/**
	 * 获取用户文件路径
	 * @return
	 */
	private File getuserFile0(){
		if(appDataPath != null){
			return new File(appDataPath, configFile);
		}else{
			log.warn("没有指定应用程序根目录");
			return null;
		}
	}
	
	public String getValue(String key){
		return getProperty(key);
	}
	public String getString(String key){
		return getValue(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return 返回对应的值。如果没有相应的值，则返回null
	 */
	public Boolean getBoolean(String key){
		String v = getValue(key);
		return v != null ? v.equalsIgnoreCase("true") : Boolean.FALSE;
	}
	
	public boolean getBool(String key){
		Boolean v = getBoolean(key);
		return v != null && v;
	}
	
	/**
	 * 
	 * @param key
	 * @return 返回对应的值。如果没有相应的值，则返回null
	 */
	public Integer getInt(String key){
		String v = getValue(key);
		return v != null ? Integer.parseInt(v) : null;
	}

	/**
	 * 
	 * @param key 返回对应的值。如果没有相应的值，则返回null
	 * @return
	 */
	public Long getLong(String key){
		String v = getValue(key);
		return v != null ? Long.parseLong(v) : null;
	}
	/**
	 * 标识文件是否有改变
	 */
	private boolean change = false;
	/**
	 * 返回配置属性是否有更改
	 * @return
	 */
	public boolean isChange(){
		return change;
	}
	public void setValue(String key, String value){
		String oldValue = getProperty(key);
		if(oldValue == null || !oldValue.equals(value)){
			changeProperty(key, value);
			change = true;
		}
		
	}

	private void changeProperty(String name, String value){

		properties.setProperty(name, value);
		
		//监听者模式
		for(PropertiesFileListener l : listeners){
			try{
				l.onChange(name, value);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void setValue(String key, Object value){
		this.setValue(key, value != null ? value.toString() : "");
	}
	/**
	 * 将配置保存到文件系统，保存目录由{@link #setAppDataPath(File)}指定。如果配置属性没有更改，则无任何影响
	 * @see #setAppDataPath(File)
	 * @see #setAppDataPath(String)
	 * @throws IOException
	 */
	public void store() throws IOException{
		store(false);
	}
	
	/**
	 * 将配置保存到文件系统。
	 * @param force 指示是否强制写入文件系统。true，不管是否有变更，都写入文件系统；false，仅当配置属性有变更时，才写入文件系统
	 * @see #store()
	 * @throws IOException
	 */
	public void store(boolean force) throws IOException{
		if(change || force){
			File file = getuserFile0();
			storeTo(file);
		}
	}
	
	/**
	 * 另存为指定的文件
	 * @param file
	 * @throws IOException 
	 */
	public void store(File file) throws IOException{
		storeTo(file);
	}

	private void storeTo(File file) throws IOException{
		if(!file.exists()){
			File p = file.getParentFile();
			if(p != null && !p.exists()){
				p.mkdirs();
			}
			file.createNewFile();
		}
		
		store(this.properties, file);

		change = false;
	}
	
	
	private static void store(Properties properties, File file) throws IOException{
		OutputStream out = null;
		out = new FileOutputStream(file);
		properties.store(out, null);
		out.close();
	}
	
	/**
	 * 配置属性
	 */
	private Properties properties = new Properties();

	private List<PropertiesFileListener> listeners = new ArrayList<PropertiesFileListener>(10);


	/**
	 * 获取指定属性的值
	 * @param key
	 * @return
	 */
	private String getProperty(String key){
		return properties.getProperty(key);
	}
	
	/**
	 * 获取属性集合（副本）
	 * @return
	 */
	public Properties getProperties(){
		Properties p = new Properties();
		for(Object k : properties.keySet()){
			Object v = properties.get(k);
			p.setProperty(k.toString(), v.toString());
		}
		return p;
	}
	
	/**
	 * 返回属性文件中key的集合
	 * @return
	 */
	public Set<Object> keySet(){
		return properties.keySet();
	}
	
//	public List<String> keyList(){
//		
//		Set<Object> keys = keySet();
//		ArrayList<String> list = new ArrayList<String>(keys.size());
//		for(Object k : keys){
//			list.add(k.toString());
//		}
//		
//		Collections.sort(list, new Comparator<String>(){
//
//			@Override
//			public int compare(String o1, String o2) {
//				if(o1 == null || o2 == null){
//					return o1 == null ? 1 : -1;
//				}	
//				return o1.compareToIgnoreCase(o2);
//			}
//			
//		});
//		
//		return list;
//	}
	
	public void addListener(PropertiesFileListener listener){
		listeners.add(listener);
	}
	
	public boolean removeListener(PropertiesFileListener listener){
		return listeners.remove(listener);
	}
	
}
