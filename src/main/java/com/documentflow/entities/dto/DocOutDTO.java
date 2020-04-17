package com.documentflow.entities.dto;

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
    private Long docInId;
    private String docInRegNumber;

    public DocOutDTO(Long id, LocalDate createDate, User creator, Integer creatorId, String creatorFIO, String docTypeName,
                     DocType docType, User signer, String content, Integer pages, String appendix,
                     String note, Boolean isGenerated, String number, LocalDate regDate, State state) {
        this.id = id;
        this.createDate = createDate;
        this.creator=creator;
        this.creatorId = creatorId;
        this.creatorFIO = creatorFIO;
        this.docTypeName = docTypeName;
        this.docType = docType;
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

    public DocOutDTO(Long id, LocalDate createDate, User creator, Integer creatorId, String creatorFIO, String docTypeName,
                     DocType docType, User signer, String content, Integer pages, String appendix,
                     String note, Boolean isGenerated, String number, LocalDate regDate) {
        this.id = id;
        this.createDate = createDate;
        this.creator=creator;
        this.creatorId = creatorId;
        this.creatorFIO = creatorFIO;
        this.docTypeName = docTypeName;
        this.docType = docType;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.appendix = appendix;
        this.note = note;
        this.isGenerated = isGenerated;
        this.number = number;
        this.regDate = regDate;

    }


    public DocOutDTO(Long id, LocalDate createDate, User creator, DocType docType, User signer,
                          String content, Integer pages, String appendix, String note,
                          Boolean isGenerated, String number, LocalDate regDate, State state, Task task) {
        this.id = id;
        this.createDate = createDate;
        this.creator = creator;
        this.docType = docType;
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

    public DocOutDTO(Long id, LocalDate createDate, User creator, DocType docType, User signer,
                     String content, Integer pages, LocalDate regDate, State state) {
        this.id = id;
        this.createDate = createDate;
        this.creator = creator;
        this.docType = docType;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.regDate=regDate;
        this.state = state;
    }

    public DocOutDTO(Long id, LocalDate createDate, User creator, DocType docType, User signer,
                     String content, Integer pages, State state) {
        this.id = id;
        this.createDate = createDate;
        this.creator = creator;
        this.docType = docType;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.state = state;
    }

    public DocOutDTO(Long id, LocalDate createDate, DocType docType, User signer,
                     String content, Integer pages, State state) {
        this.id = id;
        this.createDate = createDate;
        this.docType = docType;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.state = state;
    }
}



