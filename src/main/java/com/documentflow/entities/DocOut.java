package com.documentflow.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "doc_out")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocOut implements Serializable {

    private static final long serialVersionUID = 6L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name="create_date")
    private LocalDate createDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doc_type_id")
    private DocType docType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "signer_id")
    private User signer;

    @Column(name = "content")
    private String content;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "appendix")
    private String appendix;

    @Column(name = "note")
    private String note;

    @Column(name = "is_generated")
    private Boolean isGenerated;

    @Column(name = "number")
    private String number;

    @CreationTimestamp
    @Column(name="reg_date")
    private LocalDate regDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id")
    private State state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private Task task;


    public DocOut(User creator, User signer, String content, Integer pages, String appendix, String note, State state) {
        this.creator = creator;
        this.signer = signer;
        this.content = content;
        this.pages = pages;
        this.appendix = appendix;
        this.note = note;
        this.state = state;
    }
}

//        User creator=docOutDTO.getCreator();
//        User signer=docOutDTO.getSigner();
//        String content = docOutDTO.getContent();
//        Integer pages=docOutDTO.getPages();
//        String appendix = docOutDTO.getAppendix();
//        String note = docOutDTO.getNote();
//        State state = docOutDTO.getState();
//        DocOut docOut=new DocOut();