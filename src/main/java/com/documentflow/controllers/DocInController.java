package com.documentflow.controllers;

import com.documentflow.entities.DTOs.DocInDTO;
import com.documentflow.services.DocInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/docs/in")
public class DocInController {

    private DocInService docInService;

    @Autowired
    public void setDocInService(DocInService docInService) {
        this.docInService = docInService;
    }

    @GetMapping()
    public String showIn(Model model) {
        return "docIn";
    }

    @GetMapping("/card")
    public String registrationDoc(
            @RequestParam(name = "id", required = false) Long id,
            Model model) {
        DocInDTO docInDTO = new DocInDTO();
        if (id != null) {
            docInDTO = docInService.findById(id);
        }
        model.addAttribute("docInDTO", docInDTO);
        return "regDoc";
    }

    @PostMapping("/card")
    public String registrationDoc() {
        return "docIn";
    }

    @GetMapping("/del")
    public void delete() {
    }
}
