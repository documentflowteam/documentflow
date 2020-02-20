package com.documentflow.controllers;

import com.documentflow.entities.*;
import com.documentflow.model.enums.BusinessKeyTask;
import com.documentflow.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private UserServiceImpl userService;
    private TaskService taskService;
    private TaskHistoryService taskHistoryService;
    private TaskTypeService taskTypeService;
    private DocInService docInService;
    private DocOutService docOutService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setTaskHistoryService(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

    @Autowired
    public void setTaskTypeService(TaskTypeService taskTypeService) {
        this.taskTypeService = taskTypeService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDocOutService(DocOutService docOutService) {
        this.docOutService = docOutService;
    }

    @Autowired
    public void setDocInService(DocInService docInService) {
        this.docInService = docInService;
    }

    @GetMapping("")
    public String showAllTasks() {
        return "redirect:/tasks/registry/in";
    }

    @GetMapping("/registry/{direction}")
    public String showTasks(Principal principal, Model model, @PathVariable String direction) {
        User user = null;
        if (principal != null) {
            user = userService.getUserByUsername(principal.getName());
        }
        if (direction.equals("in")) {
            //List<Task> tasks = taskService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "id")).getContent();
            List<Task> tasks = taskService.findAllByExecutor(user);
            model.addAttribute("direction", direction);
            model.addAttribute("tasks", tasks);
            return "tasks_registry";
        } else if (direction.equals("out")) {
            //List<Task> tasks = taskService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "id")).getContent();
            List<Task> tasks = taskService.findAllByAuthor(user);
            model.addAttribute("direction", direction);
            model.addAttribute("tasks", tasks);
            return "tasks_registry";
        } else {
            return "redirect:/tasks/registry/in";
        }
    }

    @GetMapping("/card")
    public String ShowTaskCreationForm(Model model,
                               @RequestParam(name = "type") Integer typeId,
                               @RequestParam(name = "docNum") Long docId) {
        Task task = new Task();
        task.setTaskType(taskTypeService.getTaskTypeById(typeId));
        model.addAttribute("task", task);
        return "task_form";
    }

    @GetMapping("/card/{id}")
    public String showTask(Principal principal, Model model, @PathVariable Long id) {
        User user = null;
        if (principal != null) {
            user = userService.getUserByUsername(principal.getName());
        }

        Task task = taskService.findOneById(id);
        TaskHistory newTaskHistory = new TaskHistory();
        List<TaskHistory> taskHistory = taskHistoryService.findAllByTask(task);

        String taskType = task.getTaskType().getBusinessKey();
        if (taskType.equals(BusinessKeyTask.EXECUTION.name())) {
            DocIn docIn = docInService.findById(1L);
            model.addAttribute("docIn", docIn);
        } else if (taskType.equals(BusinessKeyTask.APPROVING.name())) {
            DocOut docOut = docOutService.findOneById(1L);
            model.addAttribute("docOut", docOut);
        }

        newTaskHistory.setUser(user);
        newTaskHistory.setTask(task);

        model.addAttribute("task", task);
        model.addAttribute("taskHistory", taskHistory);
        model.addAttribute("newTaskHistory", newTaskHistory);
        return "task";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute(name = "task") Task task) {
        taskService.save(task);
        return "redirect:/docs/in";
    }

    @PostMapping("/save/history")
    public String saveTaskHistory(@ModelAttribute(name = "newTaskHistory") TaskHistory taskHistory) {
        Task task = taskHistory.getTask();
        task.setState(taskHistory.getState());
        taskService.save(task);
        taskHistoryService.save(taskHistory);
        return "redirect:/tasks/";
    }



}
