package com.gridapp.territorymapper.repository;

import com.gridapp.territorymapper.model.Company;
// this imports methods so it interacts with data base without needing sql
import org.springframework.data.jpa.repository.JpaRepository;


// works withthe company entities and their IDs are type Long
public interface CompanyRepository extends JpaRepository<Company,Long> {

}
