package threading.testing;

import java.util.concurrent.ConcurrentLinkedQueue;

import threading.RunnerTask;
import threading.Task;
import threading.TaskRunner;
import threading.pool.TaskPool;

public class VerbosePool {

		private final ConcurrentLinkedQueue<Task> tasks;
		private final TaskRunner[] threads;
		private final ConcurrentLinkedQueue<TaskRunner> inactiveThreads;

		public VerbosePool(int threads) {
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

		public VerbosePool(TaskRunner[] runners) {
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
			System.out.printf("\t\t\t\t\t\tTask added. %d tasks in queue\n", tasks.size() );
			TaskRunner runner = inactiveThreads.poll();
			if (runner != null){
				System.out.printf("\t\t\t\t\t\t%d sleeping threads left.\n", inactiveThreads.size());
				runner.unpause();
			}
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
					System.out.println("\t\t\t\t\t\tGrabbing Task");
					Task task = tasks.poll();
					if (task == null) {
						System.out.println("\t\t\t\t\t\tNo tasks found. Sleeping.");
						runner.pause();
						inactiveThreads.add(runner);
					} else {
						task.task();
					}
				}
			};
			return poolTask;
		}

		public static void main(String[] args) {
			TaskPool pool = new TaskPool(testRunners(4));
			int i = 0;
			for (; i < 256;)
				pool.addTask(new VerboseTask(i++, true));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i = 0;
			for (; i < 256;)
				pool.addTask(new VerboseTask(i++, true));
		}

		private static TaskRunner[] testRunners(int count) {
			TaskRunner[] runners = new TaskRunner[count];
			for (int i = 0; i < runners.length; i++) {
				runners[i] = new VerboseRunner(i);
			}
			return runners;
		}

	}


