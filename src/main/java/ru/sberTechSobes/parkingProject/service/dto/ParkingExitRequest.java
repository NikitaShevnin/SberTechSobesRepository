package ru.sberTechSobes.parkingProject.service.dto;

/**
 * DTO для запроса регистрации выезда автомобиля с парковки.
 */
public class ParkingExitRequest {
    private String carNumber;

    public String getCarNumber() { return carNumber; }
    public void setCarNumber(String carNumber) { this.carNumber = carNumber; }
}
