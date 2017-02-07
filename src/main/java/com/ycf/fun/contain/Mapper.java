package com.ycf.fun.contain;

public class Mapper extends Contain<Class<?>, String[]>  {

	@Override
	public void put(Class<?> key, String[] value) {
		this.map.put(key, value);

	}

	@Override
	public String[] get(Class<?> key) {
		// TODO Auto-generated method stub
		return this.map.get(key);
	}

}
