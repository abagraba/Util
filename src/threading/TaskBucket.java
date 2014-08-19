package threading;

public class TaskBucket {

	private boolean dispensable;
	
	private ArrayList<DispensableTask> tasks;
	
	
	
	
	public void makeDispensable(){
		dispensable = true;
	}
	
	public void reset(){
		dispensable = false;
	}
	
}
