package com.ycf.fun.model;

import java.lang.reflect.Field;

/**
 * 实体类父类<br/>
 * 
 * 赋值 set(String fieldName, String value)<br/>
 * 
 * 
 * 取值 get(String fieldName)
 */
public abstract class Model
{
    private Field getField(String fieldName)
    {
        Field field = null;
        try
        {
            field = this.getClass().getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException | SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        field.setAccessible(true);
        return field;
    }
    
    private Object getCorrectTypeValue(Field field, String value)
    {
        Class<?> type = field.getType();
        if (type == int.class || type == Integer.class)
            return Integer.valueOf(value);
        if (type == byte.class || type == Byte.class)
            return Byte.valueOf(value);
        if (type == short.class || type == Short.class)
            return Short.valueOf(value);
        if (type == long.class || type == Long.class)
            return Long.valueOf(value);
        if (type == float.class || type == Float.class)
            return Float.valueOf(value);
        if (type == double.class || type == Double.class)
            return Double.valueOf(value);
        if (type == boolean.class || type == Boolean.class)
            return Boolean.valueOf(value);
        if (type == char.class || type == Character.class)
            return Character.valueOf(value.charAt(0));
        if (type == String.class)
            return value;
        return null;
        
    }
    
    public void set(String fieldName, String value)
    {
        Field field = getField(fieldName);
        try
        {
            field.set(this, getCorrectTypeValue(field, value));
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Object get(String fieldName)
    {
        Field field = getField(fieldName);
        try
        {
            return field.get(this);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
