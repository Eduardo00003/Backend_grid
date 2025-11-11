//make it into a plain DTO
package com.gridapp.territorymapper.model;


import lombok.*;


//annotations
@Data //Auto-generates getters, setters, toString(),
@NoArgsConstructor // Adds a no-argument constructor
@AllArgsConstructor //Adds a constructor with all fields
@Builder //Allows you to use the builder pattern

public class Company {
  // this is the primary key and it is generating a number to store it
    private Long id;

    private String name;

    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String county;
    private String phone;
    // Stores the number RID from the csv
    private Long externalId;

    private String location;
    





}
