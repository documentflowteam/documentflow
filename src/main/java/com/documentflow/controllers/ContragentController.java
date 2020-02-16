package com.documentflow.controllers;

import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.Organization;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.entities.dto.ContragentDtoEmployee;
import com.documentflow.services.AddressService;
import com.documentflow.services.ContragentService;
import com.documentflow.services.OrganizationService;
import com.documentflow.services.PersonService;
import com.documentflow.utils.ContragentUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contragent")
public class ContragentController {

    @Autowired
    private ContragentService contragentService;

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping()
    public ModelAndView searchContragent(@RequestParam(value = "searchName") String searchName,
                                         HttpServletRequest request) {

        String redirectUrl = request.getHeader("referer");
        ModelAndView modelAndView = createDefaultModelAndView(redirectUrl);

        if (StringUtils.isNotEmpty(searchName)) {
            modelAndView.addObject("contragents", contragentService.searchContragents(searchName));
        }
        return modelAndView;
    }

    private ModelAndView createDefaultModelAndView(String url) {
        return new ModelAndView(url);
    }

    @GetMapping("/add")
    public String getPageToAdd() {
        return "contragent_add";
    }

    @GetMapping("/edit")
    public String getPageToEdit() {
        return "contragent_edit";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addNewContragent(@RequestBody ContragentDto contragentDto) {

        if (ContragentUtils.isEmpty(contragentDto.getParameters())) {
            throw new RuntimeException("Не заполнены основные параметры контрагента");
        }

        if (contragentService.save(contragentDto).isEmpty()) {
            throw new RuntimeException("Неизвестное состояние");
        }
        return "{\"result\":\"SUCCESS\"}";
    }

    @GetMapping("/edit/person")
    @ResponseBody
    public List<Person> getPerson(@RequestParam(name = "first_name", required = false) String firstName,
                                  @RequestParam(name = "middle_name", required = false) String middleName,
                                  @RequestParam(name = "last_name") String lastName) {

        if (StringUtils.isEmpty(lastName)) {
            throw new RuntimeException("Last name is empty");
        }

        return personService.findAll(firstName, middleName, lastName);
    }

    @PostMapping("/edit/person")
    @ResponseBody
    public Person editPerson(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "first_name", required = false) String firstName,
                             @RequestParam(name = "middle_name", required = false) String middleName,
                             @RequestParam(name = "last_name") String lastName) {

        checkId(id);
        if (StringUtils.isEmpty(lastName)) {
            throw new RuntimeException("Last name is empty");
        }

        return personService.update(id, firstName, middleName, lastName);
    }

    @GetMapping("/edit/address")
    @ResponseBody
    public List<Address> getAddress(@RequestParam(name = "post_index", required = false) String postIndex,
                                    @RequestParam(name = "country") String country,
                                    @RequestParam(name = "city") String city,
                                    @RequestParam(name = "street") String street,
                                    @RequestParam(name = "house_number", required = false) String houseNumber,
                                    @RequestParam(name = "apartrment_number", required = false) String apartrmentNumber) {

        if (StringUtils.isEmpty(country) || StringUtils.isEmpty(city) || StringUtils.isEmpty(street)) {
            throw new RuntimeException("Country/City/Street is empty");
        }
        return addressService.findAll(postIndex, country, city, street, houseNumber, apartrmentNumber);
    }

    @PostMapping("/edit/address")
    @ResponseBody
    public Address editAddress(@RequestParam(name = "id") Long id,
                               @RequestParam(name = "post_index", required = false) Integer postIndex,
                               @RequestParam(name = "country") String country,
                               @RequestParam(name = "city") String city,
                               @RequestParam(name = "street") String street,
                               @RequestParam(name = "house_number", required = false) String houseNumber,
                               @RequestParam(name = "apartrment_number", required = false) String apartrmentNumber) {

        checkId(id);
        if (StringUtils.isEmpty(country) || StringUtils.isEmpty(city) || StringUtils.isEmpty(street)) {
            throw new RuntimeException("Country/City/Street is empty");
        }
        return addressService.update(id, postIndex, country, city, street, houseNumber, apartrmentNumber);
    }

    @GetMapping("/edit/company")
    @ResponseBody
    public List<Organization> getOrganization(@RequestParam(name = "name_company") String nameCompany) {

        if (StringUtils.isEmpty(nameCompany)) {
            throw new RuntimeException("Company name is empty");
        }
        return organizationService.findAll(nameCompany);
    }

    @PostMapping("/edit/company")
    @ResponseBody
    public Organization editOrganization(@RequestParam(name = "id") Long id,
                                         @RequestParam(name = "name_company") String nameCompany) {

        checkId(id);
        if (StringUtils.isEmpty(nameCompany)) {
            throw new RuntimeException("Company name is empty");
        }
        return organizationService.update(id, nameCompany);
    }

    @GetMapping("/edit/employee")
    @ResponseBody
    public List<ContragentDtoEmployee> getEmployee(@RequestParam(name = "first_name", required = false) String firstName,
                                                   @RequestParam(name = "middle_name", required = false) String middleName,
                                                   @RequestParam(name = "last_name") String lastName,
                                                   @RequestParam(name = "position", required = false) String position, HttpServletResponse response) {

        if (StringUtils.isEmpty(lastName)) {
            throw new RuntimeException("Last name is empty");
        }

        //the order of arguments is important
        String searchString = firstName + middleName + lastName + position;
        List<Contragent> contragents = contragentService.searchContragents(searchString.toUpperCase());

        return contragents.stream()
                .filter(contragent -> contragent.getOrganization() != null)
                .map(contragent -> {
                    return new ContragentDtoEmployee(contragent.getId().toString(),
                            contragent.getPerson().getFirstName(),
                            contragent.getPerson().getMiddleName(),
                            contragent.getPerson().getLastName(),
                            contragent.getPersonPosition()
                    );
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/edit/employee")
    @ResponseBody
    public Contragent editEmployee(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "first_name", required = false) String firstName,
                                   @RequestParam(name = "middle_name", required = false) String middleName,
                                   @RequestParam(name = "last_name") String lastName,
                                   @RequestParam(name = "position", required = false) String position) {

        checkId(id);
        if (StringUtils.isEmpty(lastName)) {
            throw new RuntimeException("Last name is empty");
        }
        return contragentService.updateEmployee(id, firstName, middleName, lastName, position);
    }

    private void checkId(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new RuntimeException("ID is empty");
        }
    }
}
