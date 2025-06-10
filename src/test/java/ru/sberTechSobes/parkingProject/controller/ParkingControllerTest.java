package ru.sberTechSobes.parkingProject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.sberTechSobes.parkingProject.enumeration.VehicleType;
import ru.sberTechSobes.parkingProject.service.ParkingService;
import ru.sberTechSobes.parkingProject.service.dto.*;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkingController.class)
@Import(ru.sberTechSobes.parkingProject.aop.ServiceExceptionAspect.class)
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingService parkingService;

    @Test
    void registerEntryReturnsCreated() throws Exception {
        ParkingEntryResponse resp = new ParkingEntryResponse("A", VehicleType.SEDAN, LocalDateTime.now());
        when(parkingService.registerEntry(any())).thenReturn(resp);

        mockMvc.perform(post("/api/v1/parking/entry")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"carNumber\":\"A\",\"vehicleType\":\"SEDAN\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleNumber").value("A"))
                .andExpect(jsonPath("$.vehicleType").value("SEDAN"));
    }

    @Test
    void registerExitReturnsOk() throws Exception {
        ParkingExitResponse resp = new ParkingExitResponse("A", LocalDateTime.now());
        when(parkingService.registerExit(any())).thenReturn(resp);

        mockMvc.perform(post("/api/v1/parking/exit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"carNumber\":\"A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value("A"));
    }

    @Test
    void getReportReturnsOk() throws Exception {
        ParkingReportResponse resp = new ParkingReportResponse(1, 1, 10.0, 1, 99);
        when(parkingService.getReport(any(), any())).thenReturn(resp);

        mockMvc.perform(get("/api/v1/parking/report")
                .param("start_date", "2020-01-01T00:00:00")
                .param("end_date", "2020-01-02T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entries").value(1));
    }

    @Test
    void handleExceptionReturnsBadRequest() throws Exception {
        when(parkingService.registerEntry(any()))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST, "err"));

        mockMvc.perform(post("/api/v1/parking/entry")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"carNumber\":\"A\",\"vehicleType\":\"SEDAN\"}"))
                .andExpect(status().isBadRequest());
    }
}
