package com.documentflow.controllers;


import com.documentflow.entities.DocIn;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.dto.DocOutDTO;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;


@Controller
@RequestMapping("old/docs/out")
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
    public void setDocOutService(DocOutService docOutService, DocInService docInService, DocTypeService docTypeService, UserServiceImpl userService,
                                 DocOutUtils docOutUtils, DocInUtils docInUtils, StateService stateService, ContragentServiceImpl contragentService,
                                 TaskService taskService) {
        this.docOutService = docOutService;
        this.docInService=docInService;
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
 //       model.addAttribute("docOutAddress", docTypeService.findAllDocTypes());

        return "doc_out";
    }

    @ResponseBody
    @RequestMapping(value = "/newcard")
    public DocOutDTO createDoc(@RequestParam(value = "docinid", required = false) Long docinid) {
        DocOutDTO docOutDTO=new DocOutDTO();
        if(docinid!=null) {
            DocIn docIn=docInService.findById(docinid);
            docOutDTO.setDocInId(docinid);
            docOutDTO.setDocInRegNumber(docIn.getRegNumber());
        }
        return docOutDTO;
    }

    @RequestMapping(value = "/newcard/submit", method = RequestMethod.POST)
    public String createDocNew(@ModelAttribute (name = "docOutDTO") DocOutDTO docOutDTO) {
        docOutUtils.convertFromDocOutDTONew(docOutDTO);
        return "redirect:/docs/out";

    }

    @RequestMapping("/card")
    public String regEditDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {
        docOutUtils.saveModifiedDocOut(docOutDTO);
          return "redirect:/docs/out";

    }

    @ResponseBody
    @RequestMapping("/card/{id}")
    public DocOutDTO getCard(@PathVariable("id") Long id){
        DocOutDTO docOutDTO=docOutUtils.convertFromDocOut(docOutService.findOneById(id));
        return docOutDTO;
    }

    @PostMapping("/delete")
    public String deleteDoc(@ModelAttribute(name = "docOutDTO") DocOutDTO docOutDTO) {
        Long id=docOutDTO.getId();
        docOutUtils.delDocOut(id);
            return "redirect:/docs/out";
    }
}

