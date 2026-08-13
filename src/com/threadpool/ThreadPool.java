package com.threadpool;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * ThreadPool
 * ThreadPool class manages a pool of worker threads that execute tasks from a shared task queue. It provides methods to submit tasks and gracefully shut down the thread pool.
 * 
 */
public class ThreadPool {
    private final ThreadSafeTaskQueue taskQueue;
    private final List<Thread> threads = new ArrayList<>();
    private final List<Worker> workers = new ArrayList<>();
    /**
     * Constructs a ThreadPool with the specified number of threads and task queue.
     * @param numT The number of worker threads to create.
     * @param taskQueue The task queue from which threads will fetch tasks.
     */
    public ThreadPool(int numT, ThreadSafeTaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        for (int i = 0; i < numT; i++) {
            Worker worker = new Worker(taskQueue);
            workers.add(worker);
            Thread thread = new Thread(worker, "CustomWorker-" + (i + 1));
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Submits a task to the thread pool for execution.
     * @param task The task to be executed.
     */
    public void executeTask(Task task) {
        taskQueue.submit(task);
    }

    /**
     * Initiates a graceful shutdown of the thread pool, stopping all worker threads.
     */
    public void shutdown() {
        System.out.println("Initiating shutdown of the thread pool.");
        for (Worker worker : workers) {
            worker.stopWorker();
        }
        for (Thread thread : threads) {

            thread.interrupt();

        }
    }
}
