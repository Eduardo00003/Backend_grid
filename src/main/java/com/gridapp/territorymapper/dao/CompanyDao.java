package com.gridapp.territorymapper.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDao {
  private final JdbcTemplate jdbcTemplate;

  public CompanyDao(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveBatch(List<CompanyRecords>records){
    // use bathUpdate for inserting in bulk
    jdbcTemplate.update("INSERT INTO my_table (data_column) VALUES (?)", records);
  }
}
