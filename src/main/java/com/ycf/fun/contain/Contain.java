package com.ycf.fun.contain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Contain<K, V> {
	protected final Map<K, V> map = new ConcurrentHashMap<K, V>();

	public abstract void put(K key, V value);

	public abstract V get(K key);
}
