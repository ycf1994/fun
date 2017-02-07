package com.ycf.fun.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class DBConnectionPool {
	private static Logger log=Logger.getLogger(DBConnectionPool.class);
	private final String DRIVER;
	private final String URL;
	private final String USER;
	private final String PWD;
	private final int poolSize;
	private final List<Connection> pool = new LinkedList<Connection>();

	public DBConnectionPool(String dRIVER, String uRL, String uSER, String pWD, int poolSize) {
		super();
		DRIVER = dRIVER;
		URL = uRL;
		USER = uSER;
		PWD = pWD;
		this.poolSize = poolSize;
		if(init()){
			log.info(new Date()+"_____________________连接池已初始化成功");
			
		}

	}

	protected Connection getConn() {
		if (pool.size() > 0) {
			return pool.remove(0);
		}
		return null;
	}

	protected void returnConn(Connection conn) {
		pool.add(conn);
	}

	private boolean init() {
		for (int i = 0; i < poolSize; i++) {
			pool.add(openConn());
		}
		return pool.size() == poolSize;
	}

	private Connection openConn() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PWD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
