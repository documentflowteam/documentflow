package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContragentDtoEmployee {

    private String firstName;
    private String middleName;
    private String lastName;
    private String personPosition;

    @JsonCreator
    public ContragentDtoEmployee(
            @JsonProperty("first_name") String firstName,
            @JsonProperty("middle_name") String middleName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("person_position") String personPosition
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.personPosition = personPosition;
    }
}
