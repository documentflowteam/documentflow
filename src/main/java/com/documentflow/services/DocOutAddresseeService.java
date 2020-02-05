package com.documentflow.services;

import com.documentflow.entities.DocOutAddressee;
import com.documentflow.repositories.DocOutAddresseeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    List<DocOutAddressee> findAll(){
        return docOutAddresseeRepository.findAll();
    }

    DocOutAddressee findOneById(Long id){
        return docOutAddresseeRepository.findOneById(id);
    }

    DocOutAddressee findOneByName(String name){
        return docOutAddresseeRepository.findOneByName(name);
    }
}