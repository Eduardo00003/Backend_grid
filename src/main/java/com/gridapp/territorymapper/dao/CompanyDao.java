package com.gridapp.territorymapper.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDao {
  private static final String INSERT_SQL = """
      INSERT INTO company (
        name, address, city, state, zip, external_id, geom
      ) VALUES (
        ?, ?, ?, ?, ?, ?, ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography
      )
      """;

  private final JdbcTemplate jdbcTemplate;

  public CompanyDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int[] saveBatch(List<CompanyRecord> records) {
    if (records == null || records.isEmpty()) {
      return new int[0];
    }

    // Guard against bad inputs before hitting the DB
    for (CompanyRecord record : records) {
      if (record.lat() == null || record.lon() == null) {
        throw new IllegalArgumentException("Latitude and longitude are required for all records");
      }
    }

    return jdbcTemplate.batchUpdate(
        INSERT_SQL,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
            CompanyRecord record = records.get(i);
            ps.setString(1, record.name());
            ps.setString(2, record.address());
            ps.setString(3, record.city());
            ps.setString(4, record.state());
            ps.setString(5, record.zip());
            ps.setString(6, record.externalId());
            ps.setDouble(7, record.lon());
            ps.setDouble(8, record.lat());
          }

          @Override
          public int getBatchSize() {
            return records.size();
          }
        });
  }
}
