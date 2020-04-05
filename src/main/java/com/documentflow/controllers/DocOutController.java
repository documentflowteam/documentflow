package com.documentflow.controllers;


import com.documentflow.entities.DTO.DocOutDTO;
import com.documentflow.entities.DocIn;
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

        return "doc_out";
    }


    @RequestMapping(value = "/newcard")
    public ModelAndView createDoc(@RequestParam(name = "idIn", required = false) Long idIn,
                                  @RequestParam(name = "idTask", required = false) Long idTask) {
        ModelAndView result = new ModelAndView("doc_out");
        result.addObject("docOutDTO", new DocOutDTO());
        result.addObject("creators", userService.getAllUsers());
        result.addObject("signers", userService.getAllUsers());
        result.addObject("states", stateService.findAllStates());
        result.addObject("docTypes", docTypeService.findAllDocTypes());
        result.addObject("docOutAddress", docTypeService.findAllDocTypes());
        if(idIn!=null) result.addObject("docIn", docInService.findById(idIn));
        if(idTask!=null) result.addObject("task", taskService.findOneById(idTask));

        return result;
    }

    @RequestMapping(value = "/newcard/submit", method = RequestMethod.POST)
    public String createDocNew(@ModelAttribute (name = "docOutDTO") DocOutDTO docOutDTO,
                               @RequestParam(name = "idIn", required = false) Long idIn)  {

        docInUtils.addDocOutToDocIn(idIn, docOutUtils.convertFromDocOutDTO(docOutDTO));
        docOutUtils.convertFromDocOutDTONew(docOutDTO);
         return "redirect:/docs/out";

    }

    @RequestMapping("/card")
    public String regEditDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO,
                             @RequestParam(name = "idIn", required = false) Long idIn,
                             @RequestParam(name = "idTask", required = false) Long idTask) {
        DocOut docOut=docOutUtils.convertFromDocOutDTO(docOutDTO);
        if(idTask!=null) docOutUtils.addTaskToDocOutDTO(docOutDTO.getId(), taskService.findOneById(idTask));
        if(idIn!=null)docInUtils.addDocOutToDocIn(idIn, docOut);

        docOutUtils.saveModifiedDocOut(docOutDTO);
          return "redirect:/docs/out";

    }

    @ResponseBody
    @RequestMapping("/card/{id}")
    public DocOutDTO getCard(@PathVariable("id") Long id,
                             @RequestParam(name = "idIn", required = false) Long idIn)  {

        if(idIn!=null) {
            DocIn docIn=docInService.findById(idIn);
        }
        return docOutUtils.getDocOutDTO(id);
    }

    @PostMapping("/delete")
    public String deleteDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {
        Long id=docOutDTO.getId();
        docOutUtils.delDocOut(id);
            return "redirect:/docs/out";
    }
}

