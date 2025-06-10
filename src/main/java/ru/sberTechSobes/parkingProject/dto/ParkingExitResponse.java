package ru.sberTechSobes.parkingProject.dto;

import java.time.LocalDateTime;

public class ParkingExitResponse {
    private String vehicleNumber;
    private LocalDateTime exitTime;

    public ParkingExitResponse(String vehicleNumber, LocalDateTime exitTime) {
        this.vehicleNumber = vehicleNumber;
        this.exitTime = exitTime;
    }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
}
