package com.documentflow.services;

import com.documentflow.AbstractContragentTest;
import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Organization;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.exceptions.NotFoundIdException;
import com.documentflow.exceptions.NotFoundOrganizationException;
import com.documentflow.repositories.ContragentRepository;
import com.documentflow.repositories.OrganizationRepository;
import com.documentflow.repositories.specifications.OrganizationSpecifications;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrganizationServiceImplTest extends AbstractContragentTest {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ContragentRepository contragentRepository;

    @Test
    public void testSave() {

        ContragentDto dtoOrganization = createRandomDtoOrganization();

        Specification<Organization> spec = Specification.where(null);
        spec = spec.and(OrganizationSpecifications.nameCompanyLike(dtoOrganization.getParameters().getNameCompany()));

        List<Organization> notFoundOrganization = organizationRepository.findAll(spec);

        Assert.assertEquals(Collections.emptyList(), notFoundOrganization);

        organizationService.save(dtoOrganization.getParameters());

        List<Organization> foundOrganization = organizationRepository.findAll(spec);

        Assert.assertEquals(1, foundOrganization.size());
        Assert.assertEquals(dtoOrganization.getParameters().getNameCompany(), foundOrganization.get(0).getName());
    }

    @Test(expected = NullPointerException.class)
    public void testSaveNPE() {
        organizationService.save(null);
    }

    @Test
    public void testFindAll() {
        Organization newOrganization = createAndSaveRandomOrganization();
        List<Organization> foundOrganization = organizationService.findAll(newOrganization.getName());

        Assert.assertEquals(1, foundOrganization.size());
        Assert.assertEquals(newOrganization.getName(), foundOrganization.get(0).getName());

        Contragent contragent = foundOrganization.get(0).getContragents().get(0);
        contragent.setIsDeleted(true);
        contragentRepository.save(contragent);

        List<Organization> notFoundOrganization = organizationService.findAll(newOrganization.getName());
        Assert.assertEquals(Collections.emptyList(), notFoundOrganization);
    }

    @Test
    public void testFind() {
        Organization newOrganization = createAndSaveRandomOrganization();
        Organization foundOrganization = organizationService.find(newOrganization.getId());
        Assert.assertEquals(newOrganization.getName(), foundOrganization.getName());
    }

    @Test(expected = NotFoundOrganizationException.class)
    public void testFindNotFoundOrganizationException() {
        organizationService.find(0L);
    }

    //TODO not work
//    @Test
//    public void testUpdate(){
//        Organization savedOrganization = createAndSaveRandomOrganization();
//        Organization modifiedOrganization = new Organization();
//        modifiedOrganization.setId(savedOrganization.getId());
//        modifiedOrganization.setName("NEW_NAME");
//        modifiedOrganization.setContragents(savedOrganization.getContragents());
//        modifiedOrganization.setAddresses(savedOrganization.getAddresses());
//        modifiedOrganization.setPersons(savedOrganization.getPersons());
//
//        Organization updatedOrganization = organizationService.update(modifiedOrganization);
//
//        Assert.assertEquals(savedOrganization.getId(), updatedOrganization.getId());
//        Assert.assertEquals("NEW_NAME", updatedOrganization.getName());
//        Assert.assertEquals("NEW_NAME", updatedOrganization.getContragents().get(0).getSearchName());
//    }

    @Test(expected = NotFoundIdException.class)
    public void testUpdateNotFoundIdException() {
        Organization organization = createRandomOrganization();
        organizationService.update(organization);
    }

    @Test(expected = NotFoundOrganizationException.class)
    public void testUpdateNotFoundOrganizationException() {
        Organization organization = createRandomOrganization();
        organization.setId(0L);
        organizationService.update(organization);
    }

    @Test
    public void testDelete() {
        Organization savedOrganization = createAndSaveRandomOrganization();

        Optional<Organization> optionalFoundOrganization = organizationRepository.findById(savedOrganization.getId());
        Organization foundOrganization = optionalFoundOrganization.get();
        Assert.assertFalse(foundOrganization.getContragents().get(0).getIsDeleted());

        organizationService.delete(savedOrganization.getId());

        Optional<Organization> optionalNotFoundOrganization = organizationRepository.findById(savedOrganization.getId());
        Organization notFoundOrganization = optionalNotFoundOrganization.get();
        Assert.assertTrue(notFoundOrganization.getContragents().get(0).getIsDeleted());
    }

    @Test(expected = NotFoundOrganizationException.class)
    public void testDeleteNotFoundOrganizationException() {
        organizationService.delete(0L);
    }

    @Test
    public void testGetAddresses() {
        Organization savedOrganization = createAndSaveRandomOrganization();
        List<Address> listAddresses = organizationService.getAddresses(savedOrganization.getId());

        Address address1 = savedOrganization.getContragents().get(0).getAddress();
        Address address2 = listAddresses.get(0);

        Assert.assertEquals(1, listAddresses.size());
        Assert.assertEquals(address1.getCountry(), address2.getCountry());
        Assert.assertEquals(address1.getCity(), address2.getCity());
        Assert.assertEquals(address1.getStreet(), address2.getStreet());
        Assert.assertEquals(address1.getIndex(), address2.getIndex());
        Assert.assertEquals(address1.getHouseNumber(), address2.getHouseNumber());
        Assert.assertEquals(address1.getApartmentNumber(), address2.getApartmentNumber());

        organizationService.delete(savedOrganization.getId());

        List<Address> listNotFoundAddresses = organizationService.getAddresses(savedOrganization.getId());

        Assert.assertEquals(Collections.emptyList(), listNotFoundAddresses);
    }

    @Test(expected = NotFoundOrganizationException.class)
    public void testGetAddressesNotFoundOrganizationException() {
        organizationService.getAddresses(0L);
    }

    @Test
    public void testGetEmployees() {
        Organization savedOrganization = createAndSaveRandomOrganization();
        List<ContragentDtoEmployee> listDtoEmployee = organizationService.getEmployees(savedOrganization.getId());

        Person person1 = savedOrganization.getPersons().get(0);
        ContragentDtoEmployee person2 = listDtoEmployee.get(0);

        Assert.assertEquals(1, listDtoEmployee.size());
        Assert.assertEquals(person1.getFirstName(), person2.getFirstName());
        Assert.assertEquals(person1.getMiddleName(), person2.getMiddleName());
        Assert.assertEquals(person1.getLastName(), person2.getLastName());
        Assert.assertEquals(savedOrganization.getContragents().get(0).getPersonPosition(), person2.getPersonPosition());

        organizationService.delete(savedOrganization.getId());

        List<ContragentDtoEmployee> listNotFoundEmployee = organizationService.getEmployees(savedOrganization.getId());

        Assert.assertEquals(Collections.emptyList(), listNotFoundEmployee);
    }

    @Test(expected = NotFoundOrganizationException.class)
    public void testGetEmployeesNotFoundOrganizationException() {
        organizationService.getEmployees(0L);
    }
}
