package com.documentflow.utils;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@UtilityClass
public class ContragentUtils {

    public static boolean isNotEmpty(@NonNull Address address) {
        return !(
                (address.getCountry() == null || address.getCountry().trim().isEmpty()) ||
                        (address.getCity() == null || address.getCity().trim().isEmpty()) ||
                        (address.getStreet() == null || address.getStreet().trim().isEmpty())
        );
    }

    public static boolean isEmpty(@NonNull ContragentDtoParameters parameters) {
        return ((parameters.getLastName() == null || parameters.getLastName().trim().isEmpty()) &&
                (parameters.getNameCompany() == null || parameters.getNameCompany().trim().isEmpty()));
    }

    public static boolean isEmpty(ContragentDtoAddress[] addresses) {
        return addresses.length == 0 || Arrays.stream(addresses).anyMatch(address -> {
           return  ((address.getCountry() == null || address.getCountry().trim().isEmpty()) ||
                    (address.getCity() == null || address.getCity().trim().isEmpty()) ||
                    (address.getStreet() == null || address.getStreet().trim().isEmpty()));
        });
    }

    public static boolean isNotEmpty(@NonNull ContragentDtoEmployee employee) {
        return !(
                (employee.getMiddleName() == null || employee.getMiddleName().trim().isEmpty()) &&
                        (employee.getLastName() == null || employee.getLastName().trim().isEmpty())
        );
    }

    public static String createSearchName(@NonNull String... values) {
        return Arrays.stream(values)
                .map(item -> item = item.replace(" ", ""))
                .filter(StringUtils::isNotEmpty)
                .map(item -> item = item.toUpperCase())
                .collect(Collectors.joining());
    }

    public static Address normalizeAddress(Address address) {

        address.setCountry(address.getCountry().toUpperCase());
        address.setCity(address.getCity().toUpperCase());
        address.setStreet(address.getStreet().toUpperCase());

        return address;
    }

    public static String toUpperCase(String string) {
        return string != null ? string.toUpperCase() : null;
    }

    public static ContragentDtoAddress[] deleteDuplicate(ContragentDtoAddress[] addresses){
        return Arrays.stream(addresses)
                .distinct()
                .collect(Collectors.toList())
                .toArray(new ContragentDtoAddress[]{});
    }

    public static ContragentDtoEmployee[] deleteDuplicate(ContragentDtoEmployee[] addresses){
        return Arrays.stream(addresses)
                .distinct()
                .collect(Collectors.toList())
                .toArray(new ContragentDtoEmployee[]{});
    }
}
