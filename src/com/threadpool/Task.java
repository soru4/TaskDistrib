package com.threadpool;
/**
 * 
 * Task class represents a unit of work that can be executed by a worker thread.
 */
public class Task {
    private final String taskId;
    private final String taskType;
    private final Runnable work;

    private volatile boolean completed = false;
    /**
     * Constructs a Task with the specified task ID, task type, and work to be executed.
     * @param taskId
     * @param taskType
     * @param work
     */
    public Task(String taskId, String taskType, Runnable work) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.work = work;
    }
    /**
     * Returns the task ID.
     * @return
     */
    public String getTaskId() {
        return taskId;
    }
    /**
     * Returns the task type.
     * @return The task type.
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Returns whether the task is completed.
     * @return True if the task is completed, false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Sets the completion status of the task.
     * @param c True if the task is completed, false otherwise.
     */
    public void setCompleted(boolean c) {
        this.completed = c;
    }
    /**
     * Executes the task by running the associated work.
     */
    public void execute(){
        System.out.println("Starting task " + taskId  + " on thread: " + Thread.currentThread().getName());
        work.run(); 
        setCompleted(true);
        System.out.println("Finished task " + taskId);
    }
}
