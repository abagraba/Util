package PNGDecoder;

import java.util.HashMap;


public class ChunkRegistry {

	private static final HashMap<String, Class<? extends Chunk>> registry = new HashMap<String, Class<? extends Chunk>>();
	
	public static void registerChunk(String header, Class<? extends Chunk> implementation){
		registry.put(header, implementation);
	}
	
	public static Class<? extends Chunk> getChunkImplementation(String header){
		if (registry.containsKey(header))
			return registry.get(header);
		return Chunk.class;
	}
	
}
