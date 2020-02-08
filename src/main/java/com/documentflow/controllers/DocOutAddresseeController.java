package com.documentflow.controllers;

import com.documentflow.entities.DocOutAddressee;
import com.documentflow.services.DocOutAddresseeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/addressee")
public class DocOutAddresseeController {

    private DocOutAddresseeService docOutAddresseeService;
    private DocOutAddressee docOutAddressee;

    @Autowired
    public void setDocOutAddresseeService(DocOutAddresseeService docOutAddresseeService) {
        this.docOutAddresseeService = docOutAddresseeService;
    }

    @GetMapping()
    public Page<DocOutAddressee> showAllAddressee(Model model) {
        return docOutAddresseeService.getPageOfDocOutAddresseeByDesc(PageRequest.of(1, 20, Sort.Direction.DESC));
    }

    @GetMapping("/{id}")
    public String getAddresseeById(@RequestParam(name = "id", required = false) Long id, Model model) {
        List<Long> allAddrById = docOutAddresseeService.getAllById();
             if (id != null && allAddrById.contains(id)) {
            docOutAddressee = docOutAddresseeService.findOneById(id);
        } else return "addressee_form";
        model.addAttribute("addressee", docOutAddressee);
        return docOutAddressee.toString();
    }

    @PostMapping("/{id}")
    public String regAddressById() {
        return "addressee_form";
    }

    @GetMapping("/{addr}")
    public String getAddresseeByName(@RequestParam(name = "addr", required = false) String addr, Model model) {
        List<DocOutAddressee> allAddr=docOutAddresseeService.getAllByName();
        if (addr != null && allAddr.contains(addr)) {
            docOutAddressee = docOutAddresseeService.findOneByName(addr);
        } else return "addressee_form";
        model.addAttribute("addressee", docOutAddressee);
        return docOutAddressee.toString();
    }

    @PostMapping("/{addr}")
    public String regAddressByName() {
        return "addressee_form";
    }

}
