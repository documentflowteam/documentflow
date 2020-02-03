package com.documentflow.services;

import com.documentflow.entities.DTOs.DocInDTO;
import com.documentflow.entities.DocIn;
import com.documentflow.repositories.DocInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DocInService {

    private DocInRepository docInRepository;

    @Autowired
    public void setDocInRepository(DocInRepository docInRepository) {
        this.docInRepository = docInRepository;
    }

    public DocInDTO findById(Long id) {
        DocIn docIn = docInRepository.findById(id).get();
        return convertToDTO(docIn);
    }


    //Доделать когда будут готовы конвертеры других сущностей
    public DocIn convertFromDTO(DocInDTO docInDTO){
        return new DocIn();
    }

    public DocInDTO convertToDTO(DocIn docIn){
        return new DocInDTO();
    }
}
