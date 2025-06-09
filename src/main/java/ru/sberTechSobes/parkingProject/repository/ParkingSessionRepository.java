package ru.sberTechSobes.parkingProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberTechSobes.parkingProject.entity.ParkingSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JPA-репозиторий для сущности ParkingSession.
 */
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    /**
     * Найти активную сессию по номеру автомобиля.
     * @param carNumber номер автомобиля
     * @return активная сессия или empty
     */
    Optional<ParkingSession> findByCarNumberAndExitTimeIsNull(String carNumber);

    /**
     * Получить все сессии по периоду въезда.
     * @param start начало периода
     * @param end конец периода
     * @return список сессий
     */
    List<ParkingSession> findAllByEntryTimeBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Количество машин на парковке.
     * @return количество активных сессий
     */
    long countByExitTimeIsNull();
}
