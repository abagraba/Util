package util.collections;

import java.util.HashMap;
import java.util.Map;



public class MapOfMaps<Key1, Key2, Value> {
	private HashMap<Key1, Map<Key2, Value>>	map	= new HashMap<Key1, Map<Key2, Value>>();	;

	public Value put(Key1 key1, Key2 key2, Value value) {
		if (!map.containsKey(key1))
			map.put(key1, new HashMap<Key2, Value>());
		Map<Key2, Value> mapmap = map.get(key1);
		Value old = mapmap.get(key2);
		mapmap.put(key2, value);
		return old;
	}

	public Value get(Key1 key1, Key2 key2) {
		if (!map.containsKey(key1))
			return null;
		return map.get(key1).get(key2);
	}

	public Value remove(Key1 key1, Key2 key2) {
		if (!map.containsKey(key1))
			return null;
		Map<Key2, Value> mapmap = map.get(key1);
		Value old = mapmap.remove(key2);
		if (mapmap.isEmpty())
			map.remove(key1);
		return old;
	}
}
