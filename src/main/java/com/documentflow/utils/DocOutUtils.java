package com.documentflow.utils;

import com.documentflow.entities.DTO.DocOutDTO;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.User;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DocOutUtils {

    private UserServiceImpl userService;
    private StateService stateService;
    private DocTypeService docTypeService;
    private TaskService taskService;
    private DocOutService docOutService;

    @Autowired
    public DocOutUtils(UserServiceImpl userService, StateService stateService, DocTypeService docTypeService,
                       TaskService taskService, DocOutService docOutService) {
        this.docOutService = docOutService;
        this.userService = userService;
        this.stateService = stateService;
        this.docTypeService = docTypeService;
        this.taskService = taskService;

    }


    public String getRegOutNumber() {
        Page<DocOut> docOutList= docOutService.findAll(Pageable.unpaged());
        Long newDocOutId=docOutList.getTotalElements()+1;
        String regNumber= "ИСХ-" + newDocOutId.toString() + LocalDate.now();
        return regNumber;
    }

    public DocOut convertFromDocOutDTO(DocOutDTO docOutDTO) {

        DocOut docOut = new DocOut();
                docOut.setId(docOutDTO.getId());
                docOut.setCreateDate(docOutDTO.getCreateDate());
                docOut.setCreator(docOutDTO.getCreator());
                docOut.setDocType(docTypeService.getDocTypeById(docOutDTO.getDocTypeId()));
      //          docOut.setDocType(docOutDTO.getDocType());
      //          docOut.setDocTypeId(docTypeService.getDocTypeById(1));
                docOut.setSigner(docOutDTO.getSigner());
                docOut.setContent(docOutDTO.getContent());
        docOut.setPages(docOutDTO.getPages());
        docOut.setAppendix(docOutDTO.getAppendix());
        docOut.setNote(docOutDTO.getNote());
        if (docOutDTO.getIsGenerated()==null) {
            docOut.setIsGenerated(false);
        } else docOut.setIsGenerated(docOutDTO.getIsGenerated());

//        if (docOutDTO.getNumber()==null) {
            docOut.setNumber("б/н");
//        } else docOut.setNumber(docOutDTO.getNumber());
 //       docOut.setNumber(docOutDTO.getNumber());

        docOut.setRegDate(null);

//        if (docOutDTO.getState() ==null) {
//            docOut.setState(stateService.getStateById(1));
//        } else {docOut.setState(docOutDTO.getState());
//        }
 //       docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.PROJECT.toString()));
        docOut.setState(docOutDTO.getState());
        docOut.setTask(docOutDTO.getTask());


//        if (docOutDTO.getTask() != null) {
//            docOutDTO.setTask(taskService.findOneById(docOutDTO.getTaskId()));
//        } else docOutDTO.setTask(null);
        return docOut;
    }

    public DocOutDTO convertFromDocOut(DocOut docOut) {
        DocOutDTO docOutDTO = new DocOutDTO(
                docOut.getId(),
                docOut.getCreateDate(),
                docOut.getCreator(),
                docOut.getCreator().getId(),
                getUserFIO(docOut.getCreator()),
                docOut.getDocType().getName(),
                docOut.getDocType(),
                docOut.getDocType().getId(),
                docOut.getSigner(),
                docOut.getContent(),
                docOut.getPages(),
                docOut.getAppendix(),
                docOut.getNote(),
                docOut.getIsGenerated(),
                docOut.getNumber(),
                docOut.getRegDate(),
                docOut.getState());
 //               docOut.getState().getId());

        if (docOut.getTask() != null) {
            docOutDTO.setTask(docOut.getTask());
            docOutDTO.setTaskId(docOut.getTask().getId());
        }
        return docOutDTO;
    }

    public String getUserFIO(User user) {
        return user.getLastName() + " " +
                user.getFirstName().substring(0, 1) + "." +
                user.getMiddleName().substring(0, 1);
    }

    public DocOutDTO getDocOutDTO(Long id) {
        DocOutDTO docOutDTO = convertFromDocOut(docOutService.findOneById(id));
       return docOutDTO;
    }

}

