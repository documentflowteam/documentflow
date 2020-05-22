package com.documentflow.controllers;


import com.documentflow.entities.DocIn;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.entities.dto.DocOutDTO;
import com.documentflow.services.*;
import com.documentflow.utils.DocInUtils;
import com.documentflow.utils.DocOutFilter;
import com.documentflow.utils.DocOutUtils;
import com.documentflow.utils.TaskUtils;
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
@RequestMapping("/docs/out")
public class DocOutController {

    private DocOutService docOutService;
    private DocInService docInService;
    private DocTypeService docTypeService;
    private UserService userService;
    private DocOutUtils docOutUtils;
    private DocInUtils docInUtils;
    private StateService stateService;
    private ContragentService contragentService;
    private TaskService taskService;
    private TaskUtils taskUtils;

    @Autowired
    public void setDocOutService(DocOutService docOutService) {
        this.docOutService = docOutService;
    }

    @Autowired
    public void setDocInService(DocInService docInService) {
        this.docInService = docInService;
    }

    @Autowired
    public void setDocTypeService(DocTypeService docTypeService) {
        this.docTypeService = docTypeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDocOutUtils(DocOutUtils docOutUtils) {
        this.docOutUtils = docOutUtils;
    }

    @Autowired
    public void setDocInUtils(DocInUtils docInUtils) {
        this.docInUtils = docInUtils;
    }

    @Autowired
    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Autowired
    public void setContragentService(ContragentService contragentService) {
        this.contragentService = contragentService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setTaskUtils(TaskUtils taskUtils) {
        this.taskUtils = taskUtils;
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
    public String createDocNew(@ModelAttribute (name = "docOutDTO") DocOutDTO docOutDTO,
                               @ModelAttribute(name = "check") String check) {
        DocOut docOut=docOutUtils.convertFromDocOutDTONew(docOutDTO);
        if (check!=null) taskUtils.createApprovingTask(docOut);//docOutUtils.addApprovingTask(docOut);

        return "redirect:/docs/out";

    }


    @RequestMapping("/card")
    public String regEditDoc(@ModelAttribute (name = "docOutDTO") DocOutDTO docOutDTO,
                             @ModelAttribute(name = "check") String check) {
        DocOut docOut=docOutUtils.saveModifiedDocOut(docOutDTO);
        if (check!=null) taskUtils.createApprovingTask(docOut);//docOutUtils.addApprovingTask(docOut);
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

