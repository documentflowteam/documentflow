package com.documentflow.services;

import com.documentflow.AbstractContragentTest;
import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Organization;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.entities.dto.ContragentDtoBindAddressAndEmployee;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.exceptions.NotFoundContragentException;
import com.documentflow.exceptions.NotFoundEmployeeException;
import com.documentflow.repositories.ContragentRepository;
import com.documentflow.utils.ContragentUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Test for {@link ContragentService}
 */
public class ContragentServiceImplTest extends AbstractContragentTest {

    @Autowired
    private ContragentService contragentService;

    @Autowired
    private ContragentRepository contragentRepository;

    @Test
    public void testSearchContragents() {
        Contragent contragent = createAndSaveRandomContragent();
        List<Contragent> list1 = contragentService.searchContragents(contragent.getPerson().getFirstName());
        List<Contragent> list2 = contragentService.searchContragents(contragent.getPerson().getMiddleName());
        List<Contragent> list3 = contragentService.searchContragents(contragent.getPerson().getLastName());

        Assert.assertEquals(list1, list2);
        Assert.assertEquals(list2, list3);
    }

    @Test
    public void testSave() {
        ContragentDto dtoPerson = createRandomDtoPerson();
        ContragentDto dtoCompany = createRandomDtoOrganization();

        List<Contragent> contragentPerson = contragentService.save(dtoPerson);

        Assert.assertEquals(1, contragentPerson.size());
        Assert.assertEquals(false, contragentPerson.get(0).getIsDeleted());
        Assert.assertNull(contragentPerson.get(0).getPersonPosition());
        Assert.assertEquals(dtoPerson.getParameters().getFirstName().toUpperCase(), contragentPerson.get(0).getPerson().getFirstName());
        Assert.assertEquals(dtoPerson.getParameters().getMiddleName().toUpperCase(), contragentPerson.get(0).getPerson().getMiddleName());
        Assert.assertEquals(dtoPerson.getParameters().getLastName().toUpperCase(), contragentPerson.get(0).getPerson().getLastName());
        Assert.assertEquals(dtoPerson.getAddress()[0].getCountry().toUpperCase(), contragentPerson.get(0).getAddress().getCountry());
        Assert.assertEquals(dtoPerson.getAddress()[0].getCity().toUpperCase(), contragentPerson.get(0).getAddress().getCity());
        Assert.assertEquals(dtoPerson.getAddress()[0].getStreet().toUpperCase(), contragentPerson.get(0).getAddress().getStreet());
        Assert.assertEquals(dtoPerson.getAddress()[0].getHouseNumber(), contragentPerson.get(0).getAddress().getHouseNumber());
        Assert.assertEquals(dtoPerson.getAddress()[0].getApartrmentNumber(), contragentPerson.get(0).getAddress().getApartmentNumber());

        List<Contragent> contragentCompany = contragentService.save(dtoCompany);

        Assert.assertEquals(1, contragentCompany.size());
        Assert.assertEquals(dtoCompany.getParameters().getNameCompany(), contragentCompany.get(0).getOrganization().getName());
        Assert.assertEquals(dtoCompany.getEmployee()[0].getFirstName().toUpperCase(), contragentCompany.get(0).getPerson().getFirstName());
        Assert.assertEquals(dtoCompany.getEmployee()[0].getMiddleName().toUpperCase(), contragentCompany.get(0).getPerson().getMiddleName());
        Assert.assertEquals(dtoCompany.getEmployee()[0].getLastName().toUpperCase(), contragentCompany.get(0).getPerson().getLastName());
        Assert.assertEquals(dtoCompany.getEmployee()[0].getPersonPosition().toUpperCase(), contragentCompany.get(0).getPersonPosition());
        Assert.assertEquals(dtoCompany.getAddress()[0].getCountry().toUpperCase(), contragentCompany.get(0).getAddress().getCountry());
        Assert.assertEquals(dtoCompany.getAddress()[0].getCity().toUpperCase(), contragentCompany.get(0).getAddress().getCity());
        Assert.assertEquals(dtoCompany.getAddress()[0].getStreet().toUpperCase(), contragentCompany.get(0).getAddress().getStreet());
        Assert.assertEquals(dtoCompany.getAddress()[0].getHouseNumber(), contragentCompany.get(0).getAddress().getHouseNumber());
        Assert.assertEquals(dtoCompany.getAddress()[0].getApartrmentNumber(), contragentCompany.get(0).getAddress().getApartmentNumber());
    }

