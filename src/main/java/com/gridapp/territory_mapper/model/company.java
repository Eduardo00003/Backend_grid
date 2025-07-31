package com.gridapp.territory_mapper.model;

import jakarta.persistence.*;
import lombok.*;


//annotations
@Entity //Declares this class as a database entity
@Table(name = "companies") //Maps it to a table called "companies
@Data //Auto-generates getters, setters, toString(),
@NoArgsConstructor // Adds a no-argument constructor
@AllArgsConstructor //Adds a constructor with all fields
@Builder //Allows you to use the builder pattern

public class company {
  // this is the primary key and it is generating a number to store it
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String companyName;
    private String name;
    private String address;
    private String city;
    private String state;



}
