package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.repositories.AddressRepository;
import com.documentflow.utils.ContragentUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Long> save(@NonNull ContragentDtoAddress[] addressesDto) {

        List<Address> addresses = Arrays.stream(addressesDto)
                .map(Address::new)
                .collect(Collectors.toList());

        //TODO реализовать проверку адресов на предмет существования в БД
        return addressRepository.saveAll(addresses).stream()
                .filter(ContragentUtils::isNotEmpty)
                .map(Address::getId)
                .collect(Collectors.toList());
    }
}
