import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ThreadPoolTest {

    @Test
    public void executeTask_completesTask() throws Exception {
        ThreadSafeTaskQueue queue = new ThreadSafeTaskQueue();
        ThreadPool pool = new ThreadPool(1, queue);

        CountDownLatch latch = new CountDownLatch(1);
        Task task = new Task("1", "TypeA", latch::countDown);

        pool.executeTask(task);

        assertTrue(latch.await(2, TimeUnit.SECONDS), "Task should complete within 2 seconds");
        assertTrue(task.isCompleted(), "Task should be marked completed by the worker");

        pool.shutdown();
      
    }
}
