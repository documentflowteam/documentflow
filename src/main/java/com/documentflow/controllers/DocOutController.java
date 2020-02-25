package com.documentflow.controllers;


import com.documentflow.entities.DTO.DocOutDTO;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.State;
import com.documentflow.services.*;
import com.documentflow.utils.DocOutUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/docs/out")
public class DocOutController {

    private DocOutService docOutService;
    private DocTypeService docTypeService;
    private UserServiceImpl userService;
    private DocOutUtils docOutUtils;
    private StateService stateService;

    @Autowired
    public void setDocOutService(DocOutService docOutService, DocTypeService docTypeService, UserServiceImpl userService, DocOutUtils docOutUtils, StateService stateService) {
        this.docOutService = docOutService;
        this.docTypeService = docTypeService;
        this.userService = userService;
        this.docOutUtils = docOutUtils;
        this.stateService = stateService;
    }

    @GetMapping()
    public String showAllDocOut(Model model, @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        model.addAttribute("currentPage", currentPage);
        Page<DocOut> pageOut = docOutService.findAll(PageRequest.of(currentPage - 1, 20, Sort.Direction.DESC, "createDate"));
        Page<DocOutDTO> pageDTOs = pageOut.map(d -> new DocOutDTO(d));
        pageDTOs.stream().map(d -> model.addAttribute(d));
        model.addAttribute("docsOut", pageDTOs);

        DocOutDTO docOut = new DocOutDTO();

        model.addAttribute("docOut", docOut);
        model.addAttribute("createDate", LocalDate.now());
        model.addAttribute("creator", docOut.getCreator());
        model.addAttribute("docTypes", docOut.getDocType());
        model.addAttribute("appendix", docOut.getAppendix());
// TODO: добавить метод findAll с такими параметрами.
//         Page<DocOut> pageOut = docOutService.findAll(PageRequest.of(1,20));
//         model.addAttribute("pageOut", pageOut);
        return "doc_out";
        //      return "addressee_form";
    }


    @PostMapping("/card")
    public String registrationDoc(@RequestParam(name = "newDocOut") DocOutDTO docOutDTO) {
        DocOut docOut = docOutDTO.convertToDocOut();
        docOut.setNumber(docOutUtils.getRegOutNumber());
        State state = stateService.getStateById(1);
        //        State state = stateService.getStateByBusinessKey(BusinessKeyState.REGISTRATED.toString());
        docOut.setState(state);
        docOutService.save(docOutDTO);
        return "redirect:/docs/out";
    }


    @PostMapping("/delete")
    public String deleteDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {
       DocOut docOut = docOutService.findOneById(docOutDTO.getId());
        if(docOutDTO.getState()!=stateService.getStateById(1)
                || docOutDTO.getState()!=stateService.getStateById(3)
                || docOutDTO.getState()!=stateService.getStateById(4)
                || docOutDTO.getState()!=stateService.getStateById(8)
                || docOutDTO.getState()!=stateService.getStateById(9)) {

            docOutDTO.setState(stateService.getStateById(4));
            docOutDTO.setCreator(userService.getCurrentUser(1));
            docOutDTO.setDocType(docTypeService.getDocTypeById(1));
            docOutDTO.setIsGenerated(false);

            docOutService.update(docOutDTO);
            return "redirect:/docs/out";
        } else return "redirect:/docs/out";
    }


    //    @PostMapping("/card")
//    public String registrationDoc(@ModelAttribute(name = "newDocOut") DocOutDTO docOutDTO) {
//        DocOut docOut = docOutDTO.convertToDocOut();
//        docOutService.save(docOut);
//        return "redirect:/docs/out";
//    }
//
//    @PostMapping("/mod/card")
//    public String modificationDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {
//        DocOut docOut = docOutDTO.convertToDocOut();
//        docOutService.save(docOut);
//        return "redirect:/docs/out";
//    }
//
//    @PostMapping("/delete")
//    public String delete(@RequestParam(name = "docOut", required = false) DocOutDTO docOutDTO) {
//        Long id = docOutDTO.getId();
//        docOutService.deleteById(id);
//        return "redirect:/docs/out";
//    }
}