package com.documentflow.controllers;

import com.documentflow.services.ContragentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String edit(){
        return "contragent_edit";
    }
}
