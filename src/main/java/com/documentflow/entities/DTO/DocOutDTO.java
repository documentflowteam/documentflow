package com.documentflow.entities.DTO;

import com.documentflow.entities.DocType;
import com.documentflow.entities.State;
import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocOutDTO implements Serializable {
    private static final long serialVersionUID = -4L;

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private User creator;
    private Integer creatorId;
    private String creatorFIO;
    private String docTypeName;
    private DocType docType;
    private Integer docTypeId;
    private User signer;
    private String content;
    private Integer pages;
    private String appendix;
    private String note;
    private Boolean isGenerated;
    private String number;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    private State state;
    private Integer stateId;
    private Task task;
    private Long taskId;

    public DocOutDTO(Long id, LocalDate createDate, User creator, Integer creatorId, String creatorFIO, String docTypeName,
                     DocType docType, Integer docTypeId, User signer, String content, Integer pages, String appendix,
                     String note, Boolean isGenerated, String number, LocalDate regDate, State state) {
        this.id = id;
        this.createDate = createDate;
        this.creator=creator;
        this.creatorId = creatorId;
        this.creatorFIO = creatorFIO;
        this.docTypeName = docTypeName;
        this.docType = docType;
        this.docTypeId = docTypeId;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.appendix = appendix;
        this.note = note;
        this.isGenerated = isGenerated;
        this.number = number;
        this.regDate = regDate;
        this.state = state;

    }

//
//    private Long id;
//    private LocalDate createDate;
//    private User creator;
//    private DocType docType;
//     private User signer;
//    private String content;
//    private Integer pages;
//    private String appendix;
//    private String note;
//    private Boolean isGenerated;
//    private String number;
//    private LocalDate regDate;
//    private State state;
//     private Task task;

    public DocOutDTO(Long id, LocalDate createDate, User creator, DocType docType, Integer docTypeId, User signer,
                          String content, Integer pages, String appendix, String note,
                          Boolean isGenerated, String number, LocalDate regDate, State state, Task task) {
        this.id = id;
        this.createDate = createDate;
        this.creator = creator;
        this.docType = docType;
        this.docTypeId=docTypeId;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.appendix = appendix;
        this.note = note;
        this.isGenerated = isGenerated;
        this.number = number;
        this.regDate = regDate;
        this.state = state;
        this.task = task;
    }

    public DocOutDTO(Long id, LocalDate createDate, User creator, DocType docType, Integer docTypeId, User signer,
                     String content, Integer pages, State state) {
        this.id = id;
        this.createDate = createDate;
        this.creator = creator;
        this.docType = docType;
        this.docTypeId=docTypeId;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.state = state;
    }
}

    //    public DocOutDTO(DocOut docOut) {
//        this.id = docOut.getId();
//        this.createDate=docOut.getCreateDate();
//        this.creator=docOut.getCreator();
//        this.docType = docOut.getDocType();
//        this.signer=docOut.getSigner();
//        this.content = docOut.getContent();
//        this.pages=docOut.getPages();
//        this.appendix = docOut.getAppendix();
//        this.note = docOut.getNote();
//        this.isGenerated=docOut.getIsGenerated();
//        this.number = docOut.getNumber();
//        this.regDate=docOut.getRegDate();
//        this.state = docOut.getState();
//        this.task = docOut.getTask();
//
//    }

//    public DocOut convertToDocOut() {
//        DocOut docOut = new DocOut();
//        docOut.setId(this.id);
//        docOut.setCreateDate(this.createDate);
//        docOut.setCreator(this.creator);
//        if (this.docType == null) docOut.setDocType(this.docType);
//        docOut.setSigner(this.signer);
//        docOut.setContent(this.content);
//        docOut.setPages(this.pages);
//        docOut.setAppendix(this.appendix);
//        docOut.setNote(this.note);
//        docOut.setIsGenerated(this.isGenerated);
//        docOut.setNumber(this.number);
//        docOut.setRegDate(this.regDate);
//        docOut.setState(this.state);
//        docOut.setTask(this.task);
//        return docOut;
//    }
//
//    public DocOutDTO convertFromDocOut(DocOut docOut) {
//        DocOutDTO docOutDTO = new DocOutDTO(
//        docOut.getId(),
//        docOut.getCreateDate(),
//        docOut.getCreator(),
//        docOut.getDocType(),
//        docOut.getSigner(),
//        docOut.getContent(),
//        docOut.getPages(),
//        docOut.getAppendix(),
//        docOut.getNote(),
//        docOut.getIsGenerated(),
//        docOut.getNumber(),
//        docOut.getRegDate(),
//        docOut.getState(),
//        docOut.getTask());
//        return docOutDTO;
//    }

//}

