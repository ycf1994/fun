package com.ycf.fun.util;

import java.lang.reflect.Method;

public abstract class Util {

	public static boolean isBaseType(Class<?> clazz) {
		return clazz == byte.class || clazz == Byte.class || clazz == short.class || clazz == Short.class
				|| clazz == int.class || clazz == Integer.class || clazz == long.class || clazz == Long.class
				|| clazz == float.class || clazz == Float.class || clazz == double.class || clazz == Double.class
				|| clazz == boolean.class || clazz == Boolean.class || clazz == char.class || clazz == Character.class
				|| clazz == String.class;
	}

	public static Method getMethod(Class<?> clazz) {
		try {
			return clazz.getMethod("get", String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Method setMethod(Class<?> clazz) {
		try {
			return clazz.getMethod("set", String.class, String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
