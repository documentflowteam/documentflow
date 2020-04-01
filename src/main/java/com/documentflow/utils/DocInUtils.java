package com.documentflow.utils;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.entities.dto.DocInDto;
import com.documentflow.entities.DocIn;
import com.documentflow.entities.User;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DocInUtils {

    private DocIn docIn;
    private DocInDto docInDto;
    private UserService userService;
    private DepartmentService departmentService;
    private StateService stateService;
    private DocTypeService docTypeService;
    private TaskUtils taskUtils;
    private TaskService taskService;
    private DocOutService docOutService;
    private DocInService docInService;

    @Autowired
    public DocInUtils(UserService userService, DepartmentService departmentService,
                      StateService stateService, DocTypeService docTypeService,
                      DocOutService docOutService, DocInService docInService,
                      TaskUtils taskUtils, TaskService taskService) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.stateService = stateService;
        this.docTypeService = docTypeService;
        this.docOutService = docOutService;
        this.docInService = docInService;
        this.taskUtils = taskUtils;
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

    public DocInDto getDocIn(Long id, String login) {
        docInDto = new DocInDto();
        if (id > 0) {
            docInDto = convertToDTO(docInService.findById(id));
        } else {
            docInDto.setUserFIO(getUserFIO(userService.getCurrentUser(1)));
            docInDto.setUserId(userService.getCurrentUser(1).getId()); //Заменить на релаьно авторизованного юзера
        }
        return docInDto;
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

    public void saveDocIn(DocInDto docInDto) {
        docIn = convertFromDTO(docInDto);
        if (docIn.getId() == null) {
            docIn.setRegNumber(getRegNumber());
            docIn.setState(stateService.getStateByBusinessKey(BusinessKeyState.REGISTERED.name()));
        }
        docInService.save(docIn);
    }

    public void deleteDocIn(DocInDto docInDto) {
        editState(docInDto.getId(), BusinessKeyState.DELETED);
        if (docInDto.getTaskId() != null) {
            taskUtils.setAsRecalled(docInService.findById(docInDto.getId()).getTask());
        }
//        Добавить методы удаления связанного исх. документа.
    }

    public void showInDocs(Model model, Integer currentPage, HttpServletRequest request) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        model.addAttribute("currentPage", currentPage);
        DocInFilter filter = new DocInFilter(request);
        model.addAttribute("filter", filter.getFiltersStr());
        Page<DocInDto> page = docInService.findAllByPagingAndFiltering(filter.getSpecification(), PageRequest.of(currentPage-1,20, Sort.Direction.ASC, "regDate"))
                .map(d -> convertToDTO(d));
        model.addAttribute("docs", page);
        model.addAttribute("states", stateService.findAllStates());
        model.addAttribute("docTypes", docTypeService.findAllDocTypes());
        model.addAttribute("departments", departmentService.findAllDepartments());
    }
}
