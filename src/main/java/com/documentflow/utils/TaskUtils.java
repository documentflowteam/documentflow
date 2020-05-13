package com.documentflow.utils;

import com.documentflow.entities.*;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.model.enums.BusinessKeyTask;
import com.documentflow.services.StateService;
import com.documentflow.services.TaskHistoryService;
import com.documentflow.services.TaskService;
import com.documentflow.services.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskUtils {
    private TaskService taskService;
    private TaskHistoryService taskHistoryService;
    private TaskTypeService taskTypeService;
    private StateService stateService;
    private DocInUtils docInUtils;
    private DocOutUtils docOutUtils;

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
    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Autowired
    public void setDocInUtils(DocInUtils docInUtils) {
        this.docInUtils = docInUtils;
    }

    @Autowired
    public void setDocOutUtils(DocOutUtils docOutUtils) {
        this.docOutUtils = docOutUtils;
    }

    public Task setAsRecalled(Task task) {
        task.setState(stateService.getStateByBusinessKey(BusinessKeyState.RECALLED.name()));
        return task;
    }

    public Task createTask(Task task, TaskHistory taskHistory) {
        taskHistoryService.save(taskHistory);
        return taskService.save(task);
    }

    public Task createTask(TaskType taskType, User author, User executor, String taskName, LocalDate endDate, State taskState, String note) {
        Task task = new Task();
        TaskHistory taskHistory = new TaskHistory();

        task.setTaskType(taskType);
        task.setAuthor(author);
        task.setExecutor(executor);
        task.setTaskName(taskName);
        task.setEndDate(endDate);
        task.setState(taskState);

        taskHistory.setTask(task);
        taskHistory.setUser(author);
        taskHistory.setNote(note);
        taskHistory.setState(taskState);

        return createTask(task, taskHistory);
    }

    public Task createApprovingTask(Long docId, User author, User executor, String taskName, LocalDate endDate, String note) {
        TaskType taskType = taskTypeService.getTaskTypeByBusinessKey(BusinessKeyTask.APPROVING.name());
        State taskState = stateService.getStateByBusinessKey(BusinessKeyState.APPROVING.name());
        Task task = createTask(taskType, author, executor, taskName, endDate, taskState, note);
        docOutUtils.addTaskToDocOut(docId, task);
        return task;
    }

    public Task createExecutionTask(Long docId, User author, User executor, String taskName, LocalDate endDate, String note) {
        TaskType taskType = taskTypeService.getTaskTypeByBusinessKey(BusinessKeyTask.EXECUTION.name());
        State taskState = stateService.getStateByBusinessKey(BusinessKeyState.EXECUTION.name());
        Task task = createTask(taskType, author, executor, taskName, endDate, taskState, note);
        docInUtils.addTaskToDocIn(docId, task);
        return task;
    }

}
