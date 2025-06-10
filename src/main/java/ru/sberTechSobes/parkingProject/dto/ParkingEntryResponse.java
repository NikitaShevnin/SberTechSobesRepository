package ru.sberTechSobes.parkingProject.dto;

import ru.sberTechSobes.parkingProject.enumeration.VehicleType;

import java.time.LocalDateTime;

public class ParkingEntryResponse {
    private String vehicleNumber;
    private VehicleType vehicleType;
    private LocalDateTime entryTime;

    public ParkingEntryResponse(String vehicleNumber, VehicleType vehicleType, LocalDateTime entryTime) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
    }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }
}
