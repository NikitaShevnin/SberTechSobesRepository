package ru.sberTechSobes.parkingProject.dto;

/**
 * DTO для запроса регистрации въезда автомобиля на парковку.
 */
import jakarta.validation.constraints.NotBlank;

public class ParkingEntryRequest {
    @NotBlank
    private String carNumber;

    @NotBlank
    private String vehicleType; // ENUM строкой

    public String getCarNumber() { return carNumber; }
    public void setCarNumber(String carNumber) { this.carNumber = carNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}
