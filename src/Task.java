public class Task {
    private final String taskId;
    private final String taskType;
    private final Runnable work;

    private volatile boolean completed = false;
    public Task(String taskId, String taskType, Runnable work) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.work = work;
    }
    public String getTaskId() {
        return taskId;
    }
    public String getTaskType() {
        return taskType;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean c) {
        this.completed = c;
    }

    public void execute(){
        System.out.println("Starting task " + taskId  + " on thread: " + Thread.currentThread().getName());
        work.run(); 
        setCompleted(true);
        System.out.println("Finished task " + taskId);
    }
}
