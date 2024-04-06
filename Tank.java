import java.util.concurrent.atomic.AtomicInteger;

public class Tank {
    // Water tank status
    AtomicInteger waterLevel = new AtomicInteger(0);
    // Maximum capacity of the water tank
    int capacity = 100;
    // Flag to indicate if water level is at or below critical level
    boolean isCriticalHigh = false;
    boolean isCriticalLow = true;

    public Tank(AtomicInteger waterLevel) {
        this.waterLevel = waterLevel;
        if (waterLevel.get() >= 90) {
            isCriticalHigh = true;
            isCriticalLow = false;
        } else {
            isCriticalHigh = false;
            isCriticalLow = true;
        }
    }

    public AtomicInteger getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(AtomicInteger waterLevel) {
        this.waterLevel = waterLevel;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean getIsCriticalHigh() {
        return isCriticalHigh;
    }

    public boolean getIsCriticalLow() {
        return isCriticalLow;
    }

    public void setIsCriticalHigh(AtomicInteger waterLevel) {
        if (waterLevel.get() >= 90) {
            isCriticalHigh = true;
            isCriticalLow = false;

        }
    }

    public void setIsCriticalLow(AtomicInteger waterLevel) {
        if (waterLevel.get() <= 10) {
            isCriticalHigh = false;
            isCriticalLow = true;

        }
    }
}
