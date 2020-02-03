package com.documentflow.services;

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

    public DocIn findById(Long id) {
        DocIn docIn = docInRepository.findById(id).get();
        return docIn;
    }

}
