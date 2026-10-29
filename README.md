# HON Spring Boot App

Simple educational full-stack app: Spring Boot backend, React frontend, PostgreSQL in Docker, pgAdmin.

## Prerequisites

- Java 21
- Maven
- Node.js / npm
- Docker

## Project structure

```
project-root/
├── backend/
├── frontend/
├── docker-compose.yml
├── .env.example
└── README.md
```

## Start PostgreSQL and pgAdmin

```bash
docker compose up -d
docker compose down   # stop without deleting data
```

pgAdmin: http://localhost:5050  
Login: admin@example.com / admin  

Register server:
- Name: Local PostgreSQL
- Host: postgres
- Port: 5432
- Maintenance DB: postgres
- Username: postgres
- Password: postgres

## Run backend

```bash
cd backend
mvn spring-boot:run
```

Backend runs on http://localhost:8080.

## Run frontend

```bash
cd frontend
npm start
```

Frontend runs on http://localhost:3000. (Uses axios for API calls.)

## Database relationship

- `primarytable` (PrimaryEntity) one-to-many `foreigntable` (ForeignEntity).
- Foreign key: `foreigntable.primary_uid` -> `primarytable.uid`.
- Cascade: `CascadeType.ALL` + `orphanRemoval = true`.

## REST endpoints (6)

- `GET /api/primary`
- `GET /api/primary/{uid}`
- `POST /api/primary`
- `PATCH /api/primary/{uid}`
- `PUT /api/primary/{uid}`
- `DELETE /api/primary/{uid}`

Example POST body:

```json
{
  "name": "Sample Record",
  "description": "Spring Boot and React example",
  "age": 22,
  "score": 85.5,
  "active": true,
  "gender": "Other",
  "birthDate": "2004-01-15",
  "reminderTime": "14:30:00",
  "eventDateTime": "2026-10-29T15:30:00",
  "tags": ["Java", "React", "PostgreSQL"],
  "priority": 7,
  "status": "ACTIVE",
  "country": "India",
  "foreignRecords": [
    { "value": "Child one", "quantity": 10, "active": true },
    { "value": "Child two", "quantity": 20, "active": false }
  ]
}
```

PUT replaces editable fields and reconciles children (update existing by uid, create new without uid, delete omitted). PATCH updates only supplied fields.

## Query examples

- Derived: `findByNameContainingIgnoreCase`, `findByActive`, `findByStatus`, `findByAgeGreaterThanEqual`
- JPQL: `findByAgeAndActive(age, active)`
- Named query: `PrimaryEntity.findByStatusNamed`
- Native SQL: `findByCountryNative(country)`

Optional GET filters: `?name=`, `?active=`, `?status=`, `?age=`, `?country=`.

## Tests

```bash
cd backend
mvn test
```

Unit tests only (Mockito, no database required).

## Frontend build

```bash
cd frontend
npm run build
```

## Troubleshooting

- Port 5432 in use: stop local PostgreSQL or change DB_URL.
- Port 8080/3000 in use: stop conflicting process.
- Database connection: verify Docker container healthy and credentials.
- CORS: frontend origin http://localhost:3000 is allowed.
