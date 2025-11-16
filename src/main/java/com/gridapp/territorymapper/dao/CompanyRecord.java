package com.gridapp.territorymapper.dao;

public record CompanyRecord(
    String name,
    String address,
    String city,
    String state,
    String zip,
    Double lat,
    Double lon,
    String externalId
) {}
