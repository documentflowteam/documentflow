package com.documentflow.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DocInUtils {

    public String getRegNumber() {
        String regNumber;
        regNumber = "ВХ-" + LocalDate.now();
        return regNumber;
    }

    public LocalDateTime convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime()).toLocalDateTime();
    }

    public Date convertToDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return Timestamp.valueOf(date);
    }
}
