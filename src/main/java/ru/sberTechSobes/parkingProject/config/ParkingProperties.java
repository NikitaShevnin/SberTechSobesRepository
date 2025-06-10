package ru.sberTechSobes.parkingProject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for parking settings.
 */
@Component
@ConfigurationProperties(prefix = "parking")
public class ParkingProperties {

    /**
     * Total number of parking spaces available.
     */
    private int totalSpaces = 100;

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public void setTotalSpaces(int totalSpaces) {
        this.totalSpaces = totalSpaces;
    }
}