    @Test
    public void testUpdateEmployee() {

        Contragent contragent = createAndSaveRandomOrganization().getContragents().get(0);
        ContragentDtoEmployee contragentDtoEmployee = createRandomDtoEmployee();
        contragentDtoEmployee.setId(String.valueOf(contragent.getId()));

        Assert.assertNotEquals(contragent.getPerson().getFirstName(), contragentDtoEmployee.getFirstName());
        Assert.assertNotEquals(contragent.getPerson().getMiddleName(), contragentDtoEmployee.getMiddleName());
        Assert.assertNotEquals(contragent.getPerson().getLastName(), contragentDtoEmployee.getLastName());
        Assert.assertNotNull(contragent.getSearchName());

        contragent = contragentService.updateEmployee(contragentDtoEmployee);

        Assert.assertEquals(contragent.getPerson().getFirstName(), contragentDtoEmployee.getFirstName());
        Assert.assertEquals(contragent.getPerson().getMiddleName(), contragentDtoEmployee.getMiddleName());
        Assert.assertEquals(contragent.getPerson().getLastName(), contragentDtoEmployee.getLastName());

        String newSearchString = ContragentUtils.createSearchName(contragentDtoEmployee.getFirstName(),
                contragentDtoEmployee.getMiddleName(),
                contragentDtoEmployee.getLastName(),
                contragentDtoEmployee.getPersonPosition());
        Assert.assertTrue(contragent.getSearchName().contains(newSearchString));
    }

    @Test(expected = NotFoundEmployeeException.class)
    public void testUpdateEmployeeNotFoundEmployeeException() {

        ContragentDtoEmployee contragentDtoEmployee = createRandomDtoEmployee();
        contragentDtoEmployee.setId("0");
        contragentService.updateEmployee(contragentDtoEmployee);
    }

    @Test
    public void testDelete() {

        Contragent contragent = createAndSaveRandomContragent();

        Assert.assertFalse(contragent.getIsDeleted());

        contragentService.delete(contragent.getId());

        Assert.assertTrue(contragent.getIsDeleted());
    }

    @Test(expected = NotFoundContragentException.class)
    public void testDeleteNotFoundContragentException() {

        Contragent contragent = createBadContragent();
        contragentService.delete(contragent.getId());
    }

    @Test
    public void testBindAddressWithPerson() {

        Person person = createAndSaveRandomPerson();
        Address address = createRandomAddress();

        Address savedAddressWithIdContragent = contragentService.bindAddressWithPerson(person.getId(), address);

        Contragent contragent = contragentRepository.findById(savedAddressWithIdContragent.getId()).get();
        String searchName = ContragentUtils.createSearchName(person.getFirstName(), person.getMiddleName(), person.getLastName());

        Assert.assertEquals(person.getFirstName(), contragent.getPerson().getFirstName());
        Assert.assertEquals(person.getMiddleName(), contragent.getPerson().getMiddleName());
        Assert.assertEquals(person.getLastName(), contragent.getPerson().getLastName());
        Assert.assertEquals(address.getIndex(), contragent.getAddress().getIndex());
        Assert.assertEquals(address.getCountry().toUpperCase(), contragent.getAddress().getCountry());
        Assert.assertEquals(address.getCity().toUpperCase(), contragent.getAddress().getCity());
        Assert.assertEquals(address.getStreet().toUpperCase(), contragent.getAddress().getStreet());
        Assert.assertEquals(address.getHouseNumber(), contragent.getAddress().getHouseNumber());
        Assert.assertEquals(address.getApartmentNumber(), contragent.getAddress().getApartmentNumber());
        Assert.assertEquals(searchName, contragent.getSearchName());
        Assert.assertFalse(contragent.getIsDeleted());

        //work with duplicate
        Address savedAddressWithIdContragentSecond = contragentService.bindAddressWithPerson(person.getId(), address);
        Contragent contragentSecond = contragentRepository.findById(savedAddressWithIdContragentSecond.getId()).get();

        Assert.assertNotEquals(contragent.getId(), contragentSecond.getId());
        Assert.assertEquals(contragent.getAddress().getId(), contragentSecond.getAddress().getId());
    }

    @Test
    public void testBindAddressWithOrganization() {

        Organization organization = createAndSaveRandomOrganization();
        Address address = createRandomAddress();

        Address savedAddressWithIdContragent = contragentService.bindAddressWithOrganization(organization.getId(), address);

        Contragent contragent = contragentRepository.findById(savedAddressWithIdContragent.getId()).get();
        String searchName = ContragentUtils.createSearchName(organization.getName());

        Assert.assertEquals(organization.getName(), contragent.getOrganization().getName());
        Assert.assertEquals(address.getIndex(), contragent.getAddress().getIndex());
        Assert.assertEquals(address.getCountry().toUpperCase(), contragent.getAddress().getCountry());
        Assert.assertEquals(address.getCity().toUpperCase(), contragent.getAddress().getCity());
        Assert.assertEquals(address.getStreet().toUpperCase(), contragent.getAddress().getStreet());
        Assert.assertEquals(address.getHouseNumber(), contragent.getAddress().getHouseNumber());
        Assert.assertEquals(address.getApartmentNumber(), contragent.getAddress().getApartmentNumber());
        Assert.assertEquals(searchName, contragent.getSearchName());
        Assert.assertFalse(contragent.getIsDeleted());

        //work with duplicate
        Address savedAddressWithIdContragentSecond = contragentService.bindAddressWithOrganization(organization.getId(), address);
        Contragent contragentSecond = contragentRepository.findById(savedAddressWithIdContragentSecond.getId()).get();

        Assert.assertNotEquals(contragent.getId(), contragentSecond.getId());
        Assert.assertEquals(contragent.getAddress().getId(), contragentSecond.getAddress().getId());
    }

