package com.documentflow.controllers;

import com.documentflow.entities.dto.DocInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("old/docs/in")
public class DocInController {

    private DocInControllerFacade dicFacade;

    @Autowired
    public DocInController(DocInControllerFacade dicFacade) {
        this.dicFacade = dicFacade;
    }

    @GetMapping()
    public String showIn(Model model, HttpServletRequest request,
            @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        dicFacade.showInDocs(model, currentPage, request);
        return "docIn";
    }

    @ResponseBody
    @RequestMapping("/card/{id}")
    public DocInDto getCard(@PathVariable("id") Long id, Principal principal) {
        return dicFacade.getDocIn(id, principal.getName());
    }

    @ResponseBody
    @RequestMapping("/file/{id}")
    public void getFile(@PathVariable("id") Long id, HttpServletResponse response) {
        dicFacade.getFile(id, response);
    }

    @PostMapping("/card")
    public String regEditDoc(@ModelAttribute(name = "doc") DocInDto docInDto) {
        dicFacade.saveDocIn(docInDto);
        return "redirect:/docs/in";
    }

    @GetMapping("/del/{id}")
    public String deleteDocIn(@PathVariable("id") Long id) {
        dicFacade.deleteDocIn(id);
        return "redirect:/docs/in";
    }
}
