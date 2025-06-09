package ru.sberTechSobes.parkingProject.service.dto;

public class ParkingReportResponse {
    private long entries;
    private long exits;
    private double avgDurationMinutes;
    private long occupiedPlaces;
    private long freePlaces;

    public ParkingReportResponse(long entries, long exits, double avgDurationMinutes, long occupiedPlaces, long freePlaces) {
        this.entries = entries;
        this.exits = exits;
        this.avgDurationMinutes = avgDurationMinutes;
        this.occupiedPlaces = occupiedPlaces;
        this.freePlaces = freePlaces;
    }

    public long getEntries() { return entries; }
    public void setEntries(long entries) { this.entries = entries; }

    public long getExits() { return exits; }
    public void setExits(long exits) { this.exits = exits; }

    public double getAvgDurationMinutes() { return avgDurationMinutes; }
    public void setAvgDurationMinutes(double avgDurationMinutes) { this.avgDurationMinutes = avgDurationMinutes; }

    public long getOccupiedPlaces() { return occupiedPlaces; }
    public void setOccupiedPlaces(long occupiedPlaces) { this.occupiedPlaces = occupiedPlaces; }

    public long getFreePlaces() { return freePlaces; }
    public void setFreePlaces(long freePlaces) { this.freePlaces = freePlaces; }
}
