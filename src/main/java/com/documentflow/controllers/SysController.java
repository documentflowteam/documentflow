package com.documentflow.controllers;


import com.documentflow.services.DepartmentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class SysController {

    @Setter(onMethod_ = {@Autowired})
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public String registrationDoc(Model model) {
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "sys_departments";
    }
}