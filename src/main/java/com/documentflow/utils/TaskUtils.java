package com.documentflow.utils;

import com.documentflow.entities.Task;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.StateService;
import com.documentflow.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskUtils {
    private TaskService taskService;
    private StateService stateService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public Task setAsRecalled(Task task) {
        task.setState(stateService.getStateByBusinessKey(BusinessKeyState.RECALLED.name()));
        return task;
    }

}