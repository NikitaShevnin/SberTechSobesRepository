package ru.sberTechSobes.parkingProject.entity;

import jakarta.persistence.*;
import ru.sberTechSobes.parkingProject.enumeration.VehicleType;

import java.time.LocalDateTime;

/**
 * Сущность, описывающая сессию пребывания автомобиля на парковке.
 */
@Entity
@Table(name = "parking_sessions")
public class ParkingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    public ParkingSession() {}

    public ParkingSession(Long id, String carNumber, VehicleType vehicleType,
                          LocalDateTime entryTime, LocalDateTime exitTime) {
        this.id = id;
        this.carNumber = carNumber;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCarNumber() { return carNumber; }
    public void setCarNumber(String carNumber) { this.carNumber = carNumber; }

    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }

    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
}
