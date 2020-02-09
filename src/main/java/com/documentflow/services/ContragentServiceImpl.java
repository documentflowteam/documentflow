package com.documentflow.services;

import com.documentflow.entities.Contragent;
import com.documentflow.repositories.ContragentRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContragentServiceImpl implements ContragentService {

    @Autowired
    private ContragentRepository contragentRepository;

    private LoadingCache<String, List<Contragent>> cache = CacheBuilder.newBuilder()
            .maximumSize(150)
            .build(CacheLoader.from(searchItem -> contragentRepository.findAllBySearchNameLike(searchItem)));

    @Override
    public List<Contragent> searchContragents(String searchName) {
        return cache.getUnchecked(searchName);
    }
}
