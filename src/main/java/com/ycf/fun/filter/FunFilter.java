package com.ycf.fun.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ycf.fun.handler.Handler;

public class FunFilter implements Filter {
	private int len;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		//System.out.println("init........");
		//System.out.println(filterConfig.getServletContext().getContextPath());
		len = filterConfig.getServletContext().getContextPath().length() + 1;
		String bean=filterConfig.getInitParameter("bean");
		String mapper=filterConfig.getInitParameter("mapper");
		String router=filterConfig.getInitParameter("router");
		String config=filterConfig.getInitParameter("config");
		Handler.init(bean, router, mapper, config);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		String uri = req.getRequestURI().substring(len);
		String[] path = uri.split("/");
		int len = path.length;
		
		if (uri.indexOf(".") < 0 && len > 1) {
			String router = get(path);
			String method = path[len - 1];
			Handler.handler(router, method, req, res);
		}else
			chain.doFilter(request, response);
		

	}

	public String get(String[] path) {
		String router = "";
		for (int i = 0; i < path.length - 1; i++) {
			router += path[i];
			if (i != path.length - 2)
				router += "/";
		}
		return router;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
