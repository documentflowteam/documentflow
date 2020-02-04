package com.documentflow.services;

import com.documentflow.entities.DocOut;
import com.documentflow.repositories.DocOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class DocOutService {

    private DocOutRepository docOutRepository;
    private DocOut docOut;

    @Autowired
    public void setDocOutRepository(DocOutRepository docInRepository) {
        this.docOutRepository = docOutRepository;
    }

    List<DocOut> findAll(){
        return docOutRepository.findAll();
    }

    public DocOut findOneById(Long id) {
        return docOut = docOutRepository.findOneById(id);
    }

    DocOut findOneByUserId(Long userId){
        return docOut=docOutRepository.findOneByUserId(userId);
    }

    DocOut findByCreateDate (LocalDateTime createDate){
        return docOut=docOutRepository.findByCreateDate(createDate);
    }

    DocOut findByRegisterDate (LocalDateTime regDate){
        return docOut=docOutRepository.findByRegisterDate(regDate);
    }

    DocOut findByContent (String content){
        return docOut=docOutRepository.findByContent(content);
    }

    DocOut findByAppendix (String appendix){
        return docOut=docOutRepository.findByAppendix(appendix);
    }

    DocOut findByNote (String note){
        return docOut=docOutRepository.findByNote(note);
    }

    DocOut findByIsGenerated (Boolean isGenerated){
        return docOut=docOutRepository.findByIsGenerated(isGenerated);
    }

    DocOut findByStateId (Integer stateId){
        return docOut=docOutRepository.findByStateId(stateId);
    }

}
