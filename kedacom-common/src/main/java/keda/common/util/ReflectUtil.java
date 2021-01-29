package keda.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 提供以下功能: 通过反射方式构造JavaBean的实例,并通过key-value集合为对象的属性赋值。
 * @author TaoPeng
 * @已知使用工程 级联通信模块
 *
 */
public class ReflectUtil {
	
	/**
	 * <pre>
	 * 指示如果找不到相关属性，应该怎么处理：
	 * 如果checkNoFiled等于true, 在找不到相关属性时，会抛出NoSuchFieldException；
	 * 如果checkNoFiled等于false, 在找不到相关属性时，不作处理，直接忽略。
	 * </pre>
	 */
	private static boolean checkNoFiled = false;
	
	public static boolean isCheckNoFiled() {
		return checkNoFiled;
	}

	/**
	 * 
	 * @param checkNoFiled  指示如果找不到相关属性，应该怎么处理：
	 * <pre>
	 * 如果checkNoFiled等于true, 在找不到相关属性时，会抛出NoSuchFieldException；
	 * 如果checkNoFiled等于true, 在找不到相关属性时，不作处理，直接忽略。
	 * </pre>
	 */
	public static void setCheckNoFiled(boolean checkNoFiled) {
		ReflectUtil.checkNoFiled = checkNoFiled;
	}

	/**
	 * 
	 * @param className
	 * @param args
	 * @see #getObject(Class, Map)
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(String className, Map<String,String> args) throws Exception{
		
		Class<?> cls = Class.forName(className);
		return getObject(cls, args);

	}
	
	/**
	 * <pre>
	 * 根据cls指示的类和args指示的属性及其属性值，构造一个className的对象。
	 * 需要注意的是：
	 * 	类cls必须是一个符合JavaBean规范的类；
	 * 	如果cls里面的属性也是一个对象（不包括简单对象，如String, Integer 、int 等...），这个对象也必须符合JavaBean规范；
	 * 
	 * cls里面的对象属性可以用以下方式描述:
	 * 		class Department{
	 * 			private int id;
	 * 			//setter/getter methods
	 * 		}
	 * 		class User{
	 * 			private Department department;
	 * 			//setter/getter methods
	 * 		}
	 * <pre>
	 * 	为用户指定部门id时，属性名为department.id,值为1(注意：这个属性名和值以“键－值”对存放在args参数列表中)
	 * </pre>
	 * @param cls
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(Class<?> cls, Map<String,String> args) throws Exception{
		try{
			Object object = cls.newInstance();
			for (String property : args.keySet()) {
				String value = args.get(property);
				try{
					setProperty(object, property, value);
				}catch(Exception e){
					String msg = "无法为类[{0}]设置属性[{1}]，属性值=[{2}]";
					msg = MessageFormat.format(msg, cls.getName(), property, value);
					throw new Exception(msg, e);
				}
			}
			
			return object;
		}catch(Exception e){
			String msg = "无法解析对象[{0}]";
			msg = MessageFormat.format(msg, cls.getName());
			throw new Exception (msg, e);
		}
	}
	
	private static Object setProperty(Object object, String property,
			String value) throws SecurityException, NoSuchFieldException, NoSuchMethodException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException, ParseException {

		Class<?> cls = object.getClass();
		int i = property.indexOf(".");
		if (i > 0) {
			// 对象属性
			String p0 = property.substring(0, i);// 如user.department.id
			Class<?> type = getPropertyType(cls, p0);
			if(type != null){
				Object subObj = type.newInstance();
				String subProperty = property.substring(i + 1, property.length());
				subObj = setProperty(subObj, subProperty, value);
				
				{
					String name2 = getMethodName(p0, TYPE_SET);
					Method setter = cls.getMethod(name2, type);
					setter.invoke(object, subObj);
				}
			}
		} else {
			// 简单属性
			Class<?> type = getPropertyType(cls, property);

			if(type != null){
				String name2 = getMethodName(property, TYPE_SET);
				Method setter = cls.getMethod(name2, type);
	
				Object inParam = getSimpleObject(type, value);
				setter.invoke(object, inParam);
			}
		}

		return object;
	}

	/**
	 * 返回objectCls所示类中的属性property的类型
	 * 
	 * @param objectCls
	 *            指定对象的类型
	 * @param property
	 *            指定的属性
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException 
	 */
	private static Class<?> getPropertyType(Class<?> objectCls, String property)
			throws SecurityException, NoSuchFieldException {
		
		Field f = null;
		Class<?> cls = objectCls;
		
		while(cls != null){
			//遍历objectCls及其所有父类
			try {
				f = objectCls.getDeclaredField(property);
			} catch (NoSuchFieldException e) {
			}
			
			if( f != null){
				break;
			}else{
				cls = cls.getSuperclass();
			}
		}
		
		if(f == null){
			if (checkNoFiled){//if条件写成两行用于调试
				String msg = "在类[{0}]中没有找到属性[{1}]";
				msg = MessageFormat.format(msg, objectCls, property);
				throw new NoSuchFieldException(msg);
			}
		}
		
		return f != null ? f.getType() : null;
		
//		String name = getMethodName(property, TYPE_GET);
//		Method getter;
//		try {
//			getter = objectCls.getMethod(name);
//		} catch (NoSuchMethodException e) {
//			name = getMethodName(property, TYPE_IS);
//			try {
//				getter = objectCls.getMethod(name);
//			} catch (NoSuchMethodException e1) {
//				String msg = "在类[{0}]中没有找到属性[{1}]的getXXX或isXXX方法";
//				msg = MessageFormat.format(msg, objectCls, property);
//				throw new NoSuchMethodException(msg);
//			}
//		}
//		if (getter == null) {
//			throw new NoSuchMethodError("没有方法:" + name);
//		}
//
//		return getter.getReturnType();

	}
	
