package com.documentflow.utils;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.entities.dto.DocInDto;
import com.documentflow.entities.DocIn;
import com.documentflow.entities.User;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DocInUtils {

    @Value("${localStorage.storagePath}")
    private String storagePath;
    private DocIn docIn;
    private DocInDto docInDto;
    private StringBuilder path;

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
                      DocOutService docOutService, DocInService docInService,
                      TaskService taskService) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.stateService = stateService;
        this.docTypeService = docTypeService;
        this.docOutService = docOutService;
        this.docInService = docInService;
        this.taskService = taskService;
    }

    public String getRegNumber() {
        String regNumber;
        DocIn prevDocIn = docInService.findFirstByOrderByIdDesc();
        LocalDate date = LocalDate.now();
        if (prevDocIn != null && prevDocIn.getRegDate().getYear() == date.getYear()) {
            Integer number = Integer.parseInt(prevDocIn.getRegNumber().substring(3, prevDocIn.getRegNumber().length()-3));
            regNumber = "ВХ-" + (number+1) + "/" + date.getYear()%100;
        } else {
            regNumber = "ВХ-1/" + date.getYear()%100;
        }
        return regNumber;
    }

    public String getFileExtension(String filename) {
        if (filename.contains(".tar.gz")) {
            return ".tar.gz";
        }
        int ext = filename.lastIndexOf('.');
        return ext > 0 ? filename.substring(ext) : "";
    }

    public String getPath(String regNumber, String filename) {
        path = new StringBuilder();
        path.append(storagePath);
        path.append(regNumber.substring(regNumber.length()-2) + File.separator);
        path.append(regNumber.replace('/', '-') + getFileExtension(filename));
        return path.toString();
    }

    public String getPath(String filename) {
        path = new StringBuilder();
        path.append(storagePath);
        path.append(filename.substring(5, 7) + File.separator);
        path.append(filename);
        return path.toString();
    }

    public String getUserFIO(User user) {
        return user.getLastName() + " " +
                user.getFirstName().substring(0, 1) + "." +
                user.getMiddleName().substring(0, 1);
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

    public DocIn convertFromDTO(DocInDto docInDto) {
        docIn = new DocIn(
                docInDto.getId(),
                docInDto.getRegNumber(),
                convertToLocalDate(docInDto.getRegDate()),
                userService.findOneById(docInDto.getUserId()),
                docTypeService.getDocTypeById(docInDto.getDocTypeId()),
                departmentService.getDepartmentById(docInDto.getDepartmentId()),
                docInDto.getSender(),
                docInDto.getOutgoingNumber(),
                convertToLocalDate(docInDto.getOutgoingDate()),
                docInDto.getContent(),
                docInDto.getPages(),
                docInDto.getAppendix(),
                docInDto.getNote()
        );
        if (docInDto.getDocOutId() != null) {
            docIn.setDocOut(docOutService.findOneById(docInDto.getDocOutId()));
        }
        if (docInDto.getStateId() != null) {
            docIn.setState(stateService.getStateById(docInDto.getStateId()));
        } else {
            docIn.setState(stateService.getStateByBusinessKey(BusinessKeyState.REGISTERED.name()));
        }
        if (docInDto.getTaskId() != null) {
            docIn.setTask(taskService.findOneById(docInDto.getTaskId()));
        }
        return docIn;
    }

    public DocInDto convertToDTO(DocIn docIn) {
        docInDto = new DocInDto(
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
        if (docIn.getDocOut() != null) {
            docInDto.setDocOutId(docIn.getDocOut().getId());
            docInDto.setDocOutNumber(docIn.getDocOut().getNumber());
        }
        if (docIn.getTask() != null) {
            docInDto.setTaskId(docIn.getTask().getId());
        }
        return docInDto;
    }

    public void editState(Long id, BusinessKeyState state) {
        docIn = docInService.findById(id);
        docIn.setState(stateService.getStateByBusinessKey(state.toString()));
        docInService.save(docIn);
    }

    public void addTaskToDocIn(Long id, Task task) {
        docIn = docInService.findById(id);
        docIn.setTask(task);
        docIn.setState(stateService.getStateByBusinessKey(BusinessKeyState.EXECUTION.toString()));
        docInService.save(docIn);
    }

    public void addDocOutToDocIn(Long id, DocOut docOut) {
        docIn = docInService.findById(id);
        docIn.setDocOut(docOut);
        docIn.setState(stateService.getStateByBusinessKey(BusinessKeyState.EXECUTION.toString()));
        docInService.save(docIn);
    }
}
