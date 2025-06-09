package ru.sberTechSobes.parkingProject.service;

import ru.sberTechSobes.parkingProject.service.dto.ParkingEntryRequest;
import ru.sberTechSobes.parkingProject.service.dto.ParkingEntryResponse;
import ru.sberTechSobes.parkingProject.service.dto.ParkingExitRequest;
import ru.sberTechSobes.parkingProject.service.dto.ParkingExitResponse;
import ru.sberTechSobes.parkingProject.service.dto.ParkingReportResponse;

import java.time.LocalDateTime;

/**
 * Интерфейс сервисного слоя для управления парковкой.
 */
public interface ParkingService {

    /**
     * Регистрирует въезд автомобиля на парковку.
     *
     * @param request объект с номером и типом автомобиля
     * @return объект с подтверждением и временем въезда
     */
    ParkingEntryResponse registerEntry(ParkingEntryRequest request);

    /**
     * Регистрирует выезд автомобиля с парковки.
     *
     * @param request объект с номером автомобиля
     * @return объект с подтверждением и временем выезда
     */
    ParkingExitResponse registerExit(ParkingExitRequest request);

    /**
     * Формирует отчет по парковке за указанный период.
     *
     * @param startDate дата и время начала периода
     * @param endDate   дата и время окончания периода
     * @return объект с аналитикой по парковке за период
     */
    ParkingReportResponse getReport(LocalDateTime startDate, LocalDateTime endDate);

}
