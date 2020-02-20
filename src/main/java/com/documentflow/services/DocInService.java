package com.documentflow.services;

import com.documentflow.entities.DocIn;
import com.documentflow.entities.Task;
import com.documentflow.repositories.DocInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    public List<DocIn> findAll() {
        return docInRepository.findAll();
    }

    public Page<DocIn> findAll(Pageable pageable) {
        return docInRepository.findAll(pageable);
    }

    public <S extends DocIn> S save(S s) {
        return docInRepository.save(s);
    }

    public void deleteById(Long aLong) {
        docInRepository.deleteById(aLong);
    }

    public DocIn findByTask(Task task) {
        return docInRepository.findByTask(task);
    }
}
