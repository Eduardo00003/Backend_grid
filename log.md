# Project Log

## 2025-01-11
- Brought up Postgres + PostGIS locally via `docker compose up -d db`; verified container (`grid_postgres`) healthy in Docker Desktop/VS Code.
- Connected with `docker compose exec db psql -U griduser -d grid_db` and enabled PostGIS, created `company` table plus `idx_company_geom` GIST index.
- Added `db/sample.csv` placeholder to exercise upcoming `/upload` flow (single Austin record).
- Documented architecture, backend endpoints, and outstanding tasks in `TECHNICAL.md` so future sessions have persistent context.

## 2025-01-12
- Troubleshot Postgres container startup: learned `docker compose down -v` resets the DB volume when env vars (e.g., `POSTGRES_PASSWORD`) were missing, clarified safe cleanup of extra `postgis/postgis` images/containers, and confirmed only the compose-managed `grid_postgres` should be running.
- Scoped backend next steps (no JPA): `CompanyDao` must use `JdbcTemplate` batch inserts with `ST_SetSRID(ST_MakePoint(lon, lat), 4326)::geography`, controller needs multipart `/upload`, and CSV parsing service will feed the DAO.
- Began stubbing `CompanyDao` but still need to implement the real batch insert (define record DTO, use `batchUpdate`, insert into `company` table).

## 2025-11-15
- Added commons-csv dependency (prep for CSV ingestion service) and removed unused JPA properties from `src/main/resources/application.properties`.
- Implemented `CompanyDao.saveBatch` with `JdbcTemplate.batchUpdate` targeting the `company` table using PostGIS geography points; added input validation for lat/lon.
- Introduced `CompanyRecord` DTO (record) matching DB columns for batch insert.
- Next up: create `CsvImportService` to parse incoming CSV rows into `CompanyRecord` instances and wire it to a controller endpoint (multipart upload).
