package com.documentflow.model.enums;

/**
 * Type of software module
 */
public enum BusinessKeyModule {

    DOCUMENTS ("DOCUMENTS"), // документы
    TASKS ("TASKS"), // поручения
    SYS_SETTINGS ("SYS_SETTINGS"); // админка

    private String name;

    BusinessKeyModule(String name) {
        this.name = name;
    }
}
