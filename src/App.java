import java.util.UUID;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Task Distributor Application Started.");

        ThreadSafeTaskQueue taskQueue = new ThreadSafeTaskQueue();
        ThreadPool threadPool = new ThreadPool(3, taskQueue);

        for (int i = 1; i <= 10; i++) {
            final int jobId = i;
            String taskType = (i % 2 == 0) ? "TypeA" : "TypeB";
            Runnable work = () -> {
                try {
                    long computeTime = (long) (Math.random() * 15000 + 5000);
                    Thread.sleep(computeTime);
                    System.out.println(
                            "Job #" + jobId + " completed a math operation in " + computeTime + "ms");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            Task task = new Task(String.valueOf(jobId), taskType, work);
            threadPool.executeTask(task);
        }

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
        System.out.println("Thread pool has been shut down.");

    }
}
