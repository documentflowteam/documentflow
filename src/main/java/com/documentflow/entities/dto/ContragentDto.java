package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContragentDto {

    private String typePerson;
    private ContragentDtoParameters parameters;
    private ContragentDtoAddress[] address;
    private ContragentDtoEmployee[] employee;

    @JsonCreator
    public ContragentDto(
            @JsonProperty("type_person") String typePerson,
            @JsonProperty("parameters") ContragentDtoParameters parameters,
            @JsonProperty("addresses") ContragentDtoAddress[] address,
            @JsonProperty("employees") ContragentDtoEmployee[] employee
    ) {
        this.typePerson = typePerson;
        this.parameters = parameters;
        this.address = address;
        this.employee = employee;
    }
}
