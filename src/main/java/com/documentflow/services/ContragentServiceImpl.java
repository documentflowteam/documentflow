package com.documentflow.services;

import com.documentflow.entities.Contragent;
import com.documentflow.repositories.ContragentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContragentServiceImpl implements ContragentService {

    @Autowired
    private ContragentRepository contragentRepository;

    @Override
    public List<Contragent> searchContragents(String searchName) {
        return contragentRepository.findAllBySearchNameLike(searchName);
    }
}
