package com.documentflow.controllers;

import com.documentflow.services.ContragentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contragent")
public class ContragentController {

    @Autowired
    private ContragentService contragentService;


    @GetMapping()
    public ModelAndView searchContragent(@RequestParam(value = "redirectUrl") String redirectUrl,
                                         @RequestParam(value = "searchName") String searchName) {

        ModelAndView modelAndView = createDefaultModelAndView(redirectUrl);

        //to request a list of contractors, you must provide two parameters: the url to which the response will be sent, and the search string
        if (StringUtils.isNotEmpty(redirectUrl) && StringUtils.isNotEmpty(searchName)) {
            modelAndView.addObject("contragents", contragentService.searchContragents(searchName));
        }
        return modelAndView;
    }

    private ModelAndView createDefaultModelAndView(String url) {
        return new ModelAndView(url);
    }
}
