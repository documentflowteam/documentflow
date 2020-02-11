package com.documentflow.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DocInUtils {

    public String getRegNumber() {
        String regNumber;
        regNumber = "ВХ-" + LocalDate.now();
        return regNumber;
    }
}
