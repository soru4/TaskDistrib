import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.threadpool.Task;

public class TaskTest {

    /** 
     * @throws Exception
     */
    @Test
    public void execute_setsCompletedAndRunsWork() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Task task = new Task("1", "TypeA", latch::countDown);

        assertFalse(task.isCompleted());

        task.execute();

        assertTrue(task.isCompleted(), "Task should be marked completed after execute()");
        assertTrue(latch.await(1, TimeUnit.SECONDS), "Task work should run before execute() returns");
    }
}
