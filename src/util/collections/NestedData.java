package util.data;

import java.util.HashMap;
import java.util.Map;



public class NestedData {

	public static <Key1, Key2, Value> Map<Key1, Map<Key2, Value>> mapOfMaps(Class<Key1> key1, Class<Key2> key2, Class<Value> value) {
		return new HashMap<Key1, Map<Key2, Value>>();
	}

	public static <Key1, Key2, Value> Value put(Map<Key1, Map<Key2, Value>> map, Key1 key1, Key2 key2, Value value) {
		if (!map.containsKey(key1))
			map.put(key1, new HashMap<Key2, Value>());
		Map<Key2, Value> mapmap = map.get(key1);
		Value old = mapmap.get(key2);
		mapmap.put(key2, value);
		return old;
	}

	public static <Key1, Key2, Value> Value get(Map<Key1, Map<Key2, Value>> map, Key1 key1, Key2 key2) {
		if (!map.containsKey(key1))
			return null;
		return map.get(key1).get(key2);
	}

	public static <Key1, Key2, Value> Value remove(Map<Key1, Map<Key2, Value>> map, Key1 key1, Key2 key2) {
		if (!map.containsKey(key1))
			return null;
		Map<Key2, Value> mapmap = map.get(key1);
		Value old = mapmap.remove(key2);
		if
	}
}
