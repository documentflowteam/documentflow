package com.documentflow.model.enums;

/**
 * The statuses of the task
 */
public enum BusinessKeyTask {

    EXECUTION ("EXECUTION"), // задача на исполнение
    APPROVING ("APPROVING"); // задача на согласование

    private String name;

    BusinessKeyTask(String name) {
        this.name = name;
    }
}
