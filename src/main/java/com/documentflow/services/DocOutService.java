package com.documentflow.services;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.State;
import com.documentflow.entities.Task;
import com.documentflow.entities.User;
import com.documentflow.repositories.DocOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
public class DocOutService {

    private DocOutRepository docOutRepository;

    @Autowired
    public void setDocOutRepository(DocOutRepository docOutRepository) {
        this.docOutRepository = docOutRepository;
    }

    public DocOut findOneById(Long id) {
        return docOutRepository.findOneById(id);
    }

 //   public List<DocOut> findAll(){
//        return docOutRepository.findAll();
//    }
    public Page<DocOut> findAll(Pageable pageable){
        return docOutRepository.findAll(pageable);
    }


//    public List<DocOut> findAllByCreator(User creator){
//        return docOutRepository.findAllByCreator(creator);
//    }

    public Page<DocOut> findAllByCreator(User creator, Pageable pageable){
        return docOutRepository.findAllByCreator(creator, pageable);
    }

//    public List<DocOut> findAllBySigner(User signer){
//        return docOutRepository.findAllBySigner(signer);
//    }

    public Page<DocOut> findAllBySigner(User signer, Pageable pageable){
        return docOutRepository.findAllBySigner(signer, pageable);
    }

//    public List<DocOut> findAllByCreateDate (LocalDateTime createDate){
//        return docOutRepository.findAllByCreateDate(createDate);
//    }

    public Page<DocOut> findAllByCreateDate (LocalDateTime createDate, Pageable pageable){
        return docOutRepository.findAllByCreateDate(createDate, pageable);
    }

//    public List<DocOut> findAllByRegDate (LocalDateTime regDate){
//        return docOutRepository.findAllByRegDate(regDate);
//    }

    public Page<DocOut> findAllByRegDate (LocalDateTime regDate, Pageable pageable){
        return docOutRepository.findAllByRegDate(regDate, pageable);
    }

//    public List<DocOut> findAllByContent (String content){
////        return docOutRepository.findAllByContent(content);
////    }

    public Page<DocOut> findAllByContent (String content, Pageable pageable){
        return docOutRepository.findAllByContent(content, pageable);
    }

//    public List<DocOut> findAllByAppendix (String appendix){
//        return docOutRepository.findAllByAppendix(appendix);
//    }

    public Page<DocOut> findAllByAppendix (String appendix, Pageable pageable){
        return docOutRepository.findAllByAppendix(appendix, pageable);
    }

//    public List<DocOut> findAllByNote (String note){
//        return docOutRepository.findAllByNote(note);
//    }

    public Page<DocOut> findAllByNote (String note, Pageable pageable){
        return docOutRepository.findAllByNote(note, pageable);
    }

//    public List<DocOut> findAllByIsGenerated (Boolean isGenerated){
//        return docOutRepository.findAllByIsGenerated(isGenerated);
//    }

    public Page<DocOut> findAllByIsGenerated (Boolean isGenerated, Pageable pageable){
        return docOutRepository.findAllByIsGenerated(isGenerated, pageable);
    }

//    public List<DocOut> findAllByState (State state){
//        return docOutRepository.findAllByState(state);
//    }

    public Page<DocOut> findAllByState (State state, Pageable pageable){
        return docOutRepository.findAllByState(state, pageable);
    }

//    public List<DocOut> findAllByTaskType (TaskType taskType){
//        return docOutRepository.findAllByTaskType(taskType);
//    }

    public Page<DocOut> findAllByTask (Task task, Pageable pageable){
        return docOutRepository.findAllByTask(task, pageable);
    }

}



















