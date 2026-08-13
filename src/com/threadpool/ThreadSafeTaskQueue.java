package com.threadpool;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 *  
 * 
 * ThreadSafeTaskQueue
 * 
 * ThreadSafeTaskQueue class provides a thread-safe implementation of a task queue that allows multiple threads to submit and retrieve tasks concurrently. It uses locks and conditions to ensure safe access to the underlying queue.
 */
public class ThreadSafeTaskQueue {
    private final Queue<Task> taskQueue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notEmpty = lock.newCondition();
    /**
     * Submits a task to the thread-safe task queue.
     * @param task The task to be submitted.
     */
    public void submit(Task task){
        lock.lock();
        try {
            taskQueue.add(task);
            System.out.println("Task " + task.getTaskId() + " added to the line!");
            notEmpty.signal(); 
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves a task from the thread-safe task queue.
     * @return The next task in the queue.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public Task take() throws InterruptedException{
        lock.lock();
        try{
            while(taskQueue.isEmpty()){
                System.out.println(Thread.currentThread().getName() + " line empty");
                notEmpty.await(); 
            }
            return taskQueue.poll();
        } finally {
            lock.unlock();
        }
    }
    /**
     * Returns the underlying task queue.
     * @return The task queue.
     */
    public Queue<Task> getTaskQueue() {
        return taskQueue;
    }
}


