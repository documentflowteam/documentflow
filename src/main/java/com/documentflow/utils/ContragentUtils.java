package com.documentflow.utils;

import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDtoAddress;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContragentUtils {

    public static boolean isNotEmpty(Address address) {
        return !(address.getIndex() <= 0 &&
                address.getCountry().isEmpty() &&
                address.getCity().isEmpty() &&
                address.getStreet().isEmpty() &&
                address.getHouseNumber() <= 0 &&
                address.getApartmentNumber() <= 0);
    }

    public static boolean isNotEmpty(Person person) {
        return !(person.getFirstName().isEmpty() &&
                person.getMiddleName().isEmpty() &&
                person.getLastName().isEmpty());
    }

    public static boolean isNotEmpty(Contragent contragent) {
        return !(contragent.getOrganiztionId() <= 0 &&
                contragent.getPersonId() <= 0 &&
                contragent.getAddressId() <= 0);
    }
}
