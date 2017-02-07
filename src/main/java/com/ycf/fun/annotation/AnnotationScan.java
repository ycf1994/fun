package com.ycf.fun.annotation;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ycf.fun.contain.Bean;
import com.ycf.fun.contain.Mapper;
import com.ycf.fun.util.ClassUtil;

public class AnnotationScan {
	private static Logger log=Logger.getLogger(AnnotationScan.class);
	public static void initMapper(String mapper){
		Mapper map=new Mapper();
		for(String mapp:mapper.split(",")){
		List<Class<?>> classes=ClassUtil.getClasses(mapp);
		
		for(Class<?> clazz:classes){
			if(clazz.isAnnotationPresent(Domain.class)){
				Domain domain=clazz.getAnnotation(Domain.class);
				String tbname=domain.tbname();
				String pkey=domain.pkey();
				String[] tbinfo={tbname,pkey};
			//	System.out.println(tbname);
				//System.out.println(pkey);
				map.put(clazz, tbinfo);
				log.info(new Date()+"_____________________Mapper[class="+clazz+",tbname="+tbname+",pkey="+pkey+"]");
			}
		}
		}
		Bean.getInstance().put("mapper", map);
		
	}
	public static void initRouter(String router){
		com.ycf.fun.contain.Router route=new com.ycf.fun.contain.Router();
		for(String ro:router.split(",")){
		List<Class<?>> classes=ClassUtil.getClasses(ro);	
		
		for(Class<?> clazz:classes){
			if(clazz.isAnnotationPresent(Router.class)){
				Router rou=clazz.getAnnotation(Router.class);
				String uri=rou.uri();
				String clas=clazz.getName();
				//System.out.println(uri);
				//System.out.println(clas);
				Object o=null;
				try {
					o = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				route.put(uri, clas);
				Bean.getInstance().put(clas, o);
				log.info(new Date()+"_____________________Router[class="+clazz+",uri="+uri+"]");
			}
		}
		}
		Bean.getInstance().put("router", route);
	}
}
