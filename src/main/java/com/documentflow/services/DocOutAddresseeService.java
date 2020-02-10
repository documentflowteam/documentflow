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

    public List<DocOutAddressee> getAllByName(String name){
        return docOutAddresseeRepository.getAllByName(name);
    }

    public List<DocOutAddressee> getAllByAddress(String address){
        return docOutAddresseeRepository.getAllByAddress(address);
    }

//    public Page<DocOutAddressee> getAll(Pageable pageable){
//        return docOutAddresseeRepository.getAll(pageable);
//    }

    public List<DocOutAddressee> getAllById(Long id){
        return docOutAddresseeRepository.getAllById(id);
    }

    public Page<DocOutAddressee> getPageOfDocOutAddresseeByDesc(Pageable pageable) {
        return docOutAddresseeRepository.findAll(pageable);
    }

    public DocOutAddressee findOneById(Long id){
        return docOutAddresseeRepository.getDocOutAddresseeById(id);
    }

    public DocOutAddressee findOneByName(String name){
        return docOutAddresseeRepository.getDocOutAddresseeByName(name);
    }

 //   public Map<Long, String> findAllByIdAndName(Long id, String name){return docOutAddresseeRepository.findAllByIdAndName(id, name);}

}