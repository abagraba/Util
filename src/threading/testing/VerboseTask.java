package threading.testing;

import threading.Task;

public class VerboseTask extends Task {
	public int i;
	public boolean slow;

	public VerboseTask(int i, boolean slow) {
		this.i = i;
		this.slow = slow;
	}

	@Override
	public void task() {
		if (slow) {
			System.out.printf("\t\t\t#%d:\tstarting.\n", i);
			for (int i = 0; i < 100000; i++) {
				Math.sin(i);
			}
		}
		System.out.printf("\t\t\t#%d:\tdone.\n", i);
	}

	public String toString() {
		return "" + i;
	}
}
