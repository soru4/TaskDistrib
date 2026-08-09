import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.threadpool.Task;
import com.threadpool.ThreadSafeTaskQueue;

public class ThreadSafeTaskQueueTest {

    @Test
    public void submitAndTake_roundTripsTask() throws Exception {
        ThreadSafeTaskQueue queue = new ThreadSafeTaskQueue();
        Task task = new Task("1", "TypeA", () -> {});

        queue.submit(task);
        Task taken = queue.take();

        assertSame(task, taken, "The taken task should be the same instance that was submitted");
    }

    @Test
    public void take_waitsUntilTaskIsAvailable() throws Exception {
        ThreadSafeTaskQueue queue = new ThreadSafeTaskQueue();
        Task task = new Task("1", "TypeA", () -> {});

        AtomicReference<Task> result = new AtomicReference<>();
        CountDownLatch startedWaiting = new CountDownLatch(1);
        CountDownLatch finished = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            try {
                startedWaiting.countDown();
                result.set(queue.take());
                finished.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();

        assertTrue(startedWaiting.await(1, TimeUnit.SECONDS), "Worker thread should start waiting in take()");

        Thread.sleep(100);
        queue.submit(task);

        assertTrue(finished.await(1, TimeUnit.SECONDS), "take() should return after submit()");
        assertSame(task, result.get(), "The task returned by take() should match the submitted task");

        thread.interrupt();
    }
}
