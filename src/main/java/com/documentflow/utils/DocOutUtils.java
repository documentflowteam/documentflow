package com.documentflow.utils;

import com.documentflow.entities.DocOut;
import com.documentflow.services.DocOutService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DocOutUtils {

    public DocOutService docOutService;

    public String getRegOutNumber() {
        Page<DocOut> docOutList= docOutService.findAll(Pageable.unpaged());
        Long newDocOutId=docOutList.getTotalElements()+1;
        String regNumber= "ИСХ-" + newDocOutId.toString() + LocalDate.now();
        return regNumber;
    }
}
