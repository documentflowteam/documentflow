package com.documentflow.controllers;


import com.documentflow.entities.DTO.DocOutDTO;
import com.documentflow.entities.DocOut;
import com.documentflow.services.*;
import com.documentflow.utils.DocInUtils;
import com.documentflow.utils.DocOutFilter;
import com.documentflow.utils.DocOutUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;


@Controller
@RequestMapping("/docs/out")
public class DocOutController {

    private DocOutService docOutService;
    private DocInService docInService;
    private DocTypeService docTypeService;
    private UserServiceImpl userService;
    private DocOutUtils docOutUtils;
    private DocInUtils docInUtils;
    private StateService stateService;
    private ContragentServiceImpl contragentService;
    private TaskService taskService;

    @Autowired
    public void setDocOutService(DocOutService docOutService, DocTypeService docTypeService, UserServiceImpl userService,
                                 DocOutUtils docOutUtils, DocInUtils docInUtils, StateService stateService, ContragentServiceImpl contragentService,
                                 TaskService taskService) {
        this.docOutService = docOutService;
        this.docTypeService = docTypeService;
        this.userService = userService;
        this.docOutUtils = docOutUtils;
        this.docInUtils=docInUtils;
        this.stateService = stateService;
        this.contragentService=contragentService;
        this.taskService=taskService;
    }

    @GetMapping()
    public String showAllDocOut(Model model, HttpServletRequest request, @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        model.addAttribute("currentPage", currentPage);

        DocOutFilter filter = new DocOutFilter(request);
        model.addAttribute("filter", filter.getFiltersString());
        Page<DocOutDTO> page = docOutService.findAllByPagingAndFiltering(filter.getSpecification(), PageRequest.of(currentPage-1,15, Sort.Direction.DESC, "createDate"))
                .map(d -> docOutUtils.convertFromDocOut(d));
        model.addAttribute("docs", page);
        model.addAttribute("createDate", LocalDate.now());
        model.addAttribute("creators", userService.getAllUsers());
        model.addAttribute("signers", userService.getAllUsers());
        model.addAttribute("states", stateService.findAllStates());
        model.addAttribute("tasks", taskService.findAll(Pageable.unpaged()));
        model.addAttribute("docTypes", docTypeService.findAllDocTypes());
        model.addAttribute("docOutAddress", docTypeService.findAllDocTypes());
//        model.addAttribute("appendix", docOutService.findOneById(1L).getAppendix());
        return "doc_out";

    }


    @RequestMapping(value = "/newcard")
    public ModelAndView createDoc() {
        ModelAndView result = new ModelAndView("doc_out");
        result.addObject("docOut", new DocOutDTO());
        result.addObject("creators", userService.getAllUsers());
        result.addObject("signers", userService.getAllUsers());
        result.addObject("states", stateService.findAllStates());
        result.addObject("tasks", taskService.findAll(Pageable.unpaged()));
        result.addObject("docTypes", docTypeService.findAllDocTypes());
        result.addObject("docOutAddress", docTypeService.findAllDocTypes());

        return result;
    }

    @RequestMapping(value = "/newcard/submit", method = RequestMethod.POST)
    public String createDocNew(@ModelAttribute (name = "docOut") DocOutDTO docOutDTO) {//},
        //                              @RequestParam(name = "idIn", required = false) Long idIn,
        //                              @RequestParam(name = "idTask", required = false) Long idTask) {

//        docOutUtils.addTaskToDocOutDTO(taskService.findOneById(idTask));


        docOutUtils.convertFromDocOutDTO(docOutDTO);
        docOutUtils.setNewDocOutRegDate(null);
        docOutUtils.setNewDocOutNumber();

        return "redirect:/docs/out";

    }

    @RequestMapping("/card")
 //   @PostMapping("/card")
    public String regEditDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {

        DocOut docOut;
        docOutUtils.saveModifiedDocOut(docOutDTO);
          return "redirect:/docs/out";

    }

    @ResponseBody
    @RequestMapping("/card/{id}")
    public DocOutDTO getCard(@PathVariable("id") Long id) {
        return docOutUtils.getDocOutDTO(id);
    }

    @PostMapping("/delete")
    public String deleteDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {
        docOutUtils.delDocOut(docOutDTO);
            return "redirect:/docs/out";
    }
}

// TODO: saveModifiedDoc, deleteDoc, generateDoc, refuseDoc, sendDoc