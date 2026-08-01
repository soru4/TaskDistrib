public class Worker implements Runnable {
    private final ThreadSafeTaskQueue taskQueue;
    private volatile boolean running = true;

    public Worker(ThreadSafeTaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

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

    public void stopWorker() {
        running = false;
        Thread.currentThread().interrupt(); 
    }
}
