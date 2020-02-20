package com.documentflow.entities.DTO;

import com.documentflow.entities.*;
import com.documentflow.utils.DocInUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DocInDTO implements Serializable {
    private static final long serialVersionUID = -3933271279839435794L;

    private Long id;
    private String regNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
    private User user;
    private DocType docType;
    private Integer docTypeId;
    private Department department;
    private Integer departmentId;
    private String sender;
    private String outgoingNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outgoingDate;
    private String content;
    private Integer pages;
    private String appendix;
    private String note;
    private State state;
//    private DocOut docOut;
    private Task task;

    public DocInDTO(DocIn docIn) {
        this.id = docIn.getId();
        this.regNumber = docIn.getRegNumber();
        this.regDate = convertToDate(docIn.getRegDate());
        this.user = docIn.getUser();
        this.docType = docIn.getDocType();
        this.docTypeId = docIn.getDocType().getId();
        if (this.docTypeId == null) {
            this.docTypeId = -1;
        }
        this.department = docIn.getDepartment();
        this.departmentId = docIn.getDepartment().getId();
        if (this.departmentId == null) {
            this.departmentId = -1;
        }
        this.sender = docIn.getSender();
        this.outgoingNumber = docIn.getOutgoingNumber();
        this.outgoingDate = convertToDate(docIn.getOutgoingDate());
        this.content = docIn.getContent();
        this.pages = docIn.getPages();
        this.appendix = docIn.getAppendix();
        this.note = docIn.getNote();
        this.state = docIn.getState();
//        this.docOut = docIn.getDocOut();
        this.task = docIn.getTask();
    }

    public DocIn convertToDocIn(DocType docType, Department department) {
        DocIn docIn = new DocIn();
        docIn.setId(this.id);
        docIn.setRegNumber(this.regNumber);
        docIn.setRegDate(convertToLocalDate(this.regDate));
        docIn.setUser(this.user);
        if (this.docTypeId != docType.getId() || this.docType == null) {
            docIn.setDocType(docType);
        }
        if (this.departmentId != department.getId() || this.department == null) {
            docIn.setDepartment(department);
        }
        docIn.setSender(this.sender);
        docIn.setOutgoingNumber(this.outgoingNumber);
        docIn.setOutgoingDate(convertToLocalDate(this.outgoingDate));
        docIn.setContent(this.content);
        docIn.setPages(this.pages);
        docIn.setAppendix(this.appendix);
        docIn.setNote(this.note);
        docIn.setState(this.state);
//        docIn.setDocOut(this.docOut);
        docIn.setTask(this.task);
        return docIn;
    }

    
    // TODO: попробовать вынести конвертилки дат в общий utils
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
}
