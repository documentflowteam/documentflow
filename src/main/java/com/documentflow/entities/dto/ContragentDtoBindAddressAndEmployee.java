package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContragentDtoBindAddressAndEmployee {

    @JsonProperty("address")
    private ContragentDtoAddress address;
    @JsonProperty("employee")
    private ContragentDtoEmployee employee;
    @JsonProperty("id")
    private Long id;

    @JsonCreator
    public ContragentDtoBindAddressAndEmployee(
            @JsonProperty("id") Long id,
            @JsonProperty("address") ContragentDtoAddress address,
            @JsonProperty("employee") ContragentDtoEmployee employee
    ) {
        this.id = id;
        this.address = address;
        this.employee = employee;
    }
}
