package com.documentflow.services;

import com.documentflow.entities.DTOs.DocTypeDTO;
import com.documentflow.entities.DocType;
import com.documentflow.repositories.DocTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocTypeService {

    private DocTypeRepository docTypeRepository;

    @Autowired
    public void setDocTypeRepository(DocTypeRepository docTypeRepository) {
        this.docTypeRepository = docTypeRepository;
    }

    public DocType convertFromDTO(DocTypeDTO docTypeDTO){
        return new DocType(docTypeDTO.getId(),
                docTypeDTO.getName(),
                docTypeDTO.getBusinessKey());
    }

    public DocTypeDTO convertToDTO(DocType docType){
        return new DocTypeDTO(docType.getId(),
                docType.getName(),
                docType.getBusinessKey());
    }
}
