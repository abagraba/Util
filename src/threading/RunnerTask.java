package threading;


/**
 * A task which knows its TaskRunner. Necessary if the task needs some thread control.
 */
public abstract class RunnerTask extends Task{

	protected TaskRunner runner;
	
	public RunnerTask(TaskRunner runner){
		this.runner = runner;
	}
	
}
