package com.documentflow.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "doc_in")
@Getter
@Setter
@NoArgsConstructor
public class DocIn implements Serializable {
    private static final long serialVersionUID = -6611182487642686810L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reg_number")
    private String regNumber;

    @CreationTimestamp
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @ManyToOne
    @JoinColumn(name = "registrator_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "doc_type_id")
    private DocType docType;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "sender")
    private String sender;

    @Column(name = "outgoing_number")
    private String outgoingNumber;

    @Column(name = "outgoing_date")
    private LocalDateTime outgoingDate;

    @Column(name = "content")
    private String content;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "appendix")
    private String appendix;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

//    @OneToOne
//    @JoinColumn(name = "doc_out_id")
//    private DocOut docOut;

//    @OneToOne
//    @JoinColumn(name = "task_id")
//    private Task task;
}
