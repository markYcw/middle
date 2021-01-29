package keda.common.httpclient;

import keda.common.util.TimeUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

public class ParamUtil {
	/**
	 * 将bean转化成http参数
	 * @param bean 
	 * @param prefix 各参数的前缀
	 * @return 返回如：u.name=root&u.pwd=123456&u.desc=系统管理员
	 */
	public static String toHttpParam(Object bean, String prefix){
		return toHttpParam(bean, prefix, null);
	}
	/**
	 * 将bean转化成http参数
	 * @param bean 
	 * @param prefix 各参数的前缀
	 * @container bean 所在的类，如果没有，可为null
	 * @return 返回如：u.name=root&u.pwd=123456&u.desc=系统管理员
	 */
	public static String toHttpParam(Object bean, String prefix, Object container){
		if(bean == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Class bClass = bean.getClass();
		boolean includeSuperClass = bClass.getClassLoader() != null;
		//获取所有方法
		Method[] methods = (includeSuperClass) ? bClass.getMethods() : bClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i += 1) {
			try{
				Method method = methods[i];
				//是否是public方法
				if(Modifier.isPublic(method.getModifiers())){
					//获取该方法的结果
					Object result = method.invoke(bean, (Object[]) null);
					//如果为null或为空，则下一个
					if(result == null || "".equals(result))
						continue;
					/*
                	 * 如果属性的值指向当前对象的容器，则不再进行递归解析。
                	 * 此方案用于解决，对象循环引用导致解析时栈溢出的问题。
                	 * 		例如：
                	 * 		以下代码，解析时，会导致栈溢出，
                	 * 		因为会无限解析对象a,解析路径(一个循环)：
        * 		a->a.getB()->a.getB().getA()->a.getB().getA()
								B b = new B();
								A a = new A();
								b.setName("b");
								b.setA(a);
								a.setName("a");
								a.setB(b);
								JSONObject json = new JSONObject(a);
								public class A{
									private String name;
									private B b;
									// getter/setter
								}
								public class B{
									private String name;
									private A a;
									// getter/setter
								}
                	 */
					if(result == container){
						continue;
					}
					//获取方法名称
					String name = method.getName();
					//
					String key = "";
					//需要以get开始的方法
					if(name.startsWith("get")){
						//但不能是Object中的getClass方法
						if(!name.equals("getClass")){
							key = name.substring(3);
						}
					}else if(name.startsWith("is")){
						key = name.substring(2);
					}
					if (key.length() == 1) {
						key = key.toLowerCase();
					} else if (!Character.isUpperCase(key.charAt(1))) {
						key = key.substring(0, 1).toLowerCase() + key.substring(1);
					}
					//如果前缀不为空
					if(prefix != null && !"".equals(prefix)){
						key = prefix + "." + key; 
					}
					
					//如果属于基本类型
					if(result instanceof Byte || result instanceof Character
					|| result instanceof Short || result instanceof Integer
					|| result instanceof Long || result instanceof Boolean
					|| result instanceof Float || result instanceof Double
					|| result instanceof String){
						sb.append(key);
						sb.append("=");
						sb.append(result.toString());
						sb.append('&');
					}else if(result instanceof java.util.Date){
						String timeStr = TimeUtil.formatTime((java.util.Date)result, TimeUtil.TIME_SQL_PATTERN);
						if(timeStr != null){
							sb.append(key);
							sb.append("=");
							sb.append(timeStr);
							sb.append('&');
						}
					}else if(result instanceof Collection || result instanceof Map || result.getClass().isArray()){
						//如果是集合或数组现在不处理，如果后面有需要再处理
					} else {
						sb.append(toHttpParam(result, key, bean));
						sb.append('&');
					}
				}
			}catch (Exception ignore) {
			}
		}
		String result = sb.toString();
		if(result.endsWith("&")){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
}
