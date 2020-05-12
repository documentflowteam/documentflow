package com.documentflow.entities.dto;

import com.documentflow.entities.*;
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
    private String creatorFIO;
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
    private Task task;
    private Long docInId;
    private String docInRegNumber;
//    private Contragent contragent;
//    private Long contragentId;
//    private Organization organization;
//    private Long organizationId;

    public DocOutDTO(Long id, LocalDate createDate, User creator, String creatorFIO,
                     DocType docType, User signer, String content, Integer pages, String appendix,
                     String note, Boolean isGenerated, String number, LocalDate regDate, State state) {
        this.id = id;
        this.createDate = createDate;
        this.creator=creator;
        this.creatorFIO = creatorFIO;
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

}



