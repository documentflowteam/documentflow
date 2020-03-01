package com.documentflow.services;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.User;
import com.documentflow.repositories.DocOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    public List<DocOut> findAll() {
        return docOutRepository.findAll();
    }

    public Page<DocOut> findAll(Pageable pageable){
        return docOutRepository.findAll(pageable);
    }

    public User getByCreator(User user){
        return docOutRepository.getByCreator(user);
    }

    public Page<DocOut> findAllByPagingAndFiltering(Specification<DocOut> specification, Pageable pageable) {
        return docOutRepository.findAll(specification, pageable);
    }

    public <S extends DocOut> S save(S s) {
        return docOutRepository.save(s);
    }

//    public Page<DocOut> findAllByCreator(User creator, Pageable pageable){
//        return docOutRepository.findAllByCreator(creator, pageable);
//    }
//
//    public Page<DocOut> findAllBySigner(User signer, Pageable pageable){
//        return docOutRepository.findAllBySigner(signer, pageable);
//    }
//
//    public Page<DocOut> findAllByCreateDate (LocalDate createDate, Pageable pageable){
//        return docOutRepository.findAllByCreateDate(createDate, pageable);
//    }
//
//    public Page<DocOut> findAllByRegDate (LocalDate regDate, Pageable pageable){
//        return docOutRepository.findAllByRegDate(regDate, pageable);
//    }
//
//    public Page<DocOut> findAllByContent (String content, Pageable pageable){
//        return docOutRepository.findAllByContent(content, pageable);
//    }
//
//    public Page<DocOut> findAllByAppendix (String appendix, Pageable pageable){
//        return docOutRepository.findAllByAppendix(appendix, pageable);
//    }
//
//    public Page<DocOut> findAllByNote (String note, Pageable pageable){
//        return docOutRepository.findAllByNote(note, pageable);
//    }
//
//    public Page<DocOut> findAllByIsGenerated (Boolean isGenerated, Pageable pageable){
//        return docOutRepository.findAllByIsGenerated(isGenerated, pageable);
//    }
//
//    public Page<DocOut> findAllByNumber (String number, Pageable pageable){
//        return docOutRepository.findAllByNumber(number, pageable);
//    }
//
//    public Page<DocOut> findAllByState (State state, Pageable pageable){
//        return docOutRepository.findAllByState(state, pageable);
//    }
//
//    public Page<DocOut> findAllByTask (Task task, Pageable pageable){
//        return docOutRepository.findAllByTask(task, pageable);
//    }


//    public DocOut save(DocOutDTO docOutDTO) {
//        DocOut docOut=new DocOut();
//        docOut.setCreator(docOutDTO.getCreator());
//        docOut.setSigner(docOutDTO.getSigner());
//        docOut.setDocType(docOutDTO.getDocType());
//        docOut.setContent(docOutDTO.getContent());
//        docOut.setPages(docOutDTO.getPages());
//        docOut.setIsGenerated(docOutDTO.getIsGenerated());
//        docOut.setAppendix(docOutDTO.getAppendix());
//        docOut.setNote(docOutDTO.getNote());
//        return docOutRepository.save(docOut);
//
//
////        User creator=docOutDTO.getCreator();
////        User signer=docOutDTO.getSigner();
////        String content = docOutDTO.getContent();
////        Integer pages=docOutDTO.getPages();
////        String appendix = docOutDTO.getAppendix();
////        String note = docOutDTO.getNote();
////        State state = docOutDTO.getState();
////
////        DocOut docOut=new DocOut(creator, signer, content, pages, appendix, note, state);
//  //          return docOutRepository.save(docOut);
//    }

    public void deleteById (Long id){
        docOutRepository.deleteById(id);
    }

    public void delete(DocOut docOut){
        docOutRepository.delete(docOut);
    }

//    public DocOut update(DocOutDTO docOutDTO){
//        DocOut docOut=docOutDTO.convertToDocOut();
////        docOut.setCreator(docOutDTO.getCreator());
////        docOut.setSigner(docOutDTO.getSigner());
////        docOut.setDocType(docOutDTO.getDocType());
////        docOut.setContent(docOutDTO.getContent());
////        docOut.setPages(docOutDTO.getPages());
////        docOut.setIsGenerated(docOutDTO.getIsGenerated());
////        docOut.setAppendix(docOutDTO.getAppendix());
////        docOut.setNote(docOutDTO.getNote());
//        return docOutRepository.save(docOut);
//    }

}



















