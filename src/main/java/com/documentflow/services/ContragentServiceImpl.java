package com.documentflow.services;

import com.documentflow.entities.Contragent;
import com.documentflow.repositories.ContragentRepository;
import com.documentflow.utils.ContragentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContragentServiceImpl implements ContragentService {

    @Autowired
    private ContragentRepository contragentRepository;

    @Override
    public List<Contragent> searchContragents(String searchName) {
        return contragentRepository.findAllBySearchNameLike(searchName);
    }

    @Override
    public List<Long> save(List<Contragent> contragents) {

        List<Contragent> normalizeContragent = contragents.stream()
                .filter(ContragentUtils::isNotEmpty)
                .collect(Collectors.toList());

        //TODO реализовать проверку контрагента на предмет существования в БД
        return contragentRepository.saveAll(normalizeContragent).stream()
                .map(Contragent::getId)
                .collect(Collectors.toList());
    }
}
