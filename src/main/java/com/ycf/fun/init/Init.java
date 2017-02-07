package com.ycf.fun.init;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ycf.fun.contain.Bean;
import com.ycf.fun.contain.Mapper;
import com.ycf.fun.contain.Router;

public abstract class Init {
	public static void initXml(String bean, String router, String mapper) {
		String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
		initBean(classpath + bean);
		initMapper(classpath + mapper);
		initRouter(classpath + router);
	}

	public static Properties getConfig(String config) {
		String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
		Properties prop = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(classpath + config));
			prop.load(in);
			in.close();/// 加载属性列表
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	private static void initBean(String bean) {
		for (Element element : elements(bean, "bean")) {
			String name = element.attributeValue("id");
			Object value = null;
			try {
				value = Class.forName(element.attributeValue("class")).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bean.getInstance().put(name, value);
		}
	}

	private static void initRouter(String router) {
		Router route = new Router();
		for (Element element : elements(router, "router")) {
			String path = element.attributeValue("path");
			String clazz = element.attributeValue("class");
			route.put(path, clazz);
		}
		Bean.getInstance().put("router", route);
	}

	private static void initMapper(String mapper) {
		Mapper mappe = new Mapper();
		for (Element element : elements(mapper, "mapper")) {

			Class<?> clazz = null;
			try {
				clazz = Class.forName(element.attributeValue("class"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String tbname = element.attributeValue("tbname");
			String pkey = element.attributeValue("pkey");
			String[] tbinfo = { tbname, pkey };
			mappe.put(clazz, tbinfo);
		}
		Bean.getInstance().put("mapper", mappe);
	}

	private static List<Element> elements(String path, String name) {
		try {
			return (List<Element>) new SAXReader().read(path).getRootElement().elements(name);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
