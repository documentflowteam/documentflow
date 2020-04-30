package com.documentflow.utils;

import com.documentflow.entities.DocIn;
import com.documentflow.entities.dto.DocInDto;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import com.documentflow.utils.cloudBox.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Component
public class DocInControllerFacade {

    private DocIn docIn;
    private DocInDto docInDto;

    private UserService userService;
    private DepartmentService departmentService;
    private StateService stateService;
    private DocTypeService docTypeService;
    private TaskUtils taskUtils;
    private DocInService docInService;
    private DocOutUtils docOutUtils;
    private FileStorageService fileStorageService;
    private DocInUtils docInUtils;

    @Autowired
    public DocInControllerFacade(UserService userService, DepartmentService departmentService,
                      StateService stateService, DocTypeService docTypeService,
                      DocInService docInService, TaskUtils taskUtils, DocOutUtils docOutUtils,
                      FileStorageService fileStorageService, DocInUtils docInUtils) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.stateService = stateService;
        this.docTypeService = docTypeService;
        this.docInService = docInService;
        this.taskUtils = taskUtils;
        this.fileStorageService = fileStorageService;
        this.docInUtils = docInUtils;
        this.docOutUtils = docOutUtils;
    }

    public DocInDto getDocIn(Long id, String login) {
        docInDto = new DocInDto();
        if (id > 0) {
            docInDto = docInUtils.convertToDTO(docInService.findById(id));
        } else {
            docInDto.setUserFIO(docInUtils.getUserFIO(userService.getCurrentUser(1)));
            docInDto.setUserId(userService.getCurrentUser(1).getId()); //Заменить на релаьно авторизованного юзера
        }
        return docInDto;
    }

    public void saveDocIn(DocInDto docInDto) {
        docIn = docInUtils.convertFromDTO(docInDto);
        if (docIn.getId() == null) {
            docIn.setRegNumber(docInUtils.getRegNumber());
            docIn.setState(stateService.getStateByBusinessKey(BusinessKeyState.REGISTERED.name()));
        }
        if (docInDto.getFile() != null) {
            String path = docInUtils.getPath(docIn.getRegNumber(), docInDto.getFile().getOriginalFilename());
            docIn.setAppendix(path);
            fileStorageService.upload(docInDto.getFile(), path);
        }
        docInService.save(docIn);
    }

    public void deleteDocIn(DocInDto docInDto) {
        docInUtils.editState(docInDto.getId(), BusinessKeyState.DELETED);
        if (docInDto.getTaskId() != null) {
            taskUtils.setAsRecalled(docInService.findById(docInDto.getId()).getTask());
        }
        if (docInDto.getDocOutId() != null) {
            docOutUtils.delDocOut(docInDto.getDocOutId());
        }
    }

    public void showInDocs(Model model, Integer currentPage, HttpServletRequest request) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        model.addAttribute("currentPage", currentPage);
        DocInFilter filter = new DocInFilter(request);
        model.addAttribute("filter", filter.getFiltersStr());
        Page<DocInDto> page = docInService.findAllByPagingAndFiltering(filter.getSpecification(), PageRequest.of(currentPage-1,20, Sort.Direction.ASC, "regDate"))
                .map(d -> docInUtils.convertToDTO(d));
        model.addAttribute("docs", page);
        model.addAttribute("states", stateService.findAllStates());
        model.addAttribute("docTypes", docTypeService.findAllDocTypes());
        model.addAttribute("departments", departmentService.findAllDepartments());
    }
}
