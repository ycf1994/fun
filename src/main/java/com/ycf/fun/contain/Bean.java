package com.ycf.fun.contain;

public class Bean extends Contain<String, Object>  {

	@Override
	public void put(String key, Object value) {
		this.map.put(key, value);

	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return this.map.get(key);
	}

	private Bean() {

	}

	private static Bean bean;

	public static Bean getInstance() {
		if (bean == null) {
			synchronized (Bean.class) {
				if (bean == null)
					bean = new Bean();
			}
		}
		return bean;
	}

}
