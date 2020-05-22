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
    private final TaskService taskService;
    private final TaskHistoryService taskHistoryService;
    private final TaskTypeService taskTypeService;
    private final StateService stateService;
    private DocInUtils docInUtils;
    private DocOutUtils docOutUtils;

    @Autowired
    public TaskUtils(TaskService taskService, TaskHistoryService taskHistoryService, TaskTypeService taskTypeService, StateService stateService, DocOutUtils docOutUtils) {
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
        this.taskTypeService = taskTypeService;
        this.stateService = stateService;
        this.docOutUtils = docOutUtils;
    }

    @Autowired
    public void setDocInUtils(DocInUtils docInUtils) {
        this.docInUtils = docInUtils;
    }

//    @Autowired
//    public void setDocOutUtils(DocOutUtils docOutUtils) {
//        this.docOutUtils = docOutUtils;
//    }

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

    public Task createApprovingTask(DocOut docOut){//(Long docId, User author, User executor, String taskName, LocalDate endDate, String note) {

        Task task=new Task();
        TaskHistory taskHistory = new TaskHistory();
        TaskType taskType = taskTypeService.getTaskTypeByBusinessKey(BusinessKeyTask.APPROVING.name());
        State taskState = stateService.getStateByBusinessKey(BusinessKeyState.APPROVING.name());
//        Task task = createTask(taskType, docOut.getCreator(), docOut.getSigner(),
//                "На согласование", docOut.getCreateDate().plusDays(5), taskState, docOut.getNote());
        task.setTaskType(taskType);
        task.setAuthor(docOut.getCreator());
        task.setExecutor(docOut.getSigner());
        task.setTaskName("На согласование");
        task.setEndDate(docOut.getCreateDate().plusDays(5));
        task.setState(taskState);
        taskHistory.setTask(task);
        taskHistory.setUser(docOut.getCreator());
        taskHistory.setNote(docOut.getNote());
        taskHistory.setState(taskState);
        docOutUtils.addTaskToDocOut(docOut.getId(), task);
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
