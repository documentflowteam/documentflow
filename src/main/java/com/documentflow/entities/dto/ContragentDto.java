package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContragentDto {

    @JsonProperty("type_person")
    private String typePerson;
    @JsonProperty("parameters")
    private ContragentDtoParameters parameters;
    @JsonProperty("addresses")
    private ContragentDtoAddress[] address;
    @JsonProperty("employees")
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
