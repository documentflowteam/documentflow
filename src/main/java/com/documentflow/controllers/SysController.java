package com.documentflow.controllers;


import com.documentflow.entities.User;
import com.documentflow.services.DepartmentService;
import com.documentflow.services.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys")
public class SysController {

    @Setter(onMethod_ = {@Autowired})
    private DepartmentService departmentService;

    @Setter(onMethod_ = {@Autowired})
    private UserService userService;

    @GetMapping("/departments")
    public String departmentsTable(Model model) {
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "sys_departments";
    }

    @GetMapping("/users")
    public String usersTable(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "sys_users";
    }

    @RequestMapping(value = "/users/card/{id}")
    public ModelAndView editCard(@PathVariable int id) {
        ModelAndView result = new ModelAndView("sys_users_form");
        result.addObject("departments", departmentService.findAllDepartments());
        result.addObject("user", userService.findOneById(id));
        result.addObject("usersList", userService.getAllUsers());
        return result;
    }

    // TODO: сделать компактнее, объединить с редактированием
    @RequestMapping(value = "/users/card/")
    public ModelAndView createCard() {
        ModelAndView result = new ModelAndView("sys_users_form");
        result.addObject("departments", departmentService.findAllDepartments());
        result.addObject("userslist", userService.getAllUsers());
        result.addObject("user", new User());
        return result;
    }
    @RequestMapping(value = "/users/card/delete/{id}",method = RequestMethod.GET)
    public String deleteCard(@PathVariable int id) {
        User currentUser = userService.findOneById(id);

        if(currentUser != null) {
            userService.delete(currentUser);
        }
        return "redirect:/sys/users";
    }

    @RequestMapping(value = "/users/card/submit", method = RequestMethod.POST)
    public String userCardSubmit(@ModelAttribute User user) {
        userService.saveOrUpdate(user);
        return "redirect:/sys/users";
    }
}