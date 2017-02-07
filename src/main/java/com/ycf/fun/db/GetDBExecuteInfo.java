package com.ycf.fun.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.ycf.fun.contain.Bean;
import com.ycf.fun.contain.Mapper;
import com.ycf.fun.util.Util;

public class GetDBExecuteInfo {
	public static DBExecuteInfo getDBExecuteInfo(Object obj, String type) {
		if ("save".equals(type))
			return getSaveDBExecuteInfo(obj);
		if ("delete".equals(type))
			return getDeleteDBExecuteInfo(obj);
		if ("update".equals(type))
			return getUpdateDBExecuteInfo(obj);
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static DBExecuteInfo getSaveDBExecuteInfo(Object obj) {
		Class<?> clazz = obj.getClass();
		String insert = "insert into TBNAME(COLUMNS) values(PLACEHOLDERS)";
		String[] dbinfo = getDBInfo(clazz);
		String tbname = dbinfo[0];
		String pkey = dbinfo[1];
		String COLUMNS = "";
		String PLACEHOLDERS = "";
		List list = new ArrayList();
		Field[] fields = getFields(clazz);
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.equals(pkey))
				continue;
			COLUMNS += fieldName + ",";
			PLACEHOLDERS += "?,";
			try {
				list.add(Util.getMethod(obj.getClass()).invoke(obj, fieldName));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		COLUMNS = COLUMNS.substring(0, COLUMNS.length() - 1);
		PLACEHOLDERS = PLACEHOLDERS.substring(0, PLACEHOLDERS.length() - 1);
		insert = insert.replace("TBNAME", tbname).replace("COLUMNS", COLUMNS).replace("PLACEHOLDERS", PLACEHOLDERS);
		return new DBExecuteInfo(insert, list);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static DBExecuteInfo getDeleteDBExecuteInfo(Object obj) {
		Class<?> clazz = obj.getClass();
		String delete = "delete from TBNAME where PKEY=?";
		String[] dbinfo = getDBInfo(clazz);
		String tbname = dbinfo[0];
		String pkey = dbinfo[1];
		List list = new ArrayList();
		try {
			list.add(Util.getMethod(obj.getClass()).invoke(obj, pkey));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delete = delete.replace("TBNAME", tbname).replace("PKEY", pkey);
		return new DBExecuteInfo(delete, list);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static DBExecuteInfo getUpdateDBExecuteInfo(Object obj) {
		Class<?> clazz = obj.getClass();
		String update = "update TBNAME set KEY_VALUES where PKEY=?";
		String[] dbinfo = getDBInfo(clazz);
		String tbname = dbinfo[0];
		String pkey = dbinfo[1];
		String KEY_VALUES = "";

		List list = new ArrayList();
		Field[] fields = getFields(clazz);
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.equals(pkey))
				continue;
			KEY_VALUES += fieldName + "=?,";
			try {
				list.add(Util.getMethod(obj.getClass()).invoke(obj, fieldName));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			list.add(Util.getMethod(obj.getClass()).invoke(obj, pkey));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		KEY_VALUES = KEY_VALUES.substring(0, KEY_VALUES.length() - 1);
		update = update.replace("TBNAME", tbname).replace("PKEY", pkey).replace("KEY_VALUES", KEY_VALUES);
		return new DBExecuteInfo(update, list);

	}

	private static String[] getDBInfo(Class<?> clazz) {
		return ((Mapper) Bean.getInstance().get("mapper")).get(clazz);
	}

	private static Field[] getFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Field.setAccessible(fields, true);
		return fields;
	}
}
