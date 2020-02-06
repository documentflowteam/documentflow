package com.documentflow.controllers;


import com.documentflow.entities.DocOut;
import com.documentflow.services.DocOutService;
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
@RequestMapping("/docs/out")
public class DocOutController {

    private DocOutService docOutService;

    @Autowired
    public void setDocOutService(DocOutService docOutService) {
        this.docOutService = docOutService;
    }

    @GetMapping()
    public String showAllDocOut(Model model) {
        Page<DocOut> pageOut = docOutService.findAll(PageRequest.of(1,20));
        model.addAttribute("pageOut", pageOut);
        return "docOut";
    }

    @GetMapping("/card")
    public String registrationDoc(
            @RequestParam(name = "id", required = false) Long id,
            Model model) {
        DocOut docOut = new DocOut();
        if (id != null) {
            docOut = docOutService.findOneById(id);
        }
        model.addAttribute("docOut", docOut);
        return "regDocOut";
    }

    @PostMapping("/card")
    public String registrationDocOut() {
        return "docOut";
    }

}
