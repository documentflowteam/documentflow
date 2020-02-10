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
    private List<DocOutAddressee> allAddr;

    @Autowired
    public void setDocOutAddresseeService(DocOutAddresseeService docOutAddresseeService) {
        this.docOutAddresseeService = docOutAddresseeService;
    }



    @GetMapping()
    public Page<DocOutAddressee> showAllAddressee(Model model) {
        return docOutAddresseeService.getPageOfDocOutAddresseeByDesc(PageRequest.of(1, 20, Sort.Direction.DESC, "address"));
    }

    //при получении id (>0) адреса, который  уже нахолится в списке адресов, выводим этот адрес.
    //В противном случае выводим форму для внесения нового адреса в БД.
    @GetMapping("/{id}")
    public String getAddresseeById(@RequestParam(name = "id", required = false) Long id, Model model) {
       allAddr=docOutAddresseeService.getAllById(id);
        DocOutAddressee docOutAddressee = docOutAddresseeService.findOneById(id);
             if (id <0 && allAddr==null) {
            return "addressee_form";
        } else {
                 model.addAttribute("addressee", docOutAddressee);
                return "doc_out";
             }
    }

    @PostMapping("/edit/{id}")
    public String regAddressById() {
        return "doc_out";
    }

    //при получении адреса, который не null и уже нахолится в списке адресов, выводим этот адрес.
    //В противном случае выводим форму для внесения нового адреса в БД.
    @GetMapping("/{addr}")
    public String getAddresseeByName(@RequestParam(name = "addr", required = false) String addr, Model model) {
        allAddr=docOutAddresseeService.getAllByName(addr);
        DocOutAddressee docOutAddressee = docOutAddresseeService.findOneByName(addr);
        if (allAddr != null ) {
            model.addAttribute("addressee", docOutAddressee);
            return "doc_out";
        } else
            return "addressee_form";

    }

    @PostMapping("/edit/{addr}")
    public String regAddressByName() {
        return "doc_out";
    }

}
