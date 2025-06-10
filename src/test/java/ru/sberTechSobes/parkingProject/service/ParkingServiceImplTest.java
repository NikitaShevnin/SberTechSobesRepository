package ru.sberTechSobes.parkingProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.sberTechSobes.parkingProject.entity.ParkingSession;
import ru.sberTechSobes.parkingProject.enumeration.VehicleType;
import ru.sberTechSobes.parkingProject.config.ParkingProperties;
import ru.sberTechSobes.parkingProject.repository.ParkingSessionRepository;
import ru.sberTechSobes.parkingProject.service.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingServiceImplTest {

    @Mock
    private ParkingSessionRepository repository;

    private ParkingServiceImpl service;

    @BeforeEach
    void setUp() {
        ParkingProperties properties = new ParkingProperties();
        properties.setTotalSpaces(100);
        service = new ParkingServiceImpl(repository, properties);
    }

    @Test
    void registerEntryCreatesSession() {
        ParkingEntryRequest request = new ParkingEntryRequest();
        request.setCarNumber("A123AA");
        request.setVehicleType("sedan");

        when(repository.findByCarNumberAndExitTimeIsNull("A123AA"))
                .thenReturn(Optional.empty());
        ArgumentCaptor<ParkingSession> captor = ArgumentCaptor.forClass(ParkingSession.class);

        ParkingEntryResponse response = service.registerEntry(request);

        verify(repository).save(captor.capture());
        ParkingSession saved = captor.getValue();

        assertEquals("A123AA", saved.getCarNumber());
        assertEquals(VehicleType.SEDAN, saved.getVehicleType());
        assertNotNull(saved.getEntryTime());
        assertEquals(saved.getEntryTime(), response.getEntryTime());
        assertEquals("A123AA", response.getVehicleNumber());
        assertEquals(VehicleType.SEDAN, response.getVehicleType());
    }

    @Test
    void registerEntryThrowsWhenAlreadyOnParking() {
        ParkingEntryRequest request = new ParkingEntryRequest();
        request.setCarNumber("B321BB");
        request.setVehicleType("SUV");

        when(repository.findByCarNumberAndExitTimeIsNull("B321BB"))
                .thenReturn(Optional.of(new ParkingSession()));

        assertThrows(IllegalStateException.class, () -> service.registerEntry(request));
    }

    @Test
    void registerExitUpdatesSession() {
        ParkingExitRequest request = new ParkingExitRequest();
        request.setCarNumber("C456CC");
        ParkingSession session = new ParkingSession();
        session.setCarNumber("C456CC");
        session.setEntryTime(LocalDateTime.now().minusHours(1));

        when(repository.findByCarNumberAndExitTimeIsNull("C456CC"))
                .thenReturn(Optional.of(session));

        ArgumentCaptor<ParkingSession> captor = ArgumentCaptor.forClass(ParkingSession.class);

        ParkingExitResponse response = service.registerExit(request);

        verify(repository).save(captor.capture());
        ParkingSession saved = captor.getValue();
        assertNotNull(saved.getExitTime());
        assertEquals(saved.getExitTime(), response.getExitTime());
        assertEquals("C456CC", response.getVehicleNumber());
    }

    @Test
    void registerExitThrowsWhenNotFound() {
        ParkingExitRequest request = new ParkingExitRequest();
        request.setCarNumber("Z999ZZ");

        when(repository.findByCarNumberAndExitTimeIsNull("Z999ZZ"))
                .thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> service.registerExit(request));
    }

    @Test
    void getReportReturnsStatistics() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = start.plusDays(1);

        ParkingSession s1 = new ParkingSession(1L, "A", VehicleType.SEDAN,
                start.plusHours(1), start.plusHours(2));
        ParkingSession s2 = new ParkingSession(2L, "B", VehicleType.SUV,
                start.plusHours(3), start.plusHours(3).plusMinutes(30));
        ParkingSession s3 = new ParkingSession(3L, "C", VehicleType.TRUCK,
                start.plusHours(4), null);

        when(repository.findAllByEntryTimeBetween(start, end))
                .thenReturn(List.of(s1, s2, s3));
        when(repository.countByExitTimeIsNull()).thenReturn(5L);

        ParkingReportResponse report = service.getReport(start, end);

        assertEquals(3L, report.getEntries());
        assertEquals(2L, report.getExits());
        assertEquals(45.0, report.getAvgDurationMinutes(), 0.1);
        assertEquals(5L, report.getOccupiedPlaces());
        assertEquals(95L, report.getFreePlaces());
    }
}
