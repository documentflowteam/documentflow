package com.documentflow.model.enums;

/**
 * Document type
 */
public enum BusinessKeyDoc {

    LETTER ("LETTER"), // письмо
    REQUEST ("REQUEST"), // запрос
    CLAIM ("CLAIM"), // претензия
    NOTIFICATION ("NOTIFICATION"), // уведомление
    COMMERCIAL_PROPOSAL ("COMMERCPIAL_PROPOSAL"), // коммерческое предложение
    RESPONSE_TO_CLAIM ("RESPONSE_TO_CLAIM"), // ответ на претензию
    INVITATION ("INVITATION"); // приглашение

    private String name;

    BusinessKeyDoc(String name) {
        this.name = name;
    }

}
