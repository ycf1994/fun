package com.ycf.fun.db;

import java.util.List;

public class DBExecuteInfo {
	private String sql;
	@SuppressWarnings("rawtypes")
	private List list;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}
	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}
	@SuppressWarnings("rawtypes")
	public DBExecuteInfo(String sql, List list) {
		super();
		this.sql = sql;
		this.list = list;
	}
	public DBExecuteInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
