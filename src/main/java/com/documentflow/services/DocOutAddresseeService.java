package com.documentflow.services;

import com.documentflow.entities.DocOutAddressee;
import com.documentflow.repositories.DocOutAddresseeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocOutAddresseeService {

    private DocOutAddresseeRepository docOutAddresseeRepository;

    @Autowired
    public void setDocOutAddresseeRepository (DocOutAddresseeRepository docOutAddresseeRepository){
        this.docOutAddresseeRepository=docOutAddresseeRepository;
    }

    public List<DocOutAddressee> getAllByName(){
        return docOutAddresseeRepository.getAllByName();
    }

    public List<Long> getAllById(){
        return docOutAddresseeRepository.getAllById();
    }

    public Page<DocOutAddressee> getPageOfDocOutAddresseeByDesc(Pageable pageable) {
        return docOutAddresseeRepository.findAll(pageable);
    }

    public DocOutAddressee findOneById(Long id){
        return docOutAddresseeRepository.findOneById(id);
    }

    public DocOutAddressee findOneByName(String name){
        return docOutAddresseeRepository.findOneByName(name);
    }

}