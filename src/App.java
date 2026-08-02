import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(" Application Started.");
        ArrayList<Task> tasks = new ArrayList<>();
        ThreadSafeTaskQueue taskQueue = new ThreadSafeTaskQueue();
        ThreadPool threadPool = new ThreadPool(3, taskQueue);
        /*
         * Assigns just arbitrary tasks to thread pool for now hopefully in the future
         * will be able to be incoprortated into other projects
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
            int i = 0;
            for (Task task : tasks) {

                if (!task.isCompleted()) {

                    break;
                }
                i++;
            }
            if (i == tasks.size()) {
                allComplete = true;
            }
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
}
