import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {
    AtomicInteger waterLevel;

    public Producer(AtomicInteger waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void run() {
        while (true) {
            synchronized (this) {
                while (waterLevel.get() >= 90) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                waterLevel.incrementAndGet();

                System.out.println("Producer produces-" + waterLevel.get());

                notify();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}