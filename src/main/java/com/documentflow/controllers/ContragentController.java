package com.documentflow.controllers;

import com.documentflow.entities.dto.ContragentDto;
import com.documentflow.services.ContragentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/contragent")
public class ContragentController {

    @Autowired
    private ContragentService contragentService;

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

        System.out.println(contragentDto);

        return "contragent_edit";
    }
}
