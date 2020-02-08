package com.documentflow.services;

import com.documentflow.entities.DocType;
import com.documentflow.entities.State;
import com.documentflow.repositories.DocTypeRepository;
import com.documentflow.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocTypeService {

    private DocTypeRepository docTypeRepository;

    @Autowired
    public void setDocTypeRepository(DocTypeRepository docTypeRepository) {
        this.docTypeRepository = docTypeRepository;
    }

    public DocType getDocTypeById (int id) {
        return docTypeRepository.findOneById(id);
    }

    public DocType getDocTypeByBusinessKey (String business_key) {
        return docTypeRepository.findOneByBusinessKey(business_key);
    }

    public List<DocType> findAllDocTypes() {
        return docTypeRepository.findAll();
    }


}
