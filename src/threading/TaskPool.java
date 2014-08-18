package threading;

import java.util.concurrent.ConcurrentLinkedQueue;

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
				System.out.println("Grabbing Task");
				Task task = tasks.poll();
				if (task == null) {
					System.out.println("No tasks found. Sleeping.");
					runner.pause();
					inactiveThreads.add(runner);
					System.out.println(inactiveThreads.size() + " sleeping threads.");
				} else {
					task.task();
				}
			}
		};
		return poolTask;
	}

	public static void main(String[] args) {
		TaskPool pool = new TaskPool(testRunners(1));
		long t = System.nanoTime();
		int i = 0;
		pool.addTask(new TestTask(i));
	}

	private static TaskRunner[] testRunners(int count) {
		TaskRunner[] runners = new TaskRunner[count];
		for (int i = 0; i < runners.length; i++) {
			runners[i] = new TestRunner(i);
		}
		return runners;
	}

	private static class TestTask extends Task {
		public int i;

		public TestTask(int i) {
			this.i = 0;
		}

		@Override
		public void task() {
			System.out.printf("Task # %d run.\n", i);
		}

		public String toString() {
			return "" + i;
		}
	}

	private static class TestRunner extends TaskRunner {
		public int i;

		public TestRunner(int i) {
			this.i = 0;
		}

		public void runTask(Task task) {
			System.out.printf("Runner #%d running task\n", i);
		}
	}

}
