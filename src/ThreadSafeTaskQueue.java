import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadSafeTaskQueue {
    private final Queue<Task> taskQueue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notEmpty = lock.newCondition();

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
}


