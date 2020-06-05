package com.documentflow.services;

import com.documentflow.entities.DocIn;
import com.documentflow.entities.DocOut;
import com.documentflow.entities.Task;
import com.documentflow.repositories.DocInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DocInService {

    private DocInRepository docInRepository;

    @Autowired
    public DocInService(DocInRepository docInRepository) {
        this.docInRepository = docInRepository;
    }

    public DocIn findById(Long id) {
        return docInRepository.findById(id).get();
    }

    public List<DocIn> findAll() {
        return docInRepository.findAll();
    }

    public Page<DocIn> findAll(Pageable pageable) {
        return docInRepository.findAll(pageable);
    }

    public <S extends DocIn> S save(S s) {
        return docInRepository.save(s);
    }

    public DocIn findByTask(Task task) {
        return docInRepository.findByTask(task);
    }

    public DocIn findFirstByOrderByIdDesc() {
        return docInRepository.findFirstByOrderByIdDesc();
    }

    public Page<DocIn> findAllByPagingAndFiltering(Specification<DocIn> specification, Pageable pageable) {
        return docInRepository.findAll(specification, pageable);
    }

    public DocIn findByRegNumber(String regNumber) {
        return docInRepository.findByRegNumber(regNumber);
    }

    public DocIn findByDocOut(DocOut docOut) {
        return docInRepository.findByDocOut(docOut);
    }

    public boolean existsById(Long id) {
        return docInRepository.existsById(id);
    }
}
