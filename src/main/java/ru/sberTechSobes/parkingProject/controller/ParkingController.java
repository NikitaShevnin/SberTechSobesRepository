package ru.sberTechSobes.parkingProject.controller;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberTechSobes.parkingProject.dto.*;
import ru.sberTechSobes.parkingProject.service.ParkingService;

import java.time.LocalDateTime;

/**
 * REST-контроллер для управления парковкой.
 * Версия API: v1.
 */
@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

    /**
     * Конструктор ParkingController.
     *
     * @param parkingService сервис управления парковкой
     */
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    /**
     * Эндпоинт для регистрации въезда автомобиля на парковку.
     *
     * @param request DTO с номером и типом автомобиля
     * @return DTO с подтверждением и временем въезда
     */
    @PostMapping("/entry")
    public ResponseEntity<ParkingEntryResponse> registerEntry(
            @Valid @RequestBody ParkingEntryRequest request) {
        ParkingEntryResponse response = parkingService.registerEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Эндпоинт для регистрации выезда автомобиля с парковки.
     *
     * @param request DTO с номером автомобиля
     * @return DTO с подтверждением и временем выезда
     */
    @PostMapping("/exit")
    public ResponseEntity<ParkingExitResponse> registerExit(
            @Valid @RequestBody ParkingExitRequest request) {
        ParkingExitResponse response = parkingService.registerExit(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Эндпоинт для получения отчета по парковке за период.
     *
     * @param startDate дата и время начала периода (формат: yyyy-MM-dd'T'HH:mm)
     * @param endDate   дата и время окончания периода (формат: yyyy-MM-dd'T'HH:mm)
     * @return DTO с аналитикой по парковке
     */
    @GetMapping("/report")
    public ResponseEntity<ParkingReportResponse> getReport(
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        ParkingReportResponse response = parkingService.getReport(startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
