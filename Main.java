import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    AtomicInteger capacity;

    public static void main(String[] args) throws InterruptedException {

        // waterLevel doit etre mis à la vameur 100
        AtomicInteger waterLevel = new AtomicInteger(50);
        // Create a new water tank with a water level of 100
        Tank tank = new Tank(waterLevel);
        Producer producer = new Producer(tank.getWaterLevel());
        Consumer consumer = new Consumer(tank.getWaterLevel());

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}