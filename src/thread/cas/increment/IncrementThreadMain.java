package thread.cas.increment;

import java.util.ArrayList;
import java.util.List;

import static thread.util.ThreadUtils.sleep;

public class IncrementThreadMain {

    public static final int THREAD_COUNT=1000;

    public static void main(String[] args) throws InterruptedException {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
    }

    private static void test(IncrementInteger incrementInteger) throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sleep(10); //동시 실행 될 확률 높기이 위해
                incrementInteger.increment();
            }
        };

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        int result = incrementInteger.get();
        System.out.println(incrementInteger.getClass().getSimpleName() + " result=" + result);
    }
}
