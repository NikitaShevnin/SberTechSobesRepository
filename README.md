# Parking Project

This microservice manages a parking lot and provides REST endpoints for vehicle entry, exit and statistics reporting.

## Building and running

```bash
mvn spring-boot:run
```

The service expects a PostgreSQL instance configured via `application.properties`.

## REST API (v1)

- `POST /api/v1/parking/entry` – register vehicle entry.
- `POST /api/v1/parking/exit` – register vehicle exit.
- `GET  /api/v1/parking/report?start_date=...&end_date=...` – retrieve parking statistics.

## Configuration

- `parking.total-spaces` – total available parking places (default `100`).

