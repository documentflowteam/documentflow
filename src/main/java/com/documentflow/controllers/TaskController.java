package com.documentflow.controllers;

import com.documentflow.entities.DocIn;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.services.DocInService;
import com.documentflow.services.DocOutService;
import com.documentflow.services.TaskService;
import com.documentflow.services.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    private TaskTypeService taskTypeService;
    private DocInService docInService;
    private DocOutService docOutService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setTaskTypeService(TaskTypeService taskTypeService) {
        this.taskTypeService = taskTypeService;
    }

    @GetMapping("")
    public String showAllTasks() {
        return "redirect:/tasks/registry/in";
    }

    @GetMapping("/registry/{direction}")
    public String showTasks(Model model, @PathVariable String direction) {
        if (direction.equals("in")) {
            List<Task> tasks = taskService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "id")).getContent();
            model.addAttribute("direction", direction);
            model.addAttribute("tasks", tasks);
            return "tasks_registry";
        } else if (direction.equals("out")) {
            List<Task> tasks = taskService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "id")).getContent();
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
    public String showTask(Model model, @PathVariable Long id) {
        Task task = taskService.findOneById(id);
        String taskType = task.getTaskType().getBusinessKey();
//        if (taskType.equals("EXECUTION")) {
//            DocIn docIn = docInService.findByTask(task);
//            model.addAttribute("docIn", docIn);
//        } else if (taskType.equals("APPROVING")) {
//            DocOut docOut = docOutService.findByTask(task);
//            model.addAttribute("docOut", docOut);
//        }
        model.addAttribute("task", task);
        return "task";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute(name = "task") Task task) {
        taskService.save(task);
        return "redirect:/docs/in";
    }



}
