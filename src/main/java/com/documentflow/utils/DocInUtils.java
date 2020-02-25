package com.documentflow.utils;

import com.documentflow.entities.DTO.DocInDTO;
import com.documentflow.entities.DocIn;
import com.documentflow.entities.User;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DocInUtils {

    private UserService userService;
    private DepartmentService departmentService;
    private StateService stateService;
    private DocTypeService docTypeService;
    private TaskService taskService;
    private DocOutService docOutService;
    private DocInService docInService;

    @Autowired
    public DocInUtils(UserService userService, DepartmentService departmentService,
                      StateService stateService, DocTypeService docTypeService,
                      DocOutService docOutService, DocInService docInService) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.stateService = stateService;
        this.docTypeService = docTypeService;
        this.docOutService = docOutService;
        this.docInService = docInService;
    }

    public String getRegNumber() {
        String regNumber;
        DocIn docIn = docInService.findFirstByOrderByIdDesc();
        LocalDate date = LocalDate.now();
        if (docIn != null && docIn.getRegDate().getYear() == date.getYear()) {
            Integer number = Integer.parseInt(docIn.getRegNumber().substring(3, docIn.getRegNumber().length()-3));
            regNumber = "ВХ-" + (number+1) + "/" + date.getYear()%100;
        } else {
            regNumber = "ВХ-1/" + date.getYear()%100;
        }
        return regNumber;
    }

    public DocInDTO getDocIn(Long id, String login) {
        DocInDTO docIn = new DocInDTO();
        if (id > 0) {
            docIn = convertToDTO(docInService.findById(id));
        } else {
            docIn.setUserFIO(getUserFIO(userService.getCurrentUser(1)));
            docIn.setUserId(userService.getCurrentUser(1).getId()); //Заменить на релаьно авторизованного юзера
        }
        return docIn;
    }

    private LocalDateTime convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime()).toLocalDateTime();
    }

    private Date convertToDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return Timestamp.valueOf(date);
    }

    public String getUserFIO(User user) {
        return user.getLastName() + " " +
                user.getFirstName().substring(0, 1) + "." +
                user.getMiddleName().substring(0, 1);
    }

    public DocIn convertFromDTO(DocInDTO docInDTO) {
        DocIn docIn = new DocIn(
                docInDTO.getId(),
                docInDTO.getRegNumber(),
                convertToLocalDate(docInDTO.getRegDate()),
                userService.findOneById(docInDTO.getUserId()),
                docTypeService.getDocTypeById(docInDTO.getDocTypeId()),
                departmentService.getDepartmentById(docInDTO.getDepartmentId()),
                docInDTO.getSender(),
                docInDTO.getOutgoingNumber(),
                convertToLocalDate(docInDTO.getOutgoingDate()),
                docInDTO.getContent(),
                docInDTO.getPages(),
                docInDTO.getAppendix(),
                docInDTO.getNote()
        );
//        if (docInDTO.getDocOutId() != null) {
//            docIn.setDocOut(docOutService.findOneById(docInDTO.getDocOutId()));
//        }
        if (docInDTO.getStateId() != null) {
            docIn.setState(stateService.getStateById(docInDTO.getStateId()));
        } else {
//            docIn.setState(stateService.getStateByBusinessKey(BusinessKeyState.REGISTRATED.toString()));
            docIn.setState(stateService.getStateById(1));
        }
        if (docInDTO.getTaskId() != null) {
            docIn.setTask(taskService.findOneById(docInDTO.getTaskId()));
        }
        return docIn;
    }

    public DocInDTO convertToDTO(DocIn docIn) {
        DocInDTO docInDTO = new DocInDTO(
                docIn.getId(),
                docIn.getRegNumber(),
                convertToDate(docIn.getRegDate()),
                docIn.getUser().getId(),
                getUserFIO(docIn.getUser()),
                docIn.getDocType().getId(),
                docIn.getDocType().getName(),
                docIn.getDepartment().getId(),
                docIn.getSender(),
                docIn.getOutgoingNumber(),
                convertToDate(docIn.getOutgoingDate()),
                docIn.getContent(),
                docIn.getPages(),
                docIn.getAppendix(),
                docIn.getNote(),
                docIn.getState().getName(),
                docIn.getState().getId()
        );
//        if (docIn.getDocOut() != null) {
//            docInDTO.setDocOutId(docIn.getDocOut().getId());
//        }
        if (docIn.getTask() != null) {
            docInDTO.setTaskId(docIn.getTask().getId());
        }
        return docInDTO;
    }
}
