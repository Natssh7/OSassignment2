import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends Thread {
    AtomicInteger waterLevel;

    public Consumer(AtomicInteger waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void run() {
        while (true) {
            synchronized (this) {
                while (waterLevel.get() <= 10) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                waterLevel.decrementAndGet();

                System.out.println("Consumer consumes-" + waterLevel.get());

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