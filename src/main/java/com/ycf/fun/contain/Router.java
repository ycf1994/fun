package com.ycf.fun.contain;

public class Router extends Contain<String, String> {

	@Override
	public void put(String key, String value) {
		// TODO Auto-generated method stub
		this.map.put(key, value);

	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return this.map.get(key);
	}

}
