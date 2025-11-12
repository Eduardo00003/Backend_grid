# Territory Mapper – Technical Notes

## Goal
Demo application that lets users upload a CSV of companies (name/address/lat/lon), plot the points on an interactive map, generate a grid over the visible bounds, and count companies per grid cell. MVP stays lightweight: CSV upload → visualise points → build grid counts → simple export.

## Current Stack
- **Frontend (planned)**: React + Leaflet (or Mapbox GL) with optional turf.js helpers.
- **Backend**: Spring Boot 3.5.x using Web, JDBC, Jackson.
- **Database**: PostgreSQL 15 + PostGIS 3.3 (containerised via Docker Compose).

## Backend Requirements
- `POST /upload` – multipart CSV. Validate required columns (`name`, `lat`, `lon`), parse rows, batch insert into `company` table with `geom = ST_SetSRID(ST_MakePoint(lon, lat), 4326)::geography`.
- `GET /companies?bbox=minLon,minLat,maxLon,maxLat` – return GeoJSON FeatureCollection of companies within bounding box (use PostGIS `ST_MakeEnvelope` and `ST_Intersects`).
- `POST /grid?bbox=…&cellSizeMeters=…` – server generates square grid (project bbox to EPSG:3857 for sizing, `ST_SquareGrid` or generate_series fallback), counts points per cell, responds with GeoJSON polygons containing `company_cnt`.
- (Optional) `GET /export?cells=ids` – CSV of companies in selected cells.

## Database (Postgres + PostGIS)
Container: `postgis/postgis:15-3.3` exposed on `localhost:5432` with credentials `griduser/gridpass`, DB `grid_db`.

Schema:
```sql
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE company (
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT,
    address     TEXT,
    city        TEXT,
    state       TEXT,
    zip         TEXT,
    external_id TEXT,
    geom        GEOGRAPHY(POINT)
);

CREATE INDEX idx_company_geom ON company USING GIST (geom);
```

Seed data lives in `db/sample.csv`:
```
name,address,city,state,zip,lat,lon
Acme Co,123 Main St,Austin,TX,78701,30.2672,-97.7431
```

## Frontend Flow (React + Leaflet)
1. Upload CSV → POST `/upload`, display import summary (imported/skipped counts).
2. On map move/zoom, compute bbox → GET `/companies?bbox=…` → render markers (cluster or circles).
3. “Generate Grid” panel with cell-size dropdown (250m/500m/1km). POST `/grid?bbox=…&cellSizeMeters=…` → render polygon layer, colour by `company_cnt`, show popup listing companies/counts.
4. Optional select+export: multi-select cells → GET `/export?cells=…` to download CSV.

## Error / Edge Cases (MVP)
- Bad CSV row: skip, log line number, report summary.
- Missing lat/lon: skip row.
- Huge CSV: cap to reasonable limit (e.g., 10k rows) for demo.
- Bbox too large: ask user to zoom in.
- Cell size minimum (e.g., ≥100m) to avoid tiny grids.

## Open Tasks / Next Steps
- [ ] Configure Spring Boot datasource to point at Docker Postgres.
- [ ] Implement CSV parsing + validation + batch insert.
- [ ] Implement GeoJSON responses for `/companies` and `/grid`.
- [ ] Add frontend scaffold (React app, map component, upload panel).
- [ ] Add automated curl/Postman tests for API once endpoints exist.
