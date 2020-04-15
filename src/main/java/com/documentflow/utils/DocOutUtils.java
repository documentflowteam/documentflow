package com.documentflow.utils;

import com.documentflow.entities.DTO.DocOutDTO;
import com.documentflow.entities.DocIn;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import com.documentflow.model.enums.BusinessKeyState;
import com.documentflow.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DocOutUtils {

    private DocOut docOut;
    private DocOutDTO docOutDTO;
    private UserServiceImpl userService;
    private StateService stateService;
    private DocTypeService docTypeService;
    private TaskService taskService;
    private DocOutService docOutService;
    private DocInUtils docInUtils;

    @Autowired
    public DocOutUtils(UserServiceImpl userService, StateService stateService, DocTypeService docTypeService,
                       TaskService taskService, DocOutService docOutService, DocInUtils docInUtils) {
        this.docOutService = docOutService;
        this.userService = userService;
        this.stateService = stateService;
        this.docTypeService = docTypeService;
        this.taskService = taskService;
        this.docInUtils=docInUtils;
    }


    public String getRegOutNumber() {
        Page<DocOut> docOutList= docOutService.findAll(Pageable.unpaged());
        Long newDocOutId=docOutList.getTotalElements()+1;
        String regNumber= "ИСХ-" + newDocOutId.toString() + LocalDate.now();
        return regNumber;
    }

    public DocOut convertFromDocOutDTO(DocOutDTO docOutDTO) {

         docOut = new DocOut();
  //              docOut.setId(docOutDTO.getId());
                docOut.setCreateDate(docOutDTO.getCreateDate());
                docOut.setCreator(docOutDTO.getCreator());
                docOut.setDocType(docTypeService.getDocTypeById(docOutDTO.getDocTypeId()));
                docOut.setSigner(docOutDTO.getSigner());
                docOut.setContent(docOutDTO.getContent());
                docOut.setPages(docOutDTO.getPages());
                docOut.setAppendix(docOutDTO.getAppendix());
                docOut.setNote(docOutDTO.getNote());
        if (docOutDTO.getIsGenerated()==null) {
            docOut.setIsGenerated(false);
        } else docOut.setIsGenerated(docOutDTO.getIsGenerated());

        docOut.setNumber(docOutDTO.getNumber());
        docOut.setRegDate(docOutDTO.getRegDate());
        docOut.setState(docOutDTO.getState());
        docOut.setTask(taskService.findOneById(docOutDTO.getTaskId()));
        return docOut;
    }

    public DocOut convertFromDocOutDTONew(DocOutDTO docOutDTO) {

        docOut = new DocOut();
        //docOut.setId(docOutDTO.getId());
        docOut.setCreateDate(docOutDTO.getCreateDate());
        docOut.setCreator(docOutDTO.getCreator());
        docOut.setDocType(docTypeService.getDocTypeById(docOutDTO.getDocTypeId()));
        docOut.setSigner(docOutDTO.getSigner());
        docOut.setContent(docOutDTO.getContent());
        docOut.setPages(docOutDTO.getPages());
        docOut.setAppendix(docOutDTO.getAppendix());
        docOut.setNote(docOutDTO.getNote());
        docOut.setIsGenerated(false);
        docOut.setNumber("б/н");
        docOut.setRegDate(null);
        docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.PROJECT.toString()));

        if(docOutDTO.getTaskId()!=null) docOut.setTask(taskService.findOneById(docOutDTO.getTaskId()));
        docOutService.save(docOut);
        if(docOutDTO.getDocInId()!=null) docInUtils.addDocOutToDocIn(docOutDTO.getDocInId(), docOut);
        return docOut;
    }

    public DocOutDTO convertFromDocOut(DocOut docOut) {
         docOutDTO = new DocOutDTO(
                docOut.getId(),
                docOut.getCreateDate(),
                docOut.getCreator(),
                docOut.getCreator().getId(),
                getUserFIO(docOut.getCreator()),
                docOut.getDocType().getName(),
                docOut.getDocType(),
                docOut.getDocType().getId(),
                docOut.getSigner(),
                docOut.getSigner().getId(),
                docOut.getContent(),
                docOut.getPages(),
                docOut.getAppendix(),
                docOut.getNote(),
                docOut.getIsGenerated(),
                docOut.getNumber(),
                docOut.getRegDate(),
                docOut.getState());

        if (docOut.getTask() != null) {
            docOutDTO.setTask(docOut.getTask());
            docOutDTO.setTaskId(docOut.getTask().getId());
        }
        return docOutDTO;
    }

    public String getUserFIO(User user) {
        return user.getLastName() + " " +
                user.getFirstName()+" " +
                user.getMiddleName();
    }


    public DocOutDTO getDocOutDTO(Long id) {
        docOutDTO = convertFromDocOut(docOutService.findOneById(id));
       return docOutDTO;
    }

    public void addTaskToDocOutDTO(Long id, Task task) {
      docOut = docOutService.findOneById(id);
        docOutDTO.setTask(task);
        docOutDTO.setState(stateService.getStateByBusinessKey(BusinessKeyState.EXECUTION.toString()));
        docOutService.save(docOut);
    }


    public void setNewDocOut() {
        LocalDate localDate=null;
        docOut.setRegDate(localDate);
        docOut.setNumber("б/н");
        docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.PROJECT.toString()));
        docOutService.save(docOut);
    }

    public void delDocOut(Long id){
        docOut=docOutService.findOneById(id);
        docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.DELETED.toString()));
        docOutService.save(docOut);
    }

    public DocOut saveModifiedDocOut(DocOutDTO docOutDTO){
        docOut=docOutService.findOneById(docOutDTO.getId());
        docOut.setDocType(docOutDTO.getDocType());
        docOut.setSigner(docOutDTO.getSigner());
        docOut.setContent(docOutDTO.getContent());
        docOut.setPages(docOutDTO.getPages());
        docOut.setAppendix(docOutDTO.getAppendix());
        docOut.setNote(docOutDTO.getNote());
        docOut.setNumber("б/н");
        docOut.setRegDate(docOutDTO.getRegDate());
        docOut.setTask(taskService.findOneById(docOutDTO.getTaskId()));
        docOutService.save(docOut);
        return docOut;
    }

   public void generateDocOut(DocOutDTO docOutDTO){
       docOut=docOutService.findOneById(docOutDTO.getId());
       getRegOutNumber();
       docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.GENERATED.toString()));
   }

   public void refuseDocOut(DocOutDTO docOutDTO){
       docOut=docOutService.findOneById(docOutDTO.getId());
       docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.RECALLED.toString()));
   }

   public void sendDocOut(DocOutDTO docOutDTO){
       docOut=docOutService.findOneById(docOutDTO.getId());
       docOut.setRegDate(LocalDate.now());
       docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.APPROVING.toString()));
   }

    public void isSent(DocOutDTO docOutDTO){
        docOut=docOutService.findOneById(docOutDTO.getId());
        docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.SENT.toString()));
    }
}

