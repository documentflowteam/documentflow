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

    public DocOut convertedFromDocOutDTO(DocOutDTO docOutDTO) {
        docOut=docOutService.findOneById(docOutDTO.getId());
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
//            docOut.setNumber("б/н");
//        } else docOut.setNumber(docOutDTO.getNumber());
        docOut.setNumber(docOutDTO.getNumber());
        //  docOut.setRegDate(null);
        docOut.setRegDate(docOutDTO.getRegDate());

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
        docOutService.save(docOut);
        return docOut;

    }

    public DocOut convertFromDocOutDTO(DocOutDTO docOutDTO) {

         docOut = new DocOut();
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
//            docOut.setNumber("б/н");
//        } else docOut.setNumber(docOutDTO.getNumber());
        docOut.setNumber(docOutDTO.getNumber());
      //  docOut.setRegDate(null);
        docOut.setRegDate(docOutDTO.getRegDate());

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
                user.getFirstName()+" " +
                user.getMiddleName();
    }


    public DocOutDTO getDocOutDTO(Long id) {
        docOutDTO = convertFromDocOut(docOutService.findOneById(id));
       return docOutDTO;
    }

    public void editState(Long id, BusinessKeyState state) {
        DocOut docOut = docOutService.findOneById(id);
        switch (state) {
            case EXECUTION:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.EXECUTION.toString()));
                break;
            case EXECUTED:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.EXECUTED.toString()));
                break;
            case RECALLED:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.RECALLED.toString()));
                break;
            case DELETED:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.DELETED.toString()));
                break;
            case PROJECT:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.PROJECT.toString()));
                break;
            case APPROVED:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.APPROVED.toString()));
                break;
            case SENT:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.SENT.toString()));
                break;
            case REWORK:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.REWORK.toString()));
                break;
            case CHECKING:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.CHECKING.toString()));
                break;
            case REGISTERED:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.REGISTERED.toString()));
                break;
            case APPROVING:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.APPROVING.toString()));
                break;
            case GENERATED:
                docOut.setState(stateService.getStateByBusinessKey(BusinessKeyState.GENERATED.toString()));
                break;
        }
        docOutService.save(docOut);
    }

    public void addTaskToDocOutDTO(Task task) {
//        DocOut docOut = docOutService.findOneById(id);
        docOutDTO.setTask(task);
        docOutDTO.setState(stateService.getStateByBusinessKey(BusinessKeyState.EXECUTION.toString()));
 //       docOutService.save(docOut);
    }

    public void setNewDocOutRegDate(LocalDate localDate) {
        docOut.setRegDate(localDate);
        docOutService.save(docOut);
    }

//    public void setNewDocOutState() {
//        editState(docOutDTO.getId(), BusinessKeyState.PROJECT);
//        docOutService.save(docOut);
//    }

    public void setNewDocOutNumber() {
        docOut.setNumber("б/н");
        docOutService.save(docOut);
    }

    public void delDocOut(DocOutDTO docOutDTO){
        docOut=docOutService.findOneById(docOutDTO.getId());
        if(docOut.getState()!=stateService.getStateById(1)
                || docOut.getState()!=stateService.getStateById(3)
                || docOut.getState()!=stateService.getStateById(4)
                || docOut.getState()!=stateService.getStateById(8)
                || docOut.getState()!=stateService.getStateById(9)) {
      //      docOut.setState(stateService.getStateById(4));
     //       docOutService.save(docOut);
            editState(docOutDTO.getId(), BusinessKeyState.DELETED);

        }else return;
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
        docOut.setTask(docOutDTO.getTask());
        docOutService.save(docOut);
        return docOut;
    }

   public void generateDocOut(DocOutDTO docOutDTO){
       docOut=docOutService.findOneById(docOutDTO.getId());
       getRegOutNumber();
       editState(docOutDTO.getId(), BusinessKeyState.GENERATED);
   }

   public void refuseDocOut(DocOutDTO docOutDTO){
       docOut=docOutService.findOneById(docOutDTO.getId());
       editState(docOutDTO.getId(), BusinessKeyState.RECALLED);
   }

   public void sendDocOut(DocOutDTO docOutDTO){
       docOut=docOutService.findOneById(docOutDTO.getId());
       editState(docOutDTO.getId(), BusinessKeyState.CHECKING);
   }

    public void isSent(DocOutDTO docOutDTO){
        docOut=docOutService.findOneById(docOutDTO.getId());
        editState(docOutDTO.getId(), BusinessKeyState.SENT);
    }
}

