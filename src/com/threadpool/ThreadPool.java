package com.threadpool;
import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    private final ThreadSafeTaskQueue taskQueue;
    private final List<Thread> threads = new ArrayList<>();
    private final List<Worker> workers = new ArrayList<>();

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

    public void executeTask(Task task) {
        taskQueue.submit(task);
    }

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
