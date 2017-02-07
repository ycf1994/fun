package com.ycf.fun.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ycf.fun.util.Util;

public abstract class DBHelp extends DBConnection {

	public DBHelp(String dRIVER, String uRL, String uSER, String pWD, int poolSize) {
		super(dRIVER, uRL, uSER, pWD, poolSize);
		// TODO Auto-generated constructor stub
	}

	protected <T> List<T> query(String sql, Class<?> clazz) {

		if (Util.isBaseType(clazz))
			return baseTypeQuery(sql, clazz);
		else
			return refTypeQuery(sql, clazz);

	}

	@SuppressWarnings("unchecked")
	private <T> List<T> refTypeQuery(String sql, Class<?> clazz) {
		try {
			ResultSet rs = getResultSet(sql);
			List<T> list = new ArrayList<T>();
			ResultSetMetaData data = rs.getMetaData();
			int count = data.getColumnCount();
			Method set = Util.setMethod(clazz);
			T t = null;
			while (rs.next()) {
				t = (T) clazz.newInstance();
				for (int i = 1; i <= count; i++) {
					set.invoke(t, data.getColumnName(i),rs.getString(i));
				}
				list.add(t);
			}
			return list;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> baseTypeQuery(String sql, Class<?> clazz) {
		try {
			ResultSet rs = getResultSet(sql);
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				list.add((T) rs.getObject(1));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
