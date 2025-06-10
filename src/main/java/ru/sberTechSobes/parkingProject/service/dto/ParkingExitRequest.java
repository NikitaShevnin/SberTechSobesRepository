package ru.sberTechSobes.parkingProject.service.dto;

/**
 * DTO для запроса регистрации выезда автомобиля с парковки.
 */
import jakarta.validation.constraints.NotBlank;

public class ParkingExitRequest {
    @NotBlank
    private String carNumber;

    public String getCarNumber() { return carNumber; }
    public void setCarNumber(String carNumber) { this.carNumber = carNumber; }
}
