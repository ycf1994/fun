package com.ycf.fun.handler;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ycf.fun.annotation.AnnotationScan;
import com.ycf.fun.contain.Bean;
import com.ycf.fun.contain.Router;
import com.ycf.fun.controller.Controller;
import com.ycf.fun.db.DBImpl;
import com.ycf.fun.init.Init;
import com.ycf.fun.util.Util;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public abstract class Handler
{
    public static void init(String bean, String router, String mapper, String config)
    {
        
        Properties pro = Init.getConfig(config);
        Controller.setDi(new DBImpl(pro.getProperty("driver"), pro.getProperty("url"), pro.getProperty("user"),
            pro.getProperty("pwd"), Integer.valueOf(pro.getProperty("poolSize"))));
        Controller.setPrefix(pro.getProperty("prefix"));
        Controller.setSuffix(pro.getProperty("suffix"));
        if (pro.getProperty("annotation") != null)
        {
            AnnotationScan.initMapper(pro.getProperty("model"));
            AnnotationScan.initRouter(pro.getProperty("controller"));
        }
        else
            Init.initXml(bean, router, mapper);
    }
    
    public static void handler(String router, String method, HttpServletRequest request, HttpServletResponse response)
    {
        // FileHandler.getAllParamValues(request);
        delOldFiles(request);
        // System.out.println(isMultipart);
        String className = ((Router)Bean.getInstance().get("router")).get(router);
        Object o = Bean.getInstance().get(className);
        Class<?> clazz = o.getClass();
        try
        {
            clazz.getMethod("setRequest", HttpServletRequest.class).invoke(o, request);
            clazz.getMethod("setResponse", HttpServletResponse.class).invoke(o, response);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
            | SecurityException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Method m = getMethod(clazz, method);
        List<Object[]> lists = getMethodParams(clazz, method);
        Object[] values = new Object[lists.size()];
        Map<String, Object> map = getValuesMap(request);
        
        for (int i = 0; i < lists.size(); i++)
        {
            Object[] param = lists.get(i);
            String name = (String)param[0];
            Class<?> type = (Class<?>)param[1];
            // System.out.println(i);
            values[i] = getValue(name, type, request, response, map);
            
        }
        
        try
        {
            m.invoke(o, values);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    private static Object getValue(String name, Class<?> type, HttpServletRequest request, HttpServletResponse response,
        Map<String, Object> map)
    {
        if (type == byte.class || type == Byte.class)
            return getByte(getParasArray(map, name)[0]);
        if (type == short.class || type == Short.class)
            return getShort(getParasArray(map, name)[0]);
        if (type == int.class || type == Integer.class)
            return getInt(getParasArray(map, name)[0]);
        if (type == long.class || type == Long.class)
            return getLong(getParasArray(map, name)[0]);
        if (type == float.class || type == Float.class)
            return getFloat(getParasArray(map, name)[0]);
        if (type == double.class || type == Double.class)
            return getDouble(getParasArray(map, name)[0]);
        if (type == char.class || type == Character.class)
            return getChar(getParasArray(map, name)[0]);
        if (type == boolean.class || type == Boolean.class)
            return getBoolean(getParasArray(map, name)[0]);
        if (type == byte[].class || type == Byte[].class)
            return getByte(getParasArray(map, name)[0]);
        if (type == short.class || type == Short.class)
            return getShort(getParasArray(map, name)[0]);
        if (type == int.class || type == Integer.class)
            return getInt(getParasArray(map, name)[0]);
        if (type == long.class || type == Long.class)
            return getLong(getParasArray(map, name)[0]);
        if (type == float.class || type == Float.class)
            return getFloat(getParasArray(map, name)[0]);
        if (type == double.class || type == Double.class)
            return getDouble(getParasArray(map, name)[0]);
        if (type == char.class || type == Character.class)
            return getChar(getParasArray(map, name)[0]);
        if (type == boolean.class || type == Boolean.class)
            return getBoolean(getParasArray(map, name)[0]);
        if (type == HttpServletRequest.class)
            return request;
        if (type == HttpSession.class)
            return request.getSession();
        if (type == ServletContext.class)
            return request.getSession().getServletContext();
        if (type == HttpServletResponse.class)
            return response;
        if (type == String.class)
        {
            // System.out.println(name);
            return getString(getParasArray(map, name)[0]);
        }
        if (type == File.class)
            return (File)map.get(name);
        return getRefTypeValue(name, type, map);
    }
    
    private static Object getRefTypeValue(String name, Class<?> type, Map<String, Object> map)
    {
        try
        {
            Object obj = type.newInstance();
            Field[] fields = type.getDeclaredFields();
            Field.setAccessible(fields, true);
            Method set = Util.setMethod(type);
            for (Field field : fields)
            {
                String fieldName = field.getName();
                String fieldValue =
                    map.get(name + "." + fieldName) != null ? ((String[])map.get(name + "." + fieldName))[0] : null;
                if (fieldValue == null)
                    continue;
                set.invoke(obj, fieldName, fieldValue);
            }
            return obj;
        }
        catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException
            | InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
    }
    
    private static Method getMethod(Class<?> clazz, String method)
    {
        Method[] ms = clazz.getDeclaredMethods();
        for (Method m : ms)
        {
            if (m.getName().equals(method))
                return m;
        }
        return null;
    }
    
    private static List<Object[]> getMethodParams(Class<?> clazz, String method)
    {
        Method m = getMethod(clazz, method);
        Class<?>[] types = m.getParameterTypes();
        List<Object[]> lists = new ArrayList<Object[]>();
        try
        {
            ClassPool pool = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(Handler.class);
            pool.insertClassPath(classPath);
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod(method);
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr =
                (LocalVariableAttribute)codeAttribute.getAttribute(LocalVariableAttribute.tag);
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < types.length; i++)
            {
                String paramName = attr.variableName(i + pos);
                Object[] param = {paramName, types[i]};
                lists.add(param);
            }
            
        }
        catch (NotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lists;
    }
    
    private static short getShort(String value)
    {
        return Short.valueOf(value);
    }
    
    private static byte getByte(String value)
    {
        return Byte.valueOf(value);
    }
    
    private static int getInt(String value)
    {
        return Integer.valueOf(value);
    }
    
    private static long getLong(String value)
    {
        return Long.valueOf(value);
    }
    
    private static float getFloat(String value)
    {
        return Float.valueOf(value);
    }
    
    private static double getDouble(String value)
    {
        return Double.valueOf(value);
    }
    
    private static char getChar(String value)
    {
        return Character.valueOf(value.charAt(0));
    }
    
    private static boolean getBoolean(String value)
    {
        return Boolean.valueOf(value);
    }
    
    private static String getString(String value)
    {
        return value;
    }
    
    private static Map<String, Object> getValuesMap(HttpServletRequest request)
    {
        return FileHandler.getAllParamValues(request);
    }
    
    private static String[] getParasArray(Map<String, Object> map, String para)
    {
        return (String[])map.get(para);
    }
    
    private static void delOldFiles(HttpServletRequest request)
    {
        String path = request.getSession().getServletContext().getRealPath("/tmp");
        File[] files = new File(path).listFiles();
        for (File file : files)
        {
            if (System.currentTimeMillis() - file.lastModified() > 15000)
            {
                file.delete();
            }
        }
    }
}
