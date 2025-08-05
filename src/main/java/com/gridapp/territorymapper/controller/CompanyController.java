package com.gridapp.territorymapper.controller;

import com.gridapp.territorymapper.model.Company;
import com.gridapp.territorymapper.repository.CompanyRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


// this will handle api request and return a json file
@RestController
// every method will start with this in the url to keep track where we are
@RequestMapping("/api/companies")
public class CompanyController {
  // tool used to talk to database
  private final CompanyRepository companyRepository;

  public CompanyController(CompanyRepository companyRepository){
    this.companyRepository = companyRepository;
  }

  // hadles POST request (sending us data)
  @PostMapping
  public Company addCompany(@RequestBody Company company){
    return companyRepository.save(company);
  }
  
  
  
}
