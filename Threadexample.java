// Java program to implement solution of producer
// consumer problem.
import java.util.LinkedList;
 
public class Threadexample {
    public static void main(String[] args)
        throws InterruptedException
    {
        // Object of a class that has both produce()
        // and consume() methods
        final PC pc = new PC();
 
        // Create producer1 thread
        Thread producer1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
 
        // Create producer2 thread
        Thread producer2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create consumer1 thread
        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.consume();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
 
        // Create consumer2 thread
        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.consume();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
 
        // t1 finishes before t2
        producer1.join();
        producer2.join();
        consumer1.join();
        consumer2.join();
    }
 
    // This class has a list, producer (adds items to list)
    // and consumer (removes items).
    public static class PC {

        // Water tank status
        double waterLevel = 0.00;
        // Maximum capacity of the water tank
        double capacity = 2.00;
        // Critical level of the water tank
        double criticalLevel = 0.10 * capacity; // 10% of capacity
        // Flag to indicate if water level is at or below critical level
        boolean isCritical = false;
        // Create a list shared by producer and consumer
        // The list doesn't directly represent the water tank's volume but is used for synchronization.
        LinkedList<Integer> list = new LinkedList<>();
        int listCapacity = 10; // Arbitrary capacity for the list to demonstrate synchronization

        // Function called by producer thread
        public void produce() throws InterruptedException {
            int value = 0;
            while (true) {
                synchronized (this) {
                    // Wait while list is full
                    while (list.size() == listCapacity)
                        wait();

                    // Simulate adding an operation to the list (not directly related to water volume)
                    list.add(value++);

                    // Increase water level by 0.01 if not full
                    if (waterLevel < capacity) {
                        waterLevel += 0.01;
                        System.out.println("Producing: water tank status: " + waterLevel);
                    }

                    // If water level is above critical level, set isCritical to false
                    if (waterLevel > criticalLevel) {
                        isCritical = false;
                    }

                    // Notifies the consumer thread that now it can start consuming
                    notify();

                    // Makes the working of program easier to understand
                    Thread.sleep(1000);
                }
            }
        }

        // Function called by consumer thread
        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    // If water level is at or below critical level, return immediately
                    if (isCritical) {
                        return;
                    }

                    // Wait while list is empty
                    while (list.size() == 0)
                        wait();

                    // Simulate removing an operation from the list
                    list.removeFirst();

                    // Decrease water level by 0.01 if not empty
                    if (waterLevel > 0) {
                        waterLevel -= 0.01;
                        System.out.println("Consuming: water tank status: " + waterLevel);
                    }

                    // If water level is at or below critical level, set isCritical to true and print warning
                    if (waterLevel <= criticalLevel) {
                        isCritical = true;
                        System.out.println("Warning, water level is lower or equal than 10%");
                    }

                    // Wake up producer thread
                    notify();

                    // Sleep
                    Thread.sleep(1000);
                }
            }
        }
    }
}