package ru.sberTechSobes.parkingProject.service.dto;

/**
 * DTO для запроса регистрации въезда автомобиля на парковку.
 */
public class ParkingEntryRequest {
    private String carNumber;
    private String vehicleType; // ENUM строкой

    public String getCarNumber() { return carNumber; }
    public void setCarNumber(String carNumber) { this.carNumber = carNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}
