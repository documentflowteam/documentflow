package com.documentflow.services;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.State;
import com.documentflow.entities.TaskType;
import com.documentflow.entities.User;
import com.documentflow.repositories.DocOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class DocOutService {

    private DocOutRepository docOutRepository;

    @Autowired
    public void setDocOutRepository(DocOutRepository docOutRepository) {
        this.docOutRepository = docOutRepository;
    }

    public List<DocOut> findAll(){
        return docOutRepository.findAll();
    }


    public DocOut findOneById(Long id) {
        return docOutRepository.findOneById(id);
    }

    public List<DocOut> findAllByCreator(User creator){
        return docOutRepository.findAllByCreator(creator);
    }

    public List<DocOut> findAllBySigner(User signer){
        return docOutRepository.findAllBySigner(signer);
    }

    public List<DocOut> findAllByCreateDate (LocalDateTime createDate){
        return docOutRepository.findAllByCreateDate(createDate);
    }

    public List<DocOut> findAllByRegDate (LocalDateTime regDate){
        return docOutRepository.findAllByRegDate(regDate);
    }

    public List<DocOut> findAllByContent (String content){
        return docOutRepository.findAllByContent(content);
    }

    public List<DocOut> findAllByAppendix (String appendix){
        return docOutRepository.findAllByAppendix(appendix);
    }

    public List<DocOut> findAllByNote (String note){
        return docOutRepository.findAllByNote(note);
    }

    public List<DocOut> findAllByIsGenerated (Boolean isGenerated){
        return docOutRepository.findAllByIsGenerated(isGenerated);
    }

    public List<DocOut> findAllByState (State state){
        return docOutRepository.findAllByState(state);
    }

    public List<DocOut> findAllByTaskType (TaskType taskType){
        return docOutRepository.findAllByTaskType(taskType);
    }

}
