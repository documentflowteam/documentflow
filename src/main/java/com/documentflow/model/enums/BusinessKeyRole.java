package com.documentflow.model.enums;

public enum BusinessKeyRole {

    DOC_IN_REGISTRATION("DOC_IN_REGISTRATION"),
    DOC_IN_TASK_CREATION("DOC_IN_TASK_CREATION"),
    DOC_OUT_CREATION("DOC_OUT_CREATION"),
    DOC_IN_DELETING("DOC_IN_DELETING"),
    DOC_OUT_DELETING("DOC_OUT_DELETING"),
    DOC_IN_READ("DOC_IN_READ"),
    DOC_OUT_READ("DOC_OUT_READ"),
    TASKS_READ("TASKS_READ"),
    DOC_OUT_SENDING("DOC_OUT_SENDING"),
    SYS_READ("SYS_READ"),
    SYS_EDIT("SYS_EDIT");

    private String name;

    BusinessKeyRole(String name) {
        this.name = name;
    }
}
