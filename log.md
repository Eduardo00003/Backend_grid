# Project Log

## 2025-01-11
- Brought up Postgres + PostGIS locally via `docker compose up -d db`; verified container (`grid_postgres`) healthy in Docker Desktop/VS Code.
- Connected with `docker compose exec db psql -U griduser -d grid_db` and enabled PostGIS, created `company` table plus `idx_company_geom` GIST index.
- Added `db/sample.csv` placeholder to exercise upcoming `/upload` flow (single Austin record).
- Documented architecture, backend endpoints, and outstanding tasks in `TECHNICAL.md` so future sessions have persistent context.
