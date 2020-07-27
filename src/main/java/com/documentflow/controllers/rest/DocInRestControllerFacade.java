package com.documentflow.controllers.rest;

import com.documentflow.entities.DocIn;
import com.documentflow.entities.User;
import com.documentflow.entities.dto.DocInDto;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import com.documentflow.utils.DocInFilter;
import com.documentflow.utils.DocInUtils;
import com.documentflow.utils.DocOutUtils;
import com.documentflow.utils.TaskUtils;
import com.documentflow.utils.fileStorage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;

@Component
public class DocInRestControllerFacade {

    private DocIn docIn;
    private DocInDto docInDto;

    private UserService userService;
    private StateService stateService;
    private TaskUtils taskUtils;
    private DocInService docInService;
    private DocInUtils docInUtils;
    private DocOutUtils docOutUtils;
    private FileStorageService fileStorageService;


    @Autowired
    public DocInRestControllerFacade(UserService userService, StateService stateService,
                                     DocInService docInService, TaskUtils taskUtils, DocOutUtils docOutUtils,
                                     FileStorageService fileStorageService, DocInUtils docInUtils) {
        this.userService = userService;
        this.stateService = stateService;
        this.docInService = docInService;
        this.taskUtils = taskUtils;
        this.fileStorageService = fileStorageService;
        this.docInUtils = docInUtils;
        this.docOutUtils = docOutUtils;
    }

    public ResponseEntity<Page<DocInDto>> getPage(HttpServletRequest request, Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        DocInFilter filter = new DocInFilter(request);
        Page<DocInDto> page = docInService.findAllByPagingAndFiltering(filter.getSpecification(), PageRequest.of(currentPage-1,20, Sort.Direction.ASC, "regDate"))
                .map(d -> docInUtils.convertToDTO(d));
        return new ResponseEntity<>(page, filter.getMap(), HttpStatus.OK);
    }

    public ResponseEntity<DocInDto> getDocIn(Long id, String login) {
        docInDto = new DocInDto();
        if (id > 0) {
            if (!docInService.existsById(id)) {
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            docInDto = docInUtils.convertToDTO(docInService.findById(id));
        } else {
            User user = userService.getUserByUsername(login);
            docInDto.setUserFIO(docInUtils.getUserFIO(user));
            docInDto.setUserId(user.getId());
        }
        return new  ResponseEntity<>(docInDto, HttpStatus.OK);
    }

    public ResponseEntity<?> saveDocIn(DocInDto docInDto) throws FileSystemException {
        docIn = docInUtils.convertFromDTO(docInDto);
        if (docIn.getId() == null) {
            docIn.setRegNumber(docInUtils.getRegNumber());
            docIn.setState(stateService.getStateByBusinessKey(BusinessKeyState.REGISTERED.name()));
        }
        if (docInDto.getFile() != null) {
            String path = docInUtils.getPath(docIn.getRegNumber(), docInDto.getFile().getOriginalFilename());
            docIn.setAppendix(Paths.get(path).getFileName().toString());
            fileStorageService.upload(docInDto.getFile(), path);
        }
        docInService.save(docIn);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteDocIn(Long id) {
        docIn = docInService.findById(id);
        docInUtils.editState(id, BusinessKeyState.DELETED);
        if (docIn.getTask() != null) {
            taskUtils.setAsRecalled(docIn.getTask());
        }
        if (docIn.getDocOut() != null) {
            docOutUtils.delDocOut(docIn.getDocOut().getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public void getFile(Long id, HttpServletResponse response) throws FileNotFoundException {
        fileStorageService.download(docInUtils.getPath(docInService.findById(id).getAppendix()), response);
    }
}
