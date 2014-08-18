package threading;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * A TaskRunner repeatedly runs a single task until stopped. It also allows for non-interrupting pausing and stopping.
 *
 */
public class TaskRunner {

	private Task task;
	private final Thread taskThread;
	private volatile AtomicBoolean start = new AtomicBoolean(false);
	private volatile AtomicBoolean pause = new AtomicBoolean(false);
	private volatile boolean stop = false;

	public TaskRunner() {
		taskThread = new Thread(run);
	}
	
	public TaskRunner(Task task) {
		this();
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
		if (start.compareAndSet(false, true))
			taskThread.start();
	}

	public void pause() {
		pause.set(true);
	}

	public void unpause() {
		if (pause.compareAndSet(true, false))
			LockSupport.unpark(taskThread);
	}

	public void stop() {
		stop = true;
	}

	Runnable run = new Runnable() {
		@Override
		public final void run() {
			LockSupport.park();
			while (!stop) {
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
