package com.documentflow.services;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
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

    public DocOut findByTask(Task task) {
        return docOutRepository.findByTask(task);
    }

    public void deleteById (Long id){
        docOutRepository.deleteById(id);
    }

    public void delete(DocOut docOut){
        docOutRepository.delete(docOut);
    }


}



















