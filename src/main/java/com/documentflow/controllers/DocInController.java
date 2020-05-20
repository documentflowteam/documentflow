package com.documentflow.controllers;

import com.documentflow.entities.dto.DocInDto;
import com.documentflow.utils.DocInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("old/docs/in")
public class DocInController {

    private DocInUtils docInUtils;

    @Autowired
    public DocInController(DocInUtils docInUtils) {
        this.docInUtils = docInUtils;
    }

    @GetMapping()
    public String showIn(Model model, HttpServletRequest request,
            @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        docInUtils.showInDocs(model, currentPage, request);
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
