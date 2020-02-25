package com.documentflow.entities.DTO;

import com.documentflow.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class DocOutDTO implements Serializable {
    private static final long serialVersionUID = -4L;

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private User creator;
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
    private Task task;
//    private DocIn docIn;

    public DocOutDTO(DocOut docOut) {
        this.id = docOut.getId();
        this.createDate=docOut.getCreateDate();
        this.creator=docOut.getCreator();
        this.docType = docOut.getDocType();
        this.docTypeId = docOut.getDocType().getId();
        this.signer=docOut.getSigner();
        this.content = docOut.getContent();
        this.pages=docOut.getPages();
        this.appendix = docOut.getAppendix();
        this.note = docOut.getNote();
        this.isGenerated=docOut.getIsGenerated();
        this.number = docOut.getNumber();
        this.regDate=docOut.getRegDate();
        this.state = docOut.getState();
        this.task = docOut.getTask();

    }

    public DocOut convertToDocOut() {
        DocOut docOut = new DocOut();
        docOut.setId(this.id);
        docOut.setCreateDate(this.createDate);
        docOut.setCreator(this.creator);
        if (this.docType == null) docOut.setDocType(this.docType);
        docOut.setSigner(this.signer);
        docOut.setContent(this.content);
        docOut.setPages(this.pages);
        docOut.setAppendix(this.appendix);
        docOut.setNote(this.note);
        docOut.setIsGenerated(this.isGenerated);
        docOut.setNumber(this.number);
        docOut.setRegDate(this.regDate);
        docOut.setState(this.state);
        docOut.setTask(this.task);
        return docOut;
    }

}

