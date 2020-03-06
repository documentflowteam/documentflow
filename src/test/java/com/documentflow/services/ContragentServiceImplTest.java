package com.documentflow.services;

import com.documentflow.AbstractDocumentFlowTest;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.dto.ContragentDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContragentServiceImplTest extends AbstractDocumentFlowTest {

    @Autowired
    private ContragentService contragentService;


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
}