	/**
	 * 获取JavaBean的指定属性setter方法
	 * @param beanType JavaBean的类型
	 * @param property 属性名称
	 * @param propertyTypes 属性类型
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method getSetMethod(Class<?> beanType, String property, Class<?> propertyTypes) throws SecurityException, NoSuchMethodException{
		String name = getMethodName(property, TYPE_SET);
		return beanType.getMethod(name, propertyTypes);
	}
	
	/**
	 * 获取JavaBean的指定属性getter方法
	 * @param beanType JavaBean的类型
	 * @param property 属性名称
	 * @param propertyTypes 属性类型
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method getGetMethod(Class<?> beanType, String property, Class<?> propertyTypes) throws SecurityException, NoSuchMethodException{
		
		String name = null;
		if(boolean.class.isAssignableFrom(propertyTypes) || Boolean.class.isAssignableFrom(propertyTypes)){
			//布偶型的值
			name = getMethodName(property, TYPE_IS);
		}else{
			name = getMethodName(property, TYPE_GET);
		}
		
		return beanType.getMethod(name);
	}
	
	private static final int TYPE_GET = 1;
	private static final int TYPE_SET = 2;
	private static final int TYPE_IS = 3;
	/**
	 * 返回JavaBean中指定属性的getter/setter方法名
	 * 
	 * @param property
	 * @param get
	 *            标识是否是get方法,true:getter方法; false:setter方法
	 * @return
	 */
	private static String getMethodName(String property, int type) {
		String p = convertProperty(property);
		StringBuffer sb = new StringBuffer(3 + property.length());
		sb.append(type == TYPE_GET ? "get" : (type == TYPE_IS ? "is" : "set"));
		sb.append(p);
		return sb.toString();
	}

	/**
	 * 将属性转换为对应getter/setter方法的名称后缀
	 * @param property
	 * @return
	 */
	private static String convertProperty(String property) {
		char[] cs = property.toCharArray();
		char cs0 = cs[0];

		cs0 = Character.toUpperCase(cs0);
		
		if(cs.length >= 2){
			if(Character.isLowerCase(cs[0]) && Character.isUpperCase(cs[1])){
				//处理 tA***这种以小写字母+大写字母为前缀的特殊属性
				cs0 = cs[0];
			}
		}
		
		cs[0] = cs0;
		return new String(cs);
	}

