package com.documentflow.entities.DTOs;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DocInDTO implements Serializable {
    private static final long serialVersionUID = -6611182487642686810L;

    private Long id;
    private String regNumber;
    private LocalDateTime regDate;
    private UserDTO userDTO;
    private DocTypeDTO docTypeDTO;
    private DepartmentDTO department;
    private String sender;
    private String outgoingNumber;
    private LocalDateTime outgoingDate;
    private String content;
    private Integer pages;
    private String appendix;
    private String note;
    private StateDTO stateDTO;
    private DocOutDTO docOutDTO;
    private TaskDTO taskDTO;
}
