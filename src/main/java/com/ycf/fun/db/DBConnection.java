package com.ycf.fun.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DBConnection extends DBConnectionPool {

	public DBConnection(String dRIVER, String uRL, String uSER, String pWD, int poolSize) {
		super(dRIVER, uRL, uSER, pWD, poolSize);
		// TODO Auto-generated constructor stub
	}

	protected boolean dml(String sql, @SuppressWarnings("rawtypes") List list) {
		
		Connection conn = getConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int size = list.size();
			for (int i = 0; i < size; i++) {
				pstmt.setObject(i + 1, list.get(i));
			}
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			returnConn(conn);
		}

	}

	protected ResultSet getResultSet(String sql) {
		Connection conn = getConn();
		try {
			return conn.prepareStatement(sql).executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			returnConn(conn);
		}

	}
}
