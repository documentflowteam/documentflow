package com.documentflow.services;

import com.documentflow.AbstractContragentTest;
import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.entities.dto.ContragentDtoParameters;
import com.documentflow.exceptions.NotFoundPersonException;
import com.documentflow.repositories.ContragentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PersonServiceImplTest extends AbstractContragentTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private ContragentRepository contragentRepository;

    @Test
    public void testSaveDto() {
        ContragentDtoParameters dtoPerson = createRandomDtoParametersPerson();
        Person person = personService.save(dtoPerson);

        Assert.assertEquals(person.getFirstName(), dtoPerson.getFirstName().toUpperCase());
        Assert.assertEquals(person.getMiddleName(), dtoPerson.getMiddleName().toUpperCase());
        Assert.assertEquals(person.getLastName(), dtoPerson.getLastName().toUpperCase());
    }

    @Test(expected = NullPointerException.class)
    public void testSaveDtoNPE() {
        personService.save((ContragentDtoParameters) null);
    }

    @Test
    public void testSavePerson() {
        Person sourcePerson = createRandomPerson();
        Person person = personService.save(sourcePerson);

        Assert.assertEquals(person.getFirstName(), sourcePerson.getFirstName().toUpperCase());
        Assert.assertEquals(person.getMiddleName(), sourcePerson.getMiddleName().toUpperCase());
        Assert.assertEquals(person.getLastName(), sourcePerson.getLastName().toUpperCase());
    }

    @Test
    public void testSaveEmployee() {
        ContragentDtoEmployee dtoEmployee = createRandomDtoEmployee();
        Map<Person, String> mapEmployee = personService.save(new ContragentDtoEmployee[]{dtoEmployee});

        mapEmployee.forEach((key, value) -> {
            Assert.assertEquals(key.getFirstName(), dtoEmployee.getFirstName().toUpperCase());
            Assert.assertEquals(key.getMiddleName(), dtoEmployee.getMiddleName().toUpperCase());
            Assert.assertEquals(key.getLastName(), dtoEmployee.getLastName().toUpperCase());
            Assert.assertEquals(value, dtoEmployee.getPersonPosition());
        });
    }

    @Test
    public void testFind() {
        Person newPerson = createAndSaveRandomPerson();
        Person foundPerson = personService.find(newPerson.getId());

        Assert.assertEquals(newPerson.getFirstName(), foundPerson.getFirstName());
        Assert.assertEquals(newPerson.getMiddleName(), foundPerson.getMiddleName());
        Assert.assertEquals(newPerson.getLastName(), foundPerson.getLastName());
    }

    @Test(expected = NotFoundPersonException.class)
    public void testFindNotFoundPersonException() {
        personService.find(0L);
    }

    @Test
    public void testFindAll() {
        Person newPerson = createAndSaveRandomPerson();
        List<Person> foundPersons = personService.findAll(newPerson.getFirstName(), newPerson.getMiddleName(), newPerson.getLastName());

        Assert.assertEquals(1, foundPersons.size());
        Assert.assertEquals(newPerson.getFirstName(), foundPersons.get(0).getFirstName());
        Assert.assertEquals(newPerson.getMiddleName(), foundPersons.get(0).getMiddleName());
        Assert.assertEquals(newPerson.getLastName(), foundPersons.get(0).getLastName());

        Contragent contragent = newPerson.getContragents().get(0);
        contragent.setIsDeleted(true);
        contragentRepository.save(contragent);

        List<Person> notFoundPersons = personService.findAll(newPerson.getFirstName(), newPerson.getMiddleName(), newPerson.getLastName());
        Assert.assertEquals(Collections.emptyList(), notFoundPersons);
    }

    @Test
    public void testStrongFind() {
        Person newPerson = createAndSaveRandomPerson();
        Person foundPerson = personService.strongFind(newPerson);

        Assert.assertNotNull(foundPerson);
        Assert.assertEquals(newPerson.getFirstName(), foundPerson.getFirstName());
        Assert.assertEquals(newPerson.getMiddleName(), foundPerson.getMiddleName());
        Assert.assertEquals(newPerson.getLastName(), foundPerson.getLastName());

        Person notFoundPerson = personService.strongFind(new Person(null, null, newPerson.getMiddleName(), newPerson.getLastName()));
        Assert.assertNull(notFoundPerson);
    }

    //TODO test for update method
    @Test
    public void testUpdate() {
    }

    @Test
    public void testDelete() {

        Person newPerson = createAndSaveRandomPerson();
        personService.delete(newPerson.getId());

        List<Person> notFoundPersons = personService.findAll(newPerson.getFirstName(), newPerson.getMiddleName(), newPerson.getLastName());
        Assert.assertEquals(Collections.emptyList(), notFoundPersons);
    }

    @Test(expected = NotFoundPersonException.class)
    public void testDeleteNotFoundPersonException() {
        personService.delete(0L);
    }

    @Test
    public void testGetAddresses() {
        Person newPerson = createAndSaveRandomPerson();
        Address address = newPerson.getContragents().get(0).getAddress();

        List<Address> addresses = personService.getAddresses(newPerson.getId());

        Assert.assertNotNull(addresses);
        Assert.assertEquals(address.getIndex(), addresses.get(0).getIndex());
        Assert.assertEquals(address.getCountry(), addresses.get(0).getCountry());
        Assert.assertEquals(address.getCity(), addresses.get(0).getCity());
        Assert.assertEquals(address.getStreet(), addresses.get(0).getStreet());
        Assert.assertEquals(address.getHouseNumber(), addresses.get(0).getHouseNumber());
        Assert.assertEquals(address.getApartmentNumber(), addresses.get(0).getApartmentNumber());
    }

    @Test(expected = NotFoundPersonException.class)
    public void testGetAddressesNotFoundPersonException() {
        personService.getAddresses(0L);
    }
}