    @Test
    public void testBindEmployeeWithOrganization() {

        Organization organization = createAndSaveRandomOrganization();
        ContragentDtoEmployee employee = createRandomDtoEmployee();

        ContragentDtoEmployee savedEmployeeWithIdContragent = contragentService.bindEmployeeWithOrganization(organization.getId(), employee);

        Contragent contragent = contragentRepository.findById(Long.valueOf(savedEmployeeWithIdContragent.getId())).get();
        String searchName = ContragentUtils.createSearchName(employee.getFirstName(), employee.getMiddleName(), employee.getLastName(), employee.getPersonPosition(), organization.getName());

        Assert.assertEquals(organization.getName(), contragent.getOrganization().getName());
        Assert.assertEquals(employee.getPersonPosition().toUpperCase(), contragent.getPersonPosition());
        Assert.assertEquals(employee.getFirstName().toUpperCase(), contragent.getPerson().getFirstName());
        Assert.assertEquals(employee.getMiddleName().toUpperCase(), contragent.getPerson().getMiddleName());
        Assert.assertEquals(employee.getLastName().toUpperCase(), contragent.getPerson().getLastName());
        Assert.assertEquals(searchName, contragent.getSearchName());
        Assert.assertFalse(contragent.getIsDeleted());

        //work with duplicate
        ContragentDtoEmployee savedEmployeeWithIdContragentSecond = contragentService.bindEmployeeWithOrganization(organization.getId(), employee);
        Contragent contragentSecond = contragentRepository.findById(Long.valueOf(savedEmployeeWithIdContragentSecond.getId())).get();

        Assert.assertNotEquals(contragent.getId(), contragentSecond.getId());
        Assert.assertEquals(contragent.getPerson().getId(), contragentSecond.getPerson().getId());
    }

    @Test
    public void testBindEmployeeWithAddress() {

        Organization organization = createAndSaveRandomOrganization();
        ContragentDtoAddress contragentDtoAddress = createRandomDtoAddress();
        ContragentDtoEmployee contragentDtoEmployee = createRandomDtoEmployee();
        ContragentDtoBindAddressAndEmployee dto = new ContragentDtoBindAddressAndEmployee(organization.getId(), contragentDtoAddress, contragentDtoEmployee);

        ContragentDtoBindAddressAndEmployee savedDto = contragentService.bindEmployeeWithAddress(dto);

        String searchName = ContragentUtils.createSearchName(
                contragentDtoEmployee.getFirstName(),
                contragentDtoEmployee.getMiddleName(),
                contragentDtoEmployee.getLastName(),
                contragentDtoEmployee.getPersonPosition(),
                organization.getName()
        );

        Assert.assertEquals(contragentDtoEmployee.getPersonPosition().toUpperCase(), savedDto.getEmployee().getPersonPosition());
        Assert.assertEquals(contragentDtoEmployee.getFirstName().toUpperCase(), savedDto.getEmployee().getFirstName());
        Assert.assertEquals(contragentDtoEmployee.getMiddleName().toUpperCase(), savedDto.getEmployee().getMiddleName());
        Assert.assertEquals(contragentDtoEmployee.getLastName().toUpperCase(), savedDto.getEmployee().getLastName());
        //БД обрезает начальные нули
        //Assert.assertEquals(contragentDtoAddress.getPostIndex(), savedDto.getAddress().getPostIndex());
        Assert.assertEquals(contragentDtoAddress.getCountry().toUpperCase(), savedDto.getAddress().getCountry());
        Assert.assertEquals(contragentDtoAddress.getCity().toUpperCase(), savedDto.getAddress().getCity());
        Assert.assertEquals(contragentDtoAddress.getStreet().toUpperCase(), savedDto.getAddress().getStreet());
        Assert.assertEquals(contragentDtoAddress.getHouseNumber().toUpperCase(), savedDto.getAddress().getHouseNumber());
        Assert.assertEquals(contragentDtoAddress.getApartrmentNumber().toUpperCase(), savedDto.getAddress().getApartrmentNumber());
        Assert.assertEquals(savedDto.getEmployee().getId(), savedDto.getAddress().getId());

        Contragent contragent = contragentRepository.findById(Long.valueOf(savedDto.getEmployee().getId())).get();

        Assert.assertEquals(searchName, contragent.getSearchName());
        Assert.assertFalse(contragent.getIsDeleted());

        //work with duplicate
        ContragentDtoBindAddressAndEmployee savedDtoSecond = contragentService.bindEmployeeWithAddress(dto);
        Contragent contragentSecond = contragentRepository.findById(Long.valueOf(savedDtoSecond.getEmployee().getId())).get();

        Assert.assertNotEquals(contragent.getId(), contragentSecond.getId());
        Assert.assertEquals(contragent.getPerson().getId(), contragentSecond.getPerson().getId());
        Assert.assertEquals(contragent.getAddress().getId(), contragentSecond.getAddress().getId());
    }
}
