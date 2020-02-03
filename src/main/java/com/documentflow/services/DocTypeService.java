package com.documentflow.services;

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
}
