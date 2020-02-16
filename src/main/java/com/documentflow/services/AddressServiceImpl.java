package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.repositories.AddressRepository;
import com.documentflow.repositories.specifications.AddressSpecifications;
import com.documentflow.utils.ContragentUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> save(@NonNull ContragentDtoAddress[] addressesDto) {

        List<Address> addresses = Arrays.stream(addressesDto)
                .map(Address::new)
                .filter(ContragentUtils::isNotEmpty)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(addresses)) {
            return null;
        }
        return addressRepository.saveAll(addresses);
    }

    @Override
    public List<Address> findAll(String postIndex, String country, String city, String street, String houseNumber, String apartrmentNumber) {
        Specification<Address> spec = Specification.where(null);
        if(!ObjectUtils.isEmpty(postIndex)){
            spec = spec.and(AddressSpecifications.postIndexEq(postIndex));
        }
        if(!StringUtils.isEmpty(country)){
            spec = spec.and(AddressSpecifications.countryEq(country));
        }
        if(!StringUtils.isEmpty(city)){
            spec = spec.and(AddressSpecifications.cityEq(city));
        }
        if(!StringUtils.isEmpty(street)){
            spec = spec.and(AddressSpecifications.streetEq(street));
        }
        if(!ObjectUtils.isEmpty(houseNumber)){
            spec = spec.and(AddressSpecifications.houseNumberEq(houseNumber));
        }
        if(!ObjectUtils.isEmpty(apartrmentNumber)){
            spec = spec.and(AddressSpecifications.apartmentNumberEq(apartrmentNumber));
        }
        return addressRepository.findAll(spec);
    }

    @Override
    public Address update(Long id, Integer postIndex, String country, String city, String street, String houseNumber, String apartrmentNumber) {
        return addressRepository.save(new Address(id, postIndex, country, city, street, houseNumber, apartrmentNumber));
    }
}
