package com.gridapp.territorymapper.model;

import jakarta.persistence.*;
import lombok.*;


//annotations
@Entity //Declares this class as a database entity
@Table(name = "companies") //Maps it to a table called "companies
@Data //Auto-generates getters, setters, toString(),
@NoArgsConstructor // Adds a no-argument constructor
@AllArgsConstructor //Adds a constructor with all fields
@Builder //Allows you to use the builder pattern

public class Company {
  // this is the primary key and it is generating a number to store it
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String county;
    private String phone;
    // Stores the number RID from the csv
    @Column(name = "external_id")
    private Long externalId;

    @Column(columnDefinition = "geometry(Point,4326)")
    private String location;
    





}
