package com.threadpool;
/**
 * 
 * Worker class represents a worker thread that continuously fetches and executes tasks from a shared task queue. It provides methods to start and stop the worker thread.
 * 
 */
public class Worker implements Runnable {
    private final ThreadSafeTaskQueue taskQueue;
    private volatile boolean running = true;
    /**
     * Constructs a Worker with the specified task queue.
     * @param taskQueue The task queue from which the worker will fetch tasks.
     */
    public Worker(ThreadSafeTaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }
    /**
     * Runs the worker thread, continuously fetching and executing tasks.
     */
    @Override
    public void run(){
            System.out.println(Thread.currentThread().getName() + " is ready for assignments.");
            while(running && !Thread.currentThread().isInterrupted()){
                try{
                    Task currTask = taskQueue.take();
                    currTask.execute();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println( Thread.currentThread().getName() + " was interrupted.");
                }catch (Exception e){
                    System.out.println( Thread.currentThread().getName() + " encountered an error: " + e.getMessage());
                }

            }
    }
    /**
     * Stops the worker thread.
     * @param taskQueue The task queue from which the worker will fetch tasks.
     */
    public void stopWorker() {
        running = false;
        Thread.currentThread().interrupt(); 
    }
}
