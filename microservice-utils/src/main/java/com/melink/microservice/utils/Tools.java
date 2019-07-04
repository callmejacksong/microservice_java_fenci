package com.melink.microservice.utils;

import org.apache.commons.collections.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	public static boolean isEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean isMobile(String mobile) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobile);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
	public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null && !"".equals(result)) {
                    returnMap.put(propertyName, URLEncoder.encode((String) result));
                }
            }
        }
        return returnMap;
    }
	
	public static List subList(List list, String size, String page) {
		int pageInt,sizeInt;
		if(page.matches("\\d+")){
			pageInt = Integer.valueOf(page);
		}else{
			pageInt = 1;
		}
		if (size.matches("\\d+")) {
			sizeInt = Integer.valueOf(size);
		} else {
			sizeInt = 20;
		}
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList();
		}
		pageInt--;
		int startIndex = sizeInt * pageInt;
		int endIndex = sizeInt * pageInt + sizeInt;
		if (endIndex < startIndex || startIndex >= list.size()) {
			return new ArrayList();
		}
		if (endIndex > list.size()) {
			endIndex = list.size();
		}
		return list.subList(startIndex, endIndex);
	}

	public static List subList(List list, Integer size, Integer page) {
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList();
		}
		page--;//TODO 加入
		int startIndex = size * page;
		int endIndex = size * page + size;
		if (endIndex < startIndex || startIndex >= list.size()) {
			return new ArrayList();
		}
		if (endIndex > list.size()) {
			endIndex = list.size();
		}
		return list.subList(startIndex, endIndex);
	}

	public static List subListNoPage(List list, Integer startIndex, Integer endIndex) {
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList();
		}
		if (endIndex < startIndex || startIndex >= list.size()) {
			return new ArrayList();
		}
		if (endIndex > list.size()) {
			endIndex = list.size();
		}
		return new ArrayList(list.subList(startIndex, endIndex));
	}

	public static String strArrayToStr(String... fields){
		String str = "";
		if(fields == null || fields.length < 1){
			return str;
		}
		for (String field : fields) {
			str += field + ",";
		}
		str = str.substring(0,str.length() - 1);
		return str;
	}

	public static int strIntCompare(String str1,String str2){
		try {
			Integer number1 = Integer.parseInt(str1);
			Integer number2 = Integer.parseInt(str2);
			return number1.compareTo(number2);
		}catch (Exception e){
			return 1;
		}
	}


}

