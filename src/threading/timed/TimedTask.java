package threading.timed;

import threading.Task;

public class TimedTask extends Task {
	private long lastCall = System.nanoTime();
	private long[] average;
	private int averageIndex;
	
	
	public TimedTask(int smoothing) {
		average = new long[smoothing];
		for (int i = 0; i < average.length; i++)
			average[i] = Long.MAX_VALUE;
	}
	
	
	@Override
	public final void task() {
		
		run();
	}
	
	public void run(){
		
	}
}
