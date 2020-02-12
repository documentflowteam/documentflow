package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContragentDtoParameters {

    private String firstName;
    private String middleName;
    private String lastName;
    private String nameCompany;

    @JsonCreator
    public ContragentDtoParameters(
            @JsonProperty("first_name") String firstName,
            @JsonProperty("middle_name") String middleName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("name_company") String nameCompany
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameCompany = nameCompany;
    }
}
