package com.documentflow.controllers;

import com.documentflow.entities.Contragent;
import com.documentflow.entities.Person;
import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.services.AddressService;
import com.documentflow.services.ContragentService;
import com.documentflow.services.OrganizationService;
import com.documentflow.services.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contragent")
public class ContragentController {

    @Autowired
    private ContragentService contragentService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PersonService personService;

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

    @GetMapping("/edit")
    public String getEdit(){
        return "contragent_edit";
    }

    @PostMapping("/edit")
    public String addNewContragent(@RequestBody ContragentDto contragentDto){

        //TODO подумать над пустыми полями в contragentDto

        List<Long> idAddresses = addressService.save(contragentDto.getAddress());
        String searchString;

        if("person".equals(contragentDto.getTypePerson())){

            Person person = new Person(contragentDto.getParameters().getFirstName(),
                    contragentDto.getParameters().getMiddleName(),
                    contragentDto.getParameters().getLastName());
            person = personService.save(person);

            if(person == null) {
                //TODO доделать обработку NULL
            }

            searchString = person.getFirstName() + person.getMiddleName() + person.getLastName();
            long idPerson = person.getId();

            List<Contragent> contragents = idAddresses.stream()
                    .map(item -> new Contragent(idPerson, searchString,item))
                    .collect(Collectors.toList());

            List<Long> idContragents = contragentService.save(contragents);
            //TODO доделать обработку списка ID
        }
        if("company".equals(contragentDto.getTypePerson())){
            //TODO доделать для юр.лиц
        }

        return "contragent_edit";
    }
}
