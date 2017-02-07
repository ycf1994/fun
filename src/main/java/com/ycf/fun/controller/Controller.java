package com.ycf.fun.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ycf.fun.db.DBImpl;

public abstract class Controller {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static DBImpl di;
	private static String prefix = "";
	private static String suffix = "";

	public static void setDi(DBImpl di) {
		Controller.di = di;
	}

	public static void setPrefix(String prefix) {
		Controller.prefix = prefix;
	}

	public static void setSuffix(String suffix) {
		Controller.suffix = suffix;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	protected void render(String url) {
		try {
			request.getRequestDispatcher(prefix + url + suffix).forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void redirect(String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setAttrToRequest(String name, Object value) {
		request.setAttribute(name, value);
	}

	protected void setAttrToSession(String name, Object value) {
		request.getSession().setAttribute(name, value);
	}

	protected void setAttrToApplication(String name, Object value) {
		request.getSession().getServletContext().setAttribute(name, value);
	}

	protected Object getAttrFromRequest(String name) {
		return request.getAttribute(name);
	}

	protected Object getAttrFromSession(String name) {
		return request.getSession().getAttribute(name);
	}

	protected Object getAttrFromApplication(String name) {
		return request.getSession().getServletContext().getAttribute(name);
	}

	protected void out(String out) {
		try {
			response.getWriter().print(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean save(Object obj) {
		return di.save(obj);
	}

	protected boolean delete(Object obj) {
		return di.delete(obj);
	}

	protected boolean update(Object obj) {
		return di.update(obj);
	}

	protected <T> List<T> find(String sql, Class<?> clazz) {
		return di.find(sql, clazz);
	}
	
	protected void copyFile(File res,File target){
		InputStream is=null;
		OutputStream os=null;
		try {
			is=new FileInputStream(res);
			os=new FileOutputStream(target);
			 byte[] buf = new byte[1024];    
			  int bytesRead; 
			  while ((bytesRead = is.read(buf)) > 0) {
			      os.write(buf, 0, bytesRead);
			    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				os.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
