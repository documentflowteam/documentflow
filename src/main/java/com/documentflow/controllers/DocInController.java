package com.documentflow.controllers;

import com.documentflow.entities.dto.DocInDto;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import com.documentflow.utils.DocInFilter;
import com.documentflow.utils.DocInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/docs/in")
public class DocInController {

    private DocInService docInService;
    private DocTypeService docTypeService;
    private DepartmentService departmentService;
    private DocInUtils docInUtils;
    private StateService stateService;

    @Autowired
    public DocInController(DocInService docInService, DocTypeService docTypeService,
                           DepartmentService departmentService, DocInUtils docInUtils,
                           StateService stateService) {
        this.docInService = docInService;
        this.docTypeService = docTypeService;
        this.departmentService = departmentService;
        this.docInUtils = docInUtils;
        this.stateService = stateService;
    }

    @GetMapping()
    public String showIn(
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        docInUtils.showInDocs(model, currentPage, request);
        System.out.println(BusinessKeyState.REGISTRATED.toString());
        System.out.println(BusinessKeyState.REGISTRATED.name());
        return "docIn";
    }

    @ResponseBody
    @RequestMapping("/card/{id}")
    public DocInDto getCard(@PathVariable("id") Long id, Principal principal) {
        return docInUtils.getDocIn(id, principal.getName());
    }

    @PostMapping("/card")
    public String regEditDoc(@ModelAttribute(name = "doc") DocInDto docInDto) {
        docInUtils.saveDocIn(docInDto);
        return "redirect:/docs/in";
    }

    @PostMapping("/del")
    public String delete(@ModelAttribute(name = "doc") DocInDto docInDto) {
        docInUtils.deleteDocIn(docInDto);
        return "redirect:/docs/in";
    }
}
