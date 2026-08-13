package com.threadpool;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
/**
 * 
 * Just initializes the thread pool and assigns arbitrary tasks to it. 
 */
public class App {
    /**
     * Main method to start the application.
     * @param args Command line arguments.
     * @throws Exception If any error occurs during execution.
     */
    public static void main(String[] args) throws Exception {
        System.out.println(" Application Started.");
        ArrayList<Task> tasks = new ArrayList<>();
        ThreadSafeTaskQueue taskQueue = new ThreadSafeTaskQueue();
        ThreadPool threadPool = new ThreadPool(3, taskQueue);
        /*
         * Assigns just arbitrary tasks to thread pool for 
         * 
         */
        for (int i = 1; i <= 10; i++) {
            final int jobId = i;
            String taskType = (i % 2 == 0) ? "TypeA" : "TypeB";
            Runnable work = () -> {
                try {
                    if (jobId ==9) {
                        int b = jobId / 0;
                    }
                    long computeTime = (long) (Math.random() * 15000 + 3000);
                    Thread.sleep(computeTime);
                    System.out.println(
                            "Job " + jobId + " completed a math operation in " + computeTime + "ms");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            Task task = new Task(String.valueOf(jobId), taskType, work);
            tasks.add(task);
            threadPool.executeTask(task);
        }
        /*Ending logic can be changed so that it doesnt stop ever or whatever a project requires */
        boolean allComplete = false;
        while (!allComplete) {
            allComplete = endingLogic(taskQueue);
            Thread.sleep(100); 
        }

        if (allComplete) {
            System.out.println("All tasks have been completed.");

            threadPool.shutdown();
            System.out.println("Thread pool has been shut down.");
        } else {
            System.out.println("Some tasks are still pending.");
        }

    }
    /**
     * Ending logic can be changed so that it doesnt stop ever or whatever a project requires
     * @param taskqueue
     * @return
     */
    public static boolean endingLogic(ThreadSafeTaskQueue taskqueue) {
        if(taskqueue.getTaskQueue().isEmpty()){
            return true;
        }
        return false;
    }
}

