package com.ycf.fun.db;

import java.util.List;

public class DBImpl extends DBHelp {

	public DBImpl(String dRIVER, String uRL, String uSER, String pWD, int poolSize) {
		super(dRIVER, uRL, uSER, pWD, poolSize);
		// TODO Auto-generated constructor stub
	}

	public boolean save(Object obj) {
		DBExecuteInfo info = GetDBExecuteInfo.getDBExecuteInfo(obj, "save");
		return dml(info.getSql(), info.getList());
	}

	public boolean delete(Object obj) {
		DBExecuteInfo info = GetDBExecuteInfo.getDBExecuteInfo(obj, "delete");
		return dml(info.getSql(), info.getList());
	}

	public boolean update(Object obj) {
		DBExecuteInfo info = GetDBExecuteInfo.getDBExecuteInfo(obj, "update");
		return dml(info.getSql(), info.getList());
	}

	public <T> List<T> find(String sql, Class<?> clazz) {
		return query(sql, clazz);
	}

}
