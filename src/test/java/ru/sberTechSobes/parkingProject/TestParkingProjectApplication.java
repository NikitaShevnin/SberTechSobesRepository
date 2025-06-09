package ru.sberTechSobes.parkingProject;

import org.springframework.boot.SpringApplication;

public class TestParkingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.from(ParkingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
