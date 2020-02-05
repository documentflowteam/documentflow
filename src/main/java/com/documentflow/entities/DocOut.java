package com.documentflow.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.documentflow.entities.User;


@Entity
@Table(name = "doc_out")
@Data
@NoArgsConstructor
public class DocOut implements Serializable {

    private static final long serialVersionUID = 6L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name="create_date")
    private LocalDateTime createDate;

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

    @Column(name = "is_genetated")
    private Boolean isGenerated;

    @Column(name = "number")
    private String number;

    @CreationTimestamp
    @Column(name="reg_date")
    private LocalDateTime regDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id")
    private State state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tasks_id")
    private TaskType taskType;

}