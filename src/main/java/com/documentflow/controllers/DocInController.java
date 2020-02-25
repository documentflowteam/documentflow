package com.documentflow.controllers;

import com.documentflow.entities.*;
import com.documentflow.entities.DTO.DocInDTO;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import com.documentflow.utils.DocInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/docs/in")
public class DocInController {

    private DocInService docInService;
    private DocTypeService docTypeService;
    private DepartmentService departmentService;
    private DocInUtils docInUtils;

    @Autowired
    public DocInController(DocInService docInService, DocTypeService docTypeService,
                           DepartmentService departmentService, UserService userService,
                           DocInUtils docInUtils) {
        this.docInService = docInService;
        this.docTypeService = docTypeService;
        this.departmentService = departmentService;
        this.docInUtils = docInUtils;
    }

    @GetMapping()
    public String showIn(
            Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        model.addAttribute("currentPage", currentPage);
        Page<DocInDTO> page = docInService.findAll(PageRequest.of(currentPage-1,20, Sort.Direction.ASC, "regDate")).map(d -> docInUtils.convertToDTO(d));
        model.addAttribute("docs", page);
        model.addAttribute("docTypes", docTypeService.findAllDocTypes());
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "docIn";
    }

    @ResponseBody
    @RequestMapping("/card/{id}")
    public DocInDTO getCard(@PathVariable("id") Long id, Principal principal) {
        return docInUtils.getDocIn(id, principal.getName());
    }

    @PostMapping("/card")
    public String regEditDoc(@ModelAttribute(name = "doc") DocInDTO docInDTO) {
        DocIn docIn = docInUtils.convertFromDTO(docInDTO);
        if (docIn.getId() == null) {
            docIn.setRegNumber(docInUtils.getRegNumber());
        }
        docInService.save(docIn);
        return "redirect:/docs/in";
    }

    @PostMapping("/del")
    public String delete(@ModelAttribute(name = "doc") DocInDTO docInDTO) {
        docInService.deleteById(docInDTO.getId());
        return "redirect:/docs/in";
    }
}
