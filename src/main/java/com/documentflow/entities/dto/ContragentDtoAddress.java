package com.documentflow.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ContragentDtoAddress {

    @JsonProperty("id")
    private String id;
    @JsonProperty("post_index")
    private String postIndex;
    @JsonProperty("country")
    private String country;
    @JsonProperty("city")
    private String city;
    @JsonProperty("street")
    private String street;
    @JsonProperty("house_number")
    private String houseNumber;
    @JsonProperty("apartrment_number")
    private String apartrmentNumber;

    @JsonCreator
    public ContragentDtoAddress(
            @JsonProperty("id") String id,
            @JsonProperty("post_index") String postIndex,
            @JsonProperty("country") String country,
            @JsonProperty("city") String city,
            @JsonProperty("street") String street,
            @JsonProperty("house_number") String houseNumber,
            @JsonProperty("apartrment_number") String apartrmentNumber
    ) {
        this.id = id;
        this.postIndex = postIndex;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartrmentNumber = apartrmentNumber;
    }
}
