package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContragentDtoAddress {

    private String postIndex;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String apartrmentNumber;

    @JsonCreator
    public ContragentDtoAddress(
            @JsonProperty("post_index") String postIndex,
            @JsonProperty("country") String country,
            @JsonProperty("city") String city,
            @JsonProperty("street") String street,
            @JsonProperty("house_number") String houseNumber,
            @JsonProperty("apartrment_number") String apartrmentNumber
    ) {
        this.postIndex = postIndex;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartrmentNumber = apartrmentNumber;
    }
}
