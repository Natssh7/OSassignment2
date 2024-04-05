import java.util.LinkedList;

public class Tank {
    // Water tank status
    double waterLevel = 0.00;
    // Maximum capacity of the water tank
    double capacity = 100.00;
    // Critical level of the water tank
    double criticalLevel = 0.1 * capacity; // 10% of capacity
    // Flag to indicate if water level is at or below critical level
    boolean isCritical = true;
    // Create a list shared by producer and consumer
    // The list doesn't directly represent the water tank's volume but is used for
    // synchronization.
    LinkedList<Integer> list = new LinkedList<>();
    int listCapacity = 10; // Arbitrary capacity for the list to demonstrate synchronization;

    public Tank(double waterLevel) {
        this.waterLevel = waterLevel;
        if (waterLevel > criticalLevel) {
            isCritical = false;
        } else {
            isCritical = true;
        }
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getCriticalLevel() {
        return criticalLevel;
    }

    public boolean getIsCritical() {
        return isCritical;
    }

    public void setIsCritical(double waterLevel) {
        if (waterLevel > criticalLevel) {
            isCritical = false;
        } else {
            isCritical = true;
        }
    }
}
