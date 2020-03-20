package com.documentflow.model.enums;

/**
 * Statuses of business process. Serves as a key for the State.
 */
public enum BusinessKeyState {

    REGISTERED ("REGISTERED"), // зарегистрирован
    EXECUTION ("EXECUTION"), // на исполнении
    EXECUTED ("EXECUTED"), // исполнен
    DELETED ("DELETED"), // удалён
    RECALLED ("RECALLED"), // отозван
    PROJECT ("PROJECT"), // проект
    APPROVING ("APPROVING"), // на утверждении
    APPROVED ("APPROVED"), // утверждено
    SENT ("SENT"), // отправлено
    REWORK ("REWORK"), // на доработке
    GENERATED ("GENERATED"), //сгенерирован
    CHECKING ("CHECKING"); // на проверке

    private String name;

    BusinessKeyState(String name) {
        this.name = name;
    }
}
