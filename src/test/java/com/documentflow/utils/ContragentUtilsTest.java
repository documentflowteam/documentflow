package com.documentflow.utils;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link ContragentUtils}
 */
public class ContragentUtilsTest {

    @Test
    public void testIsNotEmptyAddress() {

        Address goodAddress = new Address();
        goodAddress.setCountry("Россия");
        goodAddress.setCity("Москва");
        goodAddress.setStreet("Петрова");

        Address badAddressNotStreet = new Address();
        badAddressNotStreet.setCountry("Россия");
        badAddressNotStreet.setCity("Москва");

        Address badAddressNotCity1 = new Address();
        badAddressNotCity1.setCountry("Россия");
        badAddressNotCity1.setStreet("Петрова");
        badAddressNotCity1.setIndex(123456);

        Address badAddressNotCity2 = new Address();
        badAddressNotCity2.setCountry("Россия");
        badAddressNotCity2.setStreet("Петрова");
        badAddressNotCity2.setStreet("");
        badAddressNotCity2.setIndex(123456);

        Assertions.assertTrue(ContragentUtils.isNotEmpty(goodAddress));
        Assertions.assertFalse(ContragentUtils.isNotEmpty(badAddressNotStreet));
        Assertions.assertFalse(ContragentUtils.isNotEmpty(badAddressNotCity1));
        Assertions.assertFalse(ContragentUtils.isNotEmpty(badAddressNotCity2));
    }

    @Test
    public void testIsEmptyContragentDtoParameters() {

        ContragentDtoParameters goodContragentParapeterPerson = new ContragentDtoParameters("Иван", "Иванович", "Иванов", null);
        ContragentDtoParameters goodContragentParapeterOrganization = new ContragentDtoParameters(null, null, null, "TestCompany");
        ContragentDtoParameters badContragentParapeter1 = new ContragentDtoParameters("Иван", "Иванович", "", null);
        ContragentDtoParameters badContragentParapeter2 = new ContragentDtoParameters("Иван", "Иванович", "", "");
        ContragentDtoParameters badContragentParapeter3 = new ContragentDtoParameters("Иван", "Иванович", null, null);
        ContragentDtoParameters badContragentParapeter4 = new ContragentDtoParameters("Иван", "Иванович", null, "");

        Assertions.assertFalse(ContragentUtils.isEmpty(goodContragentParapeterPerson));
        Assertions.assertFalse(ContragentUtils.isEmpty(goodContragentParapeterOrganization));
        Assertions.assertTrue(ContragentUtils.isEmpty(badContragentParapeter1));
        Assertions.assertTrue(ContragentUtils.isEmpty(badContragentParapeter2));
        Assertions.assertTrue(ContragentUtils.isEmpty(badContragentParapeter3));
        Assertions.assertTrue(ContragentUtils.isEmpty(badContragentParapeter4));
    }

    @Test
    public void testIsNotContragentDtoEmployee() {

        ContragentDtoEmployee goodEmployee = new ContragentDtoEmployee(null, "Иван", "Иванович", "Иванов", "postman");
        ContragentDtoEmployee badEmployee1 = new ContragentDtoEmployee(null, "Иван", "", "", "postman");
        ContragentDtoEmployee badEmployee2 = new ContragentDtoEmployee(null, "Иван", null, null, "postman");
        ContragentDtoEmployee badEmployee3 = new ContragentDtoEmployee(null, "Иван", "", null, "postman");
        ContragentDtoEmployee badEmployee4 = new ContragentDtoEmployee(null, "Иван", null, "", "postman");

        Assertions.assertTrue(ContragentUtils.isNotEmpty(goodEmployee));
        Assertions.assertFalse(ContragentUtils.isNotEmpty(badEmployee1));
        Assertions.assertFalse(ContragentUtils.isNotEmpty(badEmployee2));
        Assertions.assertFalse(ContragentUtils.isNotEmpty(badEmployee3));
        Assertions.assertFalse(ContragentUtils.isNotEmpty(badEmployee4));
    }
}
