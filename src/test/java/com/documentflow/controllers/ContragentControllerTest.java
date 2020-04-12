package com.documentflow.controllers;

import com.documentflow.AbstractContragentTest;
import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Organization;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.entities.dto.ContragentDtoBindAddressAndEmployee;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.exceptions.BadArgumentException;
import com.documentflow.repositories.ContragentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static com.documentflow.utils.Urls.*;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ContragentControllerTest extends AbstractContragentTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ContragentRepository contragentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    @Test
    public void testSearchContragent() throws Exception {

        mockMvc.perform(get(URL_CONTRAGENT)
                .header("referer", "login.html")
                .param("searchName", "someSearchValue"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("contragents", any(List.class)))
                .andReturn();
    }

    @Test
    public void testGetPage() throws Exception {

        mockMvc.perform(get(URL_CONTRAGENT + URL_CONTRAGENT_EDIT))
                .andExpect(view().name("contragent_edit"));

        mockMvc.perform(get(URL_CONTRAGENT + URL_CONTRAGENT_ADD))
                .andExpect(view().name("contragent_edit"));
    }

    @Test
    public void testAddNewContragent() throws Exception {

        ContragentDto personDto = createRandomDtoPerson();
        String requestJson = mapper.writeValueAsString(personDto);

        mockMvc.perform(post(URL_CONTRAGENT + URL_CONTRAGENT_ADD)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ContragentDto badPersonDto = createBadDtoPerson();
        requestJson = mapper.writeValueAsString(badPersonDto);

        try {
            mockMvc.perform(post(URL_CONTRAGENT + URL_CONTRAGENT_ADD)
                    .with(user("TEST_USER"))
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson));
            Assert.fail("the mockMvc should have thrown an exception");
        } catch (NestedServletException exception) {
            Assert.assertTrue(exception.getCause() instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testGetPerson() throws Exception {

        Person person = createAndSaveRandomPerson();

        mockMvc.perform(get(URL_CONTRAGENT + URL_PERSON)
                .param("first_name", person.getFirstName())
                .param("middle_name", person.getMiddleName())
                .param("last_name", person.getLastName())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].first_name", is(person.getFirstName())))
                .andExpect(jsonPath("$[0].middle_name", is(person.getMiddleName())))
                .andExpect(jsonPath("$[0].last_name", is(person.getLastName())))
                .andExpect(jsonPath("$[0].id", is(person.getId().intValue())))
                .andExpect(status().isOk());

        try {
            mockMvc.perform(get(URL_CONTRAGENT + URL_PERSON)
                    .param("first_name", person.getFirstName())
                    .param("middle_name", person.getMiddleName())
                    .param("last_name", ""));
            Assert.fail("the mockMvc should have thrown an exception");
        } catch (NestedServletException exception) {
            Assert.assertTrue(exception.getCause() instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testUpdatePerson() throws Exception {

        Person person = createAndSaveRandomPerson();
        Person updatedPerson = new Person(person.getId(), "NEW_FIRST_NAME", "NEW_MIDDLE_NAME", "NEW_LAST_NAME");

        String requestJson = mapper.writeValueAsString(updatedPerson);

        mockMvc.perform(post(URL_CONTRAGENT + URL_PERSON)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.first_name", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.middle_name", is(updatedPerson.getMiddleName())))
                .andExpect(jsonPath("$.last_name", is(updatedPerson.getLastName())))
                .andExpect(jsonPath("$.id", is(person.getId().intValue())))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {

        Person person = createAndSaveRandomPerson();

        Assert.assertEquals(false, person.getContragents().get(0).getIsDeleted());

        mockMvc.perform(delete(URL_CONTRAGENT + URL_PERSON_ID, person.getId())
                .with(user("TEST_USER"))
                .with(csrf()))
                .andExpect(status().isOk());

        Assert.assertEquals(true, person.getContragents().get(0).getIsDeleted());
    }

    @Test
    public void testDeleteAddressPerson() throws Exception {

        Person person = createAndSaveRandomPerson();
        Assert.assertEquals(false, person.getContragents().get(0).getIsDeleted());

        mockMvc.perform(delete(URL_CONTRAGENT + URL_PERSON_ADDRESS_ID, person.getContragents().get(0).getId())
                .with(user("TEST_USER"))
                .with(csrf()))
                .andExpect(status().isOk());

        Assert.assertEquals(true, person.getContragents().get(0).getIsDeleted());
    }

    @Test
    public void testAddNewAddressToPerson() throws Exception {

        Person person = createAndSaveRandomPerson();

        Address newAddress = createRandomAddress();

        //В полученном объекте типа Address в поле ID хранится ID объекта типа Person
        newAddress.setId(person.getId());

        String requestJson = mapper.writeValueAsString(newAddress);

        MvcResult mvcResult = mockMvc.perform(post(URL_CONTRAGENT + URL_PERSON_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.post_index", is(newAddress.getIndex())))
                .andExpect(jsonPath("$.country", is(newAddress.getCountry().toUpperCase())))
                .andExpect(jsonPath("$.city", is(newAddress.getCity().toUpperCase())))
                .andExpect(jsonPath("$.street", is(newAddress.getStreet().toUpperCase())))
                .andExpect(jsonPath("$.house_number", is(newAddress.getHouseNumber())))
                .andExpect(jsonPath("$.apartrment_number", is(newAddress.getApartmentNumber())))
                .andExpect(status().isOk())
                .andReturn();

        Address addressForSendToFrontend = mapper.readValue(mvcResult.getResponse().getContentAsString(), Address.class);
        Contragent firstContragent = contragentRepository.findById(addressForSendToFrontend.getId()).get();

        Assert.assertEquals(firstContragent.getPerson().getLastName(), person.getLastName());
        Assert.assertEquals(firstContragent.getPerson().getMiddleName(), person.getMiddleName());
        Assert.assertEquals(firstContragent.getPerson().getFirstName(), person.getFirstName());
        Assert.assertEquals(firstContragent.getAddress().getIndex(), newAddress.getIndex());
        Assert.assertEquals(firstContragent.getAddress().getCountry(), newAddress.getCountry().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getCity(), newAddress.getCity().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getStreet(), newAddress.getStreet().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getHouseNumber(), newAddress.getHouseNumber().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getApartmentNumber(), newAddress.getApartmentNumber().toUpperCase());

        mvcResult = mockMvc.perform(post(URL_CONTRAGENT + URL_PERSON_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.post_index", is(newAddress.getIndex())))
                .andExpect(jsonPath("$.country", is(newAddress.getCountry().toUpperCase())))
                .andExpect(jsonPath("$.city", is(newAddress.getCity().toUpperCase())))
                .andExpect(jsonPath("$.street", is(newAddress.getStreet().toUpperCase())))
                .andExpect(jsonPath("$.house_number", is(newAddress.getHouseNumber())))
                .andExpect(jsonPath("$.apartrment_number", is(newAddress.getApartmentNumber())))
                .andExpect(status().isOk())
                .andReturn();

        addressForSendToFrontend = mapper.readValue(mvcResult.getResponse().getContentAsString(), Address.class);
        Contragent secondContragent = contragentRepository.findById(addressForSendToFrontend.getId()).get();

        long addresId1 = firstContragent.getAddress().getId();
        long addresId2 = secondContragent.getAddress().getId();

        Assert.assertEquals(addresId1, addresId2);
    }

    @Test
    public void testGetAddressToPerson() throws Exception {

        Person person = createAndSaveRandomPerson();
        Address address = person.getAddresses().get(0);

        mockMvc.perform(get(URL_CONTRAGENT + URL_ADDRESS_TO_PERSON, person.getId()))
                .andExpect(jsonPath("$[0].post_index", is(address.getIndex())))
                .andExpect(jsonPath("$[0].country", is(address.getCountry().toUpperCase())))
                .andExpect(jsonPath("$[0].city", is(address.getCity().toUpperCase())))
                .andExpect(jsonPath("$[0].street", is(address.getStreet().toUpperCase())))
                .andExpect(jsonPath("$[0].house_number", is(address.getHouseNumber())))
                .andExpect(jsonPath("$[0].apartrment_number", is(address.getApartmentNumber())))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAddress() throws Exception {

        Address address = createAndSaveRandomAddress();

        mockMvc.perform(get(URL_CONTRAGENT + URL_ADDRESS)
                .param("post_index", address.getIndex().toString())
                .param("country", address.getCountry())
                .param("city", address.getCity())
                .param("street", address.getStreet())
                .param("house_number", address.getHouseNumber())
                .param("apartrment_number", address.getApartmentNumber()))
                .andExpect(jsonPath("$[0].post_index", is(address.getIndex())))
                .andExpect(jsonPath("$[0].country", is(address.getCountry().toUpperCase())))
                .andExpect(jsonPath("$[0].city", is(address.getCity().toUpperCase())))
                .andExpect(jsonPath("$[0].street", is(address.getStreet().toUpperCase())))
                .andExpect(jsonPath("$[0].house_number", is(address.getHouseNumber())))
                .andExpect(jsonPath("$[0].apartrment_number", is(address.getApartmentNumber())))
                .andExpect(status().isOk());

        try {
            mockMvc.perform(get(URL_CONTRAGENT + URL_ADDRESS)
                    .param("post_index", address.getIndex().toString())
                    .param("country", "")
                    .param("city", address.getCity())
                    .param("street", address.getStreet())
                    .param("house_number", address.getHouseNumber())
                    .param("apartrment_number", address.getApartmentNumber()));
            Assert.fail("the mockMvc should have thrown an exception");
        } catch (NestedServletException exp) {
            Assert.assertTrue(exp.getCause() instanceof BadArgumentException);
        }
        try {
            mockMvc.perform(get(URL_CONTRAGENT + URL_ADDRESS)
                    .param("post_index", address.getIndex().toString())
                    .param("country", address.getCountry())
                    .param("city", "")
                    .param("street", address.getStreet())
                    .param("house_number", address.getHouseNumber())
                    .param("apartrment_number", address.getApartmentNumber()));
            Assert.fail("the mockMvc should have thrown an exception");
        } catch (NestedServletException exp) {
            Assert.assertTrue(exp.getCause() instanceof BadArgumentException);
        }
        try {
            mockMvc.perform(get(URL_CONTRAGENT + URL_ADDRESS)
                    .param("post_index", address.getIndex().toString())
                    .param("country", address.getCountry())
                    .param("city", address.getCity())
                    .param("street", "")
                    .param("house_number", address.getHouseNumber())
                    .param("apartrment_number", address.getApartmentNumber()));
            Assert.fail("the mockMvc should have thrown an exception");
        } catch (NestedServletException exp) {
            Assert.assertTrue(exp.getCause() instanceof BadArgumentException);
        }
    }

    @Test
    public void testUpdateAddress() throws Exception {

        Address address = createAndSaveRandomAddress();

        Address newAddress = Address.builder()
                .id(address.getId())
                .country("USA")
                .city("New York")
                .street("Victory")
                .houseNumber("100")
                .apartmentNumber("100")
                .build();

        String requestJson = mapper.writeValueAsString(newAddress);

        mockMvc.perform(post(URL_CONTRAGENT + URL_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.post_index", is(address.getIndex())))
                .andExpect(jsonPath("$.country", is(newAddress.getCountry().toUpperCase())))
                .andExpect(jsonPath("$.city", is(newAddress.getCity().toUpperCase())))
                .andExpect(jsonPath("$.street", is(newAddress.getStreet().toUpperCase())))
                .andExpect(jsonPath("$.house_number", is(newAddress.getHouseNumber())))
                .andExpect(jsonPath("$.apartrment_number", is(newAddress.getApartmentNumber())))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAddress() throws Exception {

        Address address = createAndSaveRandomAddress();
        Assert.assertEquals(false, address.getContragents().get(0).getIsDeleted());

        mockMvc.perform(delete(URL_CONTRAGENT + URL_ADDRESS_ID, address.getId())
                .with(user("TEST_USER"))
                .with(csrf()))
                .andExpect(status().isOk());

        Assert.assertEquals(true, address.getContragents().get(0).getIsDeleted());
    }

    @Test
    public void testGetOrganization() throws Exception {

        Organization organization = createAndSaveRandomOrganization();

        mockMvc.perform(get(URL_CONTRAGENT + URL_COMPANY)
                .param("name_company", organization.getName()))
                .andExpect(jsonPath("$[0].name_company", is(organization.getName())))
                .andExpect(status().isOk());

        try {
            mockMvc.perform(get(URL_CONTRAGENT + URL_COMPANY)
                    .param("name_company", ""));
            Assert.fail("the mockMvc should have thrown an exception");
        } catch (NestedServletException exp) {
            Assert.assertTrue(exp.getCause() instanceof BadArgumentException);
        }
    }

    @Test
    public void testGetAddressToCompany() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Address addressOrganization = organization.getAddresses().get(0);

        mockMvc.perform(get(URL_CONTRAGENT + URL_ADDRESS_TO_COMPANY, organization.getId()))
                .andExpect(jsonPath("$[0].post_index", is(addressOrganization.getIndex())))
                .andExpect(jsonPath("$[0].country", is(addressOrganization.getCountry())))
                .andExpect(jsonPath("$[0].city", is(addressOrganization.getCity())))
                .andExpect(jsonPath("$[0].street", is(addressOrganization.getStreet())))
                .andExpect(jsonPath("$[0].house_number", is(addressOrganization.getHouseNumber())))
                .andExpect(jsonPath("$[0].apartrment_number", is(addressOrganization.getApartmentNumber())))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddNewAddressToCompany() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Address address = createRandomAddress();

        //В полученном объекте типа Address в поле ID хранится ID объекта типа Organization
        address.setId(organization.getId());

        Assert.assertEquals(1, organization.getAddresses().size());

        String requestJson = mapper.writeValueAsString(address);

        MvcResult mvcResult = mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.post_index", is(address.getIndex())))
                .andExpect(jsonPath("$.country", is(address.getCountry().toUpperCase())))
                .andExpect(jsonPath("$.city", is(address.getCity().toUpperCase())))
                .andExpect(jsonPath("$.street", is(address.getStreet().toUpperCase())))
                .andExpect(jsonPath("$.house_number", is(address.getHouseNumber())))
                .andExpect(jsonPath("$.apartrment_number", is(address.getApartmentNumber())))
                .andExpect(status().isOk())
                .andReturn();

        Address addressForSendToFrontend = mapper.readValue(mvcResult.getResponse().getContentAsString(), Address.class);
        Contragent firstContragent = contragentRepository.findById(addressForSendToFrontend.getId()).get();

        Assert.assertEquals(firstContragent.getOrganization().getName(), organization.getName());
        Assert.assertEquals(firstContragent.getAddress().getIndex(), address.getIndex());
        Assert.assertEquals(firstContragent.getAddress().getCountry(), address.getCountry().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getCity(), address.getCity().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getStreet(), address.getStreet().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getHouseNumber(), address.getHouseNumber().toUpperCase());
        Assert.assertEquals(firstContragent.getAddress().getApartmentNumber(), address.getApartmentNumber().toUpperCase());

        //проверяем то, что не будет создан дубликат
        mvcResult = mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.post_index", is(address.getIndex())))
                .andExpect(jsonPath("$.country", is(address.getCountry().toUpperCase())))
                .andExpect(jsonPath("$.city", is(address.getCity().toUpperCase())))
                .andExpect(jsonPath("$.street", is(address.getStreet().toUpperCase())))
                .andExpect(jsonPath("$.house_number", is(address.getHouseNumber())))
                .andExpect(jsonPath("$.apartrment_number", is(address.getApartmentNumber())))
                .andExpect(status().isOk())
                .andReturn();

        addressForSendToFrontend = mapper.readValue(mvcResult.getResponse().getContentAsString(), Address.class);
        Contragent secondContragent = contragentRepository.findById(addressForSendToFrontend.getId()).get();

        long addresId1 = firstContragent.getAddress().getId();
        long addresId2 = secondContragent.getAddress().getId();

        Assert.assertEquals(addresId1, addresId2);

        address.setId(null);
        requestJson = mapper.writeValueAsString(address);

        mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddNewEmployeeToCompany() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        ContragentDtoEmployee employee = createRandomDtoEmployee();

        //В полученном объекте типа ContragentDtoEmployee в поле ID хранится ID объекта типа Organization
        employee.setId(organization.getId().toString());

        Assert.assertEquals(1, organization.getPersons().size());

        String requestJson = mapper.writeValueAsString(employee);

        MvcResult mvcResult = mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_EMPLOYEE)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.first_name", is(employee.getFirstName().toUpperCase())))
                .andExpect(jsonPath("$.middle_name", is(employee.getMiddleName().toUpperCase())))
                .andExpect(jsonPath("$.last_name", is(employee.getLastName().toUpperCase())))
                .andExpect(jsonPath("$.position", is(employee.getPersonPosition().toUpperCase())))
                .andExpect(status().isOk())
                .andReturn();

        ContragentDtoEmployee contragentDtoEmployee = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContragentDtoEmployee.class);
        Contragent firstContragent = contragentRepository.findById(Long.valueOf(contragentDtoEmployee.getId())).get();

        Assert.assertEquals(firstContragent.getOrganization().getName(), organization.getName());
        Assert.assertEquals(firstContragent.getPerson().getFirstName(), employee.getFirstName().toUpperCase());
        Assert.assertEquals(firstContragent.getPerson().getMiddleName(), employee.getMiddleName().toUpperCase());
        Assert.assertEquals(firstContragent.getPerson().getLastName(), employee.getLastName().toUpperCase());

        //проверяем то, что не будет создан дубликат
        mvcResult = mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_EMPLOYEE)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.first_name", is(employee.getFirstName().toUpperCase())))
                .andExpect(jsonPath("$.middle_name", is(employee.getMiddleName().toUpperCase())))
                .andExpect(jsonPath("$.last_name", is(employee.getLastName().toUpperCase())))
                .andExpect(jsonPath("$.position", is(employee.getPersonPosition().toUpperCase())))
                .andExpect(status().isOk())
                .andReturn();

        contragentDtoEmployee = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContragentDtoEmployee.class);
        Contragent secondContragent = contragentRepository.findById(Long.valueOf(contragentDtoEmployee.getId())).get();

        long employeeId1 = firstContragent.getPerson().getId();
        long employeeId2 = secondContragent.getPerson().getId();

        Assert.assertEquals(employeeId1, employeeId2);

        employee.setId(null);
        requestJson = mapper.writeValueAsString(employee);

        mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_EMPLOYEE)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddNewEmployeeAndAddressToCompany() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        ContragentDtoAddress address = createRandomDtoAddress();
        ContragentDtoEmployee employee = createRandomDtoEmployee();

        Assert.assertEquals(1, organization.getAddresses().size());
        Assert.assertEquals(1, organization.getPersons().size());

        ContragentDtoBindAddressAndEmployee newEmployeeAndAddressForOrganization = new ContragentDtoBindAddressAndEmployee(
                organization.getId(),
                address,
                employee
        );

        String requestJson = mapper.writeValueAsString(newEmployeeAndAddressForOrganization);

        MvcResult mvcResult = mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_EMPLOYEE_AND_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.address.post_index", is(address.getPostIndex())))
                .andExpect(jsonPath("$.address.country", is(address.getCountry().toUpperCase())))
                .andExpect(jsonPath("$.address.city", is(address.getCity().toUpperCase())))
                .andExpect(jsonPath("$.address.street", is(address.getStreet().toUpperCase())))
                .andExpect(jsonPath("$.address.house_number", is(address.getHouseNumber())))
                .andExpect(jsonPath("$.address.apartrment_number", is(address.getApartrmentNumber())))
                .andExpect(jsonPath("$.employee.first_name", is(employee.getFirstName().toUpperCase())))
                .andExpect(jsonPath("$.employee.middle_name", is(employee.getMiddleName().toUpperCase())))
                .andExpect(jsonPath("$.employee.last_name", is(employee.getLastName().toUpperCase())))
                .andExpect(jsonPath("$.employee.position", is(employee.getPersonPosition().toUpperCase())))
                .andExpect(status().isOk())
                .andReturn();

        ContragentDtoBindAddressAndEmployee savedContragentDtoBindAddressAndEmployee = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContragentDtoBindAddressAndEmployee.class);
        Contragent firstContragent = contragentRepository.findById(Long.valueOf(savedContragentDtoBindAddressAndEmployee.getEmployee().getId())).get();

        Assert.assertEquals(firstContragent.getPersonPosition().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getEmployee().getPersonPosition());
        Assert.assertEquals(firstContragent.getPerson().getFirstName().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getEmployee().getFirstName());
        Assert.assertEquals(firstContragent.getPerson().getMiddleName().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getEmployee().getMiddleName());
        Assert.assertEquals(firstContragent.getPerson().getLastName().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getEmployee().getLastName());
        Assert.assertEquals(firstContragent.getAddress().getCountry().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getAddress().getCountry());
        Assert.assertEquals(firstContragent.getAddress().getCity().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getAddress().getCity());
        Assert.assertEquals(firstContragent.getAddress().getStreet().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getAddress().getStreet());
        Assert.assertEquals(firstContragent.getAddress().getHouseNumber().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getAddress().getHouseNumber());
        Assert.assertEquals(firstContragent.getAddress().getApartmentNumber().toUpperCase(), savedContragentDtoBindAddressAndEmployee.getAddress().getApartrmentNumber());

        //проверяем работу с дубликатами
        mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_EMPLOYEE_AND_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.address.post_index", is(address.getPostIndex())))
                .andExpect(jsonPath("$.address.country", is(address.getCountry().toUpperCase())))
                .andExpect(jsonPath("$.address.city", is(address.getCity().toUpperCase())))
                .andExpect(jsonPath("$.address.street", is(address.getStreet().toUpperCase())))
                .andExpect(jsonPath("$.address.house_number", is(address.getHouseNumber())))
                .andExpect(jsonPath("$.address.apartrment_number", is(address.getApartrmentNumber())))
                .andExpect(jsonPath("$.employee.first_name", is(employee.getFirstName().toUpperCase())))
                .andExpect(jsonPath("$.employee.middle_name", is(employee.getMiddleName().toUpperCase())))
                .andExpect(jsonPath("$.employee.last_name", is(employee.getLastName().toUpperCase())))
                .andExpect(jsonPath("$.employee.position", is(employee.getPersonPosition().toUpperCase())))
                .andExpect(status().isOk());

        ContragentDtoBindAddressAndEmployee savedContragentDtoBindAddressAndEmployeeSecond = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContragentDtoBindAddressAndEmployee.class);
        Contragent secondContragent = contragentRepository.findById(Long.valueOf(savedContragentDtoBindAddressAndEmployeeSecond.getEmployee().getId())).get();

        long employeeId1 = firstContragent.getPerson().getId();
        long employeeId2 = secondContragent.getPerson().getId();

        Assert.assertEquals(employeeId1, employeeId2);

        long address1 = firstContragent.getAddress().getId();
        long address2 = secondContragent.getAddress().getId();

        Assert.assertEquals(address1, address2);

        newEmployeeAndAddressForOrganization.setId(null);
        requestJson = mapper.writeValueAsString(newEmployeeAndAddressForOrganization);

        mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY_EMPLOYEE_AND_ADDRESS)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetEmployeeToCompany() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Person employee = organization.getPersons().get(0);

        Assert.assertEquals(1, organization.getPersons().size());

        mockMvc.perform(get(URL_CONTRAGENT + URL_EMPLOYEE_TO_COMPANY, organization.getId()))
                .andExpect(jsonPath("$[0].id", is(organization.getContragents().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].first_name", is(employee.getFirstName().toUpperCase())))
                .andExpect(jsonPath("$[0].middle_name", is(employee.getMiddleName().toUpperCase())))
                .andExpect(jsonPath("$[0].last_name", is(employee.getLastName().toUpperCase())))
                .andExpect(jsonPath("$[0].position", is(organization.getContragents().get(0).getPersonPosition().toUpperCase())))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCompanyAddressOrEmployee() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Contragent contragent = organization.getContragents().get(0);

        Assert.assertFalse(contragent.getIsDeleted());

        mockMvc.perform(delete(URL_CONTRAGENT + URL_COMPANY_ADDRESS_ID, contragent.getId())
                .with(user("TEST_USER"))
                .with(csrf()))
                .andExpect(status().isOk());

        Assert.assertTrue(contragent.getIsDeleted());
    }

    @Test
    public void testUpdateOrganization() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Organization updatedOrganization = new Organization(organization.getId(), "NEW_NAME_ORGANIZATION");

        Assert.assertNotEquals(organization.getName(), updatedOrganization.getName());

        String requestJson = mapper.writeValueAsString(updatedOrganization);

        mockMvc.perform(post(URL_CONTRAGENT + URL_COMPANY)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        Assert.assertEquals(organization.getName(), updatedOrganization.getName());
        Assert.assertTrue(organization.getContragents().get(0).getSearchName().contains("NEW_NAME_ORGANIZATION"));
    }

    @Test
    public void testDeleteOrganization() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Contragent contragent = organization.getContragents().get(0);

        Assert.assertFalse(contragent.getIsDeleted());

        mockMvc.perform(delete(URL_CONTRAGENT + URL_COMPANY_ID, organization.getId())
                .with(user("TEST_USER"))
                .with(csrf()))
                .andExpect(status().isOk());

        Assert.assertTrue(contragent.getIsDeleted());
    }

    @Test
    public void testGetEmployee() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Person employee = organization.getPersons().get(0);
        String employeePosition = organization.getContragents().get(0).getPersonPosition();

        mockMvc.perform(get(URL_CONTRAGENT + URL_EMPLOYEE)
                .param("first_name", employee.getFirstName())
                .param("middle_name", employee.getMiddleName())
                .param("last_name", employee.getLastName())
                .param("position", employeePosition))
                .andExpect(jsonPath("$[0].id", is(organization.getContragents().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$[0].middle_name", is(employee.getMiddleName())))
                .andExpect(jsonPath("$[0].last_name", is(employee.getLastName())))
                .andExpect(jsonPath("$[0].position", is(employeePosition)))
                .andExpect(status().isOk());

        mockMvc.perform(get(URL_CONTRAGENT + URL_EMPLOYEE)
                .param("middle_name", employee.getMiddleName())
                .param("last_name", employee.getLastName())
                .param("position", employeePosition))
                .andExpect(jsonPath("$[0].id", is(organization.getContragents().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$[0].middle_name", is(employee.getMiddleName())))
                .andExpect(jsonPath("$[0].last_name", is(employee.getLastName())))
                .andExpect(jsonPath("$[0].position", is(employeePosition)))
                .andExpect(status().isOk());

        mockMvc.perform(get(URL_CONTRAGENT + URL_EMPLOYEE)
                .param("first_name", employee.getFirstName())
                .param("last_name", employee.getLastName())
                .param("position", employeePosition))
                .andExpect(jsonPath("$[0].id", is(organization.getContragents().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$[0].middle_name", is(employee.getMiddleName())))
                .andExpect(jsonPath("$[0].last_name", is(employee.getLastName())))
                .andExpect(jsonPath("$[0].position", is(employeePosition)))
                .andExpect(status().isOk());

        mockMvc.perform(get(URL_CONTRAGENT + URL_EMPLOYEE)
                .param("last_name", employee.getLastName())
                .param("position", employeePosition))
                .andExpect(jsonPath("$[0].id", is(organization.getContragents().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].first_name", is(employee.getFirstName())))
                .andExpect(jsonPath("$[0].middle_name", is(employee.getMiddleName())))
                .andExpect(jsonPath("$[0].last_name", is(employee.getLastName())))
                .andExpect(jsonPath("$[0].position", is(employeePosition)))
                .andExpect(status().isOk());

        mockMvc.perform(get(URL_CONTRAGENT + URL_EMPLOYEE)
                .param("first_name", employee.getFirstName())
                .param("middle_name", employee.getMiddleName())
                .param("position", employeePosition))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testEditEmployee() throws Exception {

        Organization organization = createAndSaveRandomOrganization();
        Contragent employee = organization.getContragents().get(0);
        ContragentDtoEmployee updatedEmployee = createRandomDtoEmployee();
        updatedEmployee.setId(employee.getId().toString());

        Assert.assertNotEquals(employee.getPerson().getFirstName(), updatedEmployee.getFirstName());
        Assert.assertNotEquals(employee.getPerson().getMiddleName(), updatedEmployee.getMiddleName());
        Assert.assertNotEquals(employee.getPerson().getLastName(), updatedEmployee.getLastName());
        Assert.assertNotEquals(employee.getPersonPosition(), updatedEmployee.getPersonPosition());

        String requestJson = mapper.writeValueAsString(updatedEmployee);

        mockMvc.perform(post(URL_CONTRAGENT + URL_EMPLOYEE)
                .with(user("TEST_USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.id", is(employee.getId().intValue())))
                .andExpect(jsonPath("$.position", is(updatedEmployee.getPersonPosition().toUpperCase())))
                .andExpect(status().isOk());
    }
}
