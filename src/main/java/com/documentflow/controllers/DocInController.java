package com.documentflow.controllers;

import com.documentflow.entities.DocIn;
import com.documentflow.services.DocInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
//        Page<DocIn> page = docInService.findAll(PageRequest.of(1,20));
//        model.addAttribute("page", page);
        return "docIn";
    }

    @GetMapping("/card")
    public String registrationDoc(
            @RequestParam(name = "id", required = false) Long id,
            Model model) {
        DocIn docIn = new DocIn();
        if (id != null) {
            docIn = docInService.findById(id);
        }
        model.addAttribute("docIn", docIn);
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
