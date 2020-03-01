package com.documentflow.controllers;


import com.documentflow.entities.DTO.DocOutDTO;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.State;
import com.documentflow.entities.User;
import com.documentflow.services.*;
import com.documentflow.utils.DocOutFilter;
import com.documentflow.utils.DocOutUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;


@RestController
@RequestMapping("/docs/out")
public class DocOutController {

    private DocOutService docOutService;
    private DocInService docInService;
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
    public String showAllDocOut(Model model, HttpServletRequest request, @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        model.addAttribute("currentPage", currentPage);
//        Page<DocOut> pageOut = docOutService.findAll(PageRequest.of(currentPage - 1, 20, Sort.Direction.DESC, "createDate"));
//        Page<DocOutDTO> pageDTOs = pageOut.map(d -> new DocOutDTO(d));
//        pageDTOs.stream().map(d -> model.addAttribute(d));
  //      model.addAttribute("docsOut", pageDTOs);

  //      DocOutDTO docOut = new DocOutDTO();


        DocOutFilter filter = new DocOutFilter(request);
        model.addAttribute("filter", filter.getFiltersString());
//        Page<DocInDto> page = docInService.findAll(PageRequest.of(currentPage-1,20, Sort.Direction.ASC, "regDate")).map(d -> docInUtils.convertToDTO(d));
        Page<DocOutDTO> page = docOutService.findAllByPagingAndFiltering(filter.getSpecification(), PageRequest.of(currentPage-1,20, Sort.Direction.DESC, "createDate"))
                .map(d -> docOutUtils.convertFromDocOut(d));
        model.addAttribute("docOut", page);
        model.addAttribute("createDate", LocalDate.now());
        model.addAttribute("creator", userService.getAllUsers());
        model.addAttribute("docTypes", docTypeService.findAllDocTypes());
//        model.addAttribute("appendix", docOutService.findAll().getAppendix());
        return "doc_out";

    }


//    @RequestMapping(value = "/card", method = RequestMethod.POST)
//    public String userCardSubmit(@ModelAttribute DocOutDTO docOutDTO) {
//        docOutService.save(docOutDTO);
//        return "redirect:/docs/out";
//    }


//    @RequestMapping(value = "/card")
//    public ModelAndView editCard(@PathVariable DocOutDTO docOutDTO) {
//        ModelAndView result = new ModelAndView("doc_out");
// //       DocOut docOut=docOutDTO.convertToDocOut();
//        result.addObject("creators", userService.getAllUsers());
//        result.addObject("docType", docTypeService.findAllDocTypes());
//        result.addObject("signer", userService.getAllUsers());
//        return result;
//    }

//    @PostMapping("/card")
//    public String createNewDocOut(@RequestParam(name = "newDocOut") DocOutDTO docOutDTO) {
//        DocOut docOut=new DocOut();
//        docOut.setCreateDate(docOutDTO.getCreateDate());
//        docOut.setCreator(docOutDTO.getCreator());
//        docOut.setDocType(docOutDTO.getDocType());
//        docOut.setSigner(docOutDTO.getSigner());


//        docOutService.save(docOutDTO);
//        return "redirect:/docs/out";
//    }

    @RequestMapping(value = "/card", method = RequestMethod.POST)
    public String userCardSubmit(@ModelAttribute DocOut docOut) {
        docOutService.save(docOut);
        return "redirect:/docs/out";
    }

//    @GetMapping("/edit")
//    public DocOut getDocOut(@RequestParam(name = "editDocOut") DocOutDTO docOutDTO) {
////        DocOut docOut = docOutService.findOneById(docOutDTO.getId());
//        docOutService.update(docOutDTO);
//        return "redirect:/docs/out";
//    }

    @PostMapping("/edit")
    public String editDocOut(@RequestParam(name = "editDocOut") DocOut docOut) {
//        DocOut docOut = docOutService.findOneById(docOutDTO.getId());
            docOutService.save(docOut);
         return "redirect:/docs/out";
    }

    @PostMapping("/delete")
    public String deleteDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {
       DocOut docOut = docOutService.findOneById(docOutDTO.getId());
        if(docOut.getState()!=stateService.getStateById(1)
                || docOut.getState()!=stateService.getStateById(3)
                || docOut.getState()!=stateService.getStateById(4)
                || docOut.getState()!=stateService.getStateById(8)
                || docOut.getState()!=stateService.getStateById(9)) {

            docOut.setState(stateService.getStateById(4));
            docOut.setCreator(userService.getCurrentUser(1));
            docOut.setDocType(docTypeService.getDocTypeById(1));
            docOut.setIsGenerated(false);

            docOutService.save(docOut);
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