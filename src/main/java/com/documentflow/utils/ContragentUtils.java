package com.documentflow.utils;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

    public static boolean isNotEmpty(@NonNull ContragentDtoEmployee employee) {
        return !(
                (employee.getMiddleName() == null || employee.getMiddleName().trim().isEmpty()) &&
                        (employee.getLastName() == null || employee.getLastName().trim().isEmpty())
        );
    }
}
