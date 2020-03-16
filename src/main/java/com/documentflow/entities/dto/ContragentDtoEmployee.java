package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContragentDtoEmployee {

    @JsonProperty("id")
    private String id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("position")
    private String personPosition;

    @JsonCreator
    public ContragentDtoEmployee(
            @JsonProperty("id") String id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("middle_name") String middleName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("position") String personPosition
    ) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.personPosition = personPosition;
    }
}
