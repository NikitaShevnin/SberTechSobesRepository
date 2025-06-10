package ru.sberTechSobes.parkingProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sberTechSobes.parkingProject.config.ParkingProperties;
import ru.sberTechSobes.parkingProject.entity.ParkingSession;
import ru.sberTechSobes.parkingProject.enumeration.VehicleType;
import ru.sberTechSobes.parkingProject.repository.ParkingSessionRepository;
import ru.sberTechSobes.parkingProject.service.dto.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервисного слоя управления парковкой.
 */
@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final int totalParkingPlaces;

    public ParkingServiceImpl(ParkingSessionRepository parkingSessionRepository,
                              ParkingProperties parkingProperties) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.totalParkingPlaces = parkingProperties.getTotalSpaces();
    }

    @Override
    @Transactional
    public ParkingEntryResponse registerEntry(ParkingEntryRequest request) {
        Optional<ParkingSession> openSession =
                parkingSessionRepository.findByCarNumberAndExitTimeIsNull(request.getCarNumber());
        if (openSession.isPresent()) {
            throw new IllegalStateException("Автомобиль уже на парковке.");
        }
        if (parkingSessionRepository.countByExitTimeIsNull() >= totalParkingPlaces) {
            throw new IllegalStateException("Парковка заполнена.");
        }
        VehicleType type = VehicleType.valueOf(request.getVehicleType().toUpperCase());
        LocalDateTime now = LocalDateTime.now();
        ParkingSession session = new ParkingSession();
        session.setCarNumber(request.getCarNumber());
        session.setVehicleType(type);
        session.setEntryTime(now);
        parkingSessionRepository.save(session);
        return new ParkingEntryResponse(request.getCarNumber(), type, now);
    }

    @Override
    @Transactional
    public ParkingExitResponse registerExit(ParkingExitRequest request) {
        ParkingSession session = parkingSessionRepository
                .findByCarNumberAndExitTimeIsNull(request.getCarNumber())
                .orElseThrow(() -> new IllegalStateException("Автомобиль не найден на парковке или уже выехал."));
        LocalDateTime now = LocalDateTime.now();
        session.setExitTime(now);
        parkingSessionRepository.save(session);
        return new ParkingExitResponse(request.getCarNumber(), now);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public ParkingReportResponse getReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<ParkingSession> sessions = parkingSessionRepository.findAllByEntryTimeBetween(startDate, endDate);

        long totalEntries = sessions.size();
        long totalExits = sessions.stream()
                .filter(s -> s.getExitTime() != null
                        && !s.getExitTime().isBefore(startDate)
                        && !s.getExitTime().isAfter(endDate))
                .count();

        double avgDuration = sessions.stream()
                .filter(s -> s.getExitTime() != null)
                .mapToLong(s -> Duration.between(s.getEntryTime(), s.getExitTime()).toMinutes())
                .average().orElse(0.0);

        long occupied = parkingSessionRepository.countByExitTimeIsNull();
        long free = totalParkingPlaces - occupied;

        return new ParkingReportResponse(totalEntries, totalExits, avgDuration, occupied, free);
    }
}
