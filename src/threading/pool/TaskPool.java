package threading.pool;

import java.util.concurrent.ConcurrentLinkedQueue;

import threading.RunnerTask;
import threading.Task;
import threading.TaskRunner;

public class TaskPool {

	private final ConcurrentLinkedQueue<Task> tasks;
	private final TaskRunner[] threads;
	private final ConcurrentLinkedQueue<TaskRunner> inactiveThreads;

	public TaskPool(int threads) {
		tasks = new ConcurrentLinkedQueue<Task>();
		inactiveThreads = new ConcurrentLinkedQueue<TaskRunner>();
		this.threads = new TaskRunner[threads];
		for (int i = 0; i < threads; i++) {
			this.threads[i] = new TaskRunner();
			Task task = task(this.threads[i]);
			this.threads[i].setTask(task);
			this.threads[i].start();
		}
	}

	public TaskPool(TaskRunner[] runners) {
		tasks = new ConcurrentLinkedQueue<Task>();
		inactiveThreads = new ConcurrentLinkedQueue<TaskRunner>();
		this.threads = runners;
		for (int i = 0; i < runners.length; i++) {
			Task task = task(this.threads[i]);
			this.threads[i].setTask(task);
			this.threads[i].start();
		}
	}

	public void addTask(Task task) {
		tasks.add(task);
		System.out.println(tasks.size() + " tasks in queue");
		TaskRunner runner = inactiveThreads.poll();
		if (runner != null)
			runner.unpause();
		System.out.println(inactiveThreads.size() + " sleeping threads left.");
	}

	public void stop() {
		for (TaskRunner taskRunner : threads) {
			taskRunner.stop();
			taskRunner.unpause();
		}
	}

	private RunnerTask task(TaskRunner runner) {
		RunnerTask poolTask = new RunnerTask(runner) {
			@Override
			public void task() {
				Task task = tasks.poll();
				if (task == null) {
					runner.pause();
					inactiveThreads.add(runner);
				} else {
					task.task();
				}
			}
		};
		return poolTask;
	}

}
