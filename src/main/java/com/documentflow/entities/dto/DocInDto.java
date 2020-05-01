package com.documentflow.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DocInDto implements Serializable {
    private static final long serialVersionUID = -3933271279839435794L;

    private Long id;
    private String regNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
    private Integer userId;
    private String userFIO;
    private Integer docTypeId;
    private String docTypeName;
    private Integer departmentId;
    private String sender;
    private String outgoingNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outgoingDate;
    private String content;
    private Integer pages;
    private String appendix;
    private String note;
    private String stateName;
    private Integer stateId;
    private Long docOutId;
    private String docOutNumber;
    private Long taskId;

    public DocInDto(Long id, String regNumber, Date regDate, Integer userId, String userFIO, Integer docTypeId, String docTypeName, Integer departmentId, String sender, String outgoingNumber, Date outgoingDate, String content, Integer pages, String appendix, String note, String stateName, Integer stateId) {
        this.id = id;
        this.regNumber = regNumber;
        this.regDate = regDate;
        this.userId = userId;
        this.userFIO = userFIO;
        this.docTypeId = docTypeId;
        this.docTypeName = docTypeName;
        this.departmentId = departmentId;
        this.sender = sender;
        this.outgoingNumber = outgoingNumber;
        this.outgoingDate = outgoingDate;
        this.content = content;
        this.pages = pages;
        this.appendix = appendix;
        this.note = note;
        this.stateName = stateName;
        this.stateId = stateId;
    }
}
