import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.Random;

public class Threadexample {
    public static void main(String[] args) throws InterruptedException {
        // Object of a class that has both produce() and consume() methods
        final PC pc = new PC();

        // Create producer thread
        Thread producer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create producer thread
        Thread producer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create consumer thread
        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create consumer thread
        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start producer threads first
        producer1.start();
        producer2.start();

        // Start consumer threads
        consumer1.start();
        consumer2.start();

        // Wait for both threads to finish
        producer1.join();
        producer2.join();
        consumer1.join();
        consumer2.join();
    }

    // This class has a list, producer (adds items to list) and consumer (removes items).
    public static class PC {
        // Water tank status
        double waterLevel = 0.00;
        // Maximum capacity of the water tank
        double capacity = 1.00;
        // Critical level of the water tank
        double criticalLevel = 0.40 * capacity; // 10% of capacity
        // Upper threshold of the water tank
        double upperThreshold = 0.60 * capacity; // 90% of capacity
        // Flag to indicate if water level is at or below critical level
        boolean isCritical = false;
        // Flag to indicate if water tank is full
        boolean isFull = false;
        Semaphore semaphore = new Semaphore(1);
        LinkedList<Double> waterTank = new LinkedList<>();

        // Function called by producer thread
        public void produce() throws InterruptedException {
            while (true) {
                synchronized (waterTank) {
                    // If water level is below critical level, wait until it's not critical
                    while (isFull) {
                        waterTank.wait();
                    }

                    // Increase water level by 0.01
                    waterLevel += 0.01;
                    waterTank.add(waterLevel);
                    System.out.println("Producing: water tank status: " + String.format("%.2f", waterLevel));
 
                    Random random = new Random();
                    if (waterLevel > criticalLevel) {
                        isCritical = false;
                        if (random.nextBoolean()) { 
                            waterTank.notifyAll();
                        }
                    }

                    // If water level is at or above 90%, start consuming
                    if (waterLevel > upperThreshold) {
                        isFull = true;
                        System.out.println("Warning, upper threshold reached!!");
                        waterTank.notifyAll();
                    } 

                    // Notify consumer thread
                    // waterTank.notifyAll();
                }

                // Makes the working of program easier to understand
                Thread.sleep(1000);
            }
        }

        // Function called by consumer thread
        public void consume() throws InterruptedException {
            while (true) {
                synchronized (waterTank) {
                    // If water level is at or above 90%, wait until it's not critical
                    while (isCritical) {
                        waterTank.wait();
                    }

                    // Wait while water tank is empty
                    while (waterTank.isEmpty()) {
                        waterTank.wait();
                    }

                    // Decrease water level by 0.01 if not empty
                    if (!waterTank.isEmpty()) {
                        waterLevel -= 0.01;
                        waterTank.removeFirst();
                        System.out.println("Consuming: water tank status: " + String.format("%.2f", waterLevel));
                        Random random2 = new Random(); 
                        if (random2.nextBoolean()) {
                            waterTank.notifyAll();
                        }
                    }

                    // If water level is at or below critical level, start producing
                    if (waterLevel <= criticalLevel) {
                        isCritical = true;
                        isFull = false;
                        System.out.println("Warning, water level is lower or equal to 10%, start producing water");
                        waterTank.notifyAll();
                    } 

                    // Notify producer thread
                    // waterTank.notifyAll();
                }

                // Sleep
                Thread.sleep(1000);
            }
        }
    }
}