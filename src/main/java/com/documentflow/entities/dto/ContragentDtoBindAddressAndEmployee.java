package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContragentDtoBindAddressAndEmployee {

    private ContragentDtoAddress address;
    private ContragentDtoEmployee employee;
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