	private static Object getSimpleObject(Class<?> argClass, String value)
			throws InstantiationException, IllegalAccessException,
			SecurityException, NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException, ParseException {
		String argType = argClass.getName();
		if ("long".equals(argType) || "java.lang.Long".equals(argType)) {
			return java.lang.Long.parseLong(value);
		} else if ("int".equals(argType) || "java.lang.Integer".equals(argType)) {
			return java.lang.Integer.parseInt(value);
		} else if ("short".equals(argType) || "java.lang.Short".equals(argType)) {
			return java.lang.Short.parseShort(value);
		} else if ("byte".equals(argType) || "java.lang.Byte".equals(argType)) {
			return java.lang.Byte.parseByte(value);
		} else if ("float".equals(argType)|| "java.lang.Float".equals(argType)) {
			return java.lang.Float.parseFloat(value);
		} else if ("double".equals(argType)|| "java.lang.Double".equals(argType)) {
			return java.lang.Double.parseDouble(value);
		} else if ("char".equals(argType)|| "java.lang.Character".equals(argType)) {
			return new java.lang.Character((char) Integer.parseInt(value));
		} else if ("java.lang.String".equals(argType)) {
			return value;
		} else if ("boolean".equals(argType) || "java.lang.Boolean".equals(argType)){
			return new java.lang.Boolean(value);
			
		} else if ("java.sql.Timestamp".equals(argType)) {
			java.util.Date d = tryParserDateString(value);
			return new java.sql.Timestamp(d.getTime());
			
		} else if ("java.util.Date".equals(argType)) {
			return tryParserDateString(value);
			
		} else if ("java.sql.Date".equals(argType)) {
			java.util.Date d = tryParserDateString(value);
			return new java.sql.Date(d.getTime());
			
		}else {
			String msg = "无法为对象[{0}]设置值[{1}]";
			msg = MessageFormat.format(msg, argType , value);
			throw new InstantiationException(msg);
		}
	}
	
	private static ArrayList<String> datePtterns = new ArrayList<String>();
	static{
		datePtterns.add("yyyy-MM-dd HH:mm:ss.SSS");
		datePtterns.add("yyyy-MM-dd HH:mm:ss");
		datePtterns.add("yyyy-MM-dd HH:mm");
		datePtterns.add("yyyy-MM-dd HH");
		datePtterns.add("yyyy-MM-dd");
		datePtterns.add("yyyy-MM");

		datePtterns.add("MM-dd HH:mm:ss.SSS");
		datePtterns.add("MM-dd HH:mm:ss");
		datePtterns.add("MM-dd HH:mm");
		datePtterns.add("MM-dd HH:mm");
		datePtterns.add("MM-dd HH");
		datePtterns.add("MM-dd");

		datePtterns.add("HH:mm:ss.SSS");
		datePtterns.add("HH:mm:ss");
		datePtterns.add("HH:mm");
	}
	private static java.util.Date tryParserDateString(String dateString) throws ParseException{
		
		ParseException pe = null;
		for(String pattern : datePtterns){
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				return sdf.parse(dateString);
			} catch (ParseException e) {
				pe = e;
			}
		}
		
		throw pe;
	}
	/*=======================================================*/
	/*                 以下为测试代码                          */
	/*=======================================================*/
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Date d = tryParserDateString("2012-11-1 12:12:12.022");
			d = tryParserDateString("2012-11-1 12:12");
			d = tryParserDateString("2012-11-1");
			d = tryParserDateString("12:12");
			d = tryParserDateString("2012-11");
			d = tryParserDateString("8-12 11:11");
			System.out.println(d);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			long t = c.get(Calendar.MILLISECOND);
			System.out.println(t);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		B b = new B();
		try {
			setProperty(b, "a.id", "4");
			setProperty(b, "i", "5");
			setProperty(b, "str", "striiggggg");
			setProperty(b, "aAaaa.id", "7");
			System.out.println(b.getA().getId());
			System.out.println(b.getI());
			System.out.println(b.getStr());
			System.out.println(b.getaAaaa().getId());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}

class A {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

class B {
	private A a;
	private A aAaaa;
	private A aaAAA;
//	private A Aaaaa;
	private A AAaaa;
	private A aaaaa;
	private A AAAAA;
	
	public A getaAaaa() {
		return aAaaa;
	}

	public void setaAaaa(A aAaaa) {
		this.aAaaa = aAaaa;
	}

	public A getAaAAA() {
		return aaAAA;
	}

	public void setAaAAA(A aaAAA) {
		this.aaAAA = aaAAA;
	}

	public A getAAaaa() {
		return AAaaa;
	}

	public void setAAaaa(A aAaaa) {
		AAaaa = aAaaa;
	}

	public A getAaaaa() {
		return aaaaa;
	}

	public void setAaaaa(A aaaaa) {
		this.aaaaa = aaaaa;
	}

	public A getAAAAA() {
		return AAAAA;
	}

	public void setAAAAA(A aAAAA) {
		AAAAA = aAAAA;
	}

	private int i;
	private String str;
	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
}