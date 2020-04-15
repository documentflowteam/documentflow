package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContragentDtoParameters {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("name_company")
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
