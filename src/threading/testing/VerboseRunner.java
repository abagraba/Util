package threading.testing;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

import threading.Task;
import threading.TaskRunner;

public class VerboseRunner extends TaskRunner {

	private int i;
	private Task task;
	private final Thread taskThread;
	private volatile AtomicBoolean start = new AtomicBoolean(false);
	private volatile AtomicBoolean pause = new AtomicBoolean(false);
	private volatile boolean stop = false;

	public VerboseRunner(int i) {
		this.i = i;
		taskThread = new Thread(run);
	}
	
	public VerboseRunner(int i, Task task) {
		this(i);
		this.task = task;
	}

	public void setTask(Task task) {
		if (task == null)
			throw new NullPointerException("TaskRunner cannot run null Tasks.");
		this.task = task;
	}
	
	public void start() {
		if (task == null)
			throw new NullPointerException("TaskRunner cannot run null Tasks.");
		System.out.printf("#%d:\tstarted.\n", i);
		if (start.compareAndSet(false, true))
			taskThread.start();
	}

	public void pause() {
		pause.set(true);
		System.out.printf("#%d:\tpaused.\n", i);
	}

	public void unpause() {
		if (pause.compareAndSet(true, false))
			LockSupport.unpark(taskThread);
		System.out.printf("#%d:\tunpaused.\n", i);
	}

	public void stop() {
		stop = true;
		System.out.printf("#%d:\tstopped.\n", i);
	}

	protected Runnable run = new Runnable() {
		@Override
		public final void run() {
			while (!stop) {
				System.out.printf("#%d:\tattempting to run task.\n", i);
				runTask(task);
				while (pause.get())
					LockSupport.park();
			}
		}
	};
	
	public void runTask(Task task){
		task.task();
	}

}
