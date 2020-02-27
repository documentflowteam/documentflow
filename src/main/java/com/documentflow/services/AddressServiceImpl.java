package com.documentflow.services;

import com.documentflow.entities.Address;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.exceptions.NotFoundAddressException;
import com.documentflow.exceptions.NotFoundIdException;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContragentService contragentService;

    @Override
    public List<Address> save(@NonNull ContragentDtoAddress[] addressesDto) {

        List<Address> addresses = Arrays.stream(addressesDto)
                .map(Address::new)
                .filter(ContragentUtils::isNotEmpty)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(addresses)) {
            throw new NotFoundAddressException();
        }
        return addressRepository.saveAll(addresses);
    }

    @Override
    public List<Address> findAll(String postIndex, String country, String city, String street, String houseNumber, String apartrmentNumber) {
        Specification<Address> spec = Specification.where(null);
        if (!ObjectUtils.isEmpty(postIndex)) {
            spec = spec.and(AddressSpecifications.postIndexEq(postIndex));
        }
        if (!StringUtils.isEmpty(country)) {
            spec = spec.and(AddressSpecifications.countryEq(country));
        }
        if (!StringUtils.isEmpty(city)) {
            spec = spec.and(AddressSpecifications.cityEq(city));
        }
        if (!StringUtils.isEmpty(street)) {
            spec = spec.and(AddressSpecifications.streetEq(street));
        }
        if (!ObjectUtils.isEmpty(houseNumber)) {
            spec = spec.and(AddressSpecifications.houseNumberEq(houseNumber));
        }
        if (!ObjectUtils.isEmpty(apartrmentNumber)) {
            spec = spec.and(AddressSpecifications.apartmentNumberEq(apartrmentNumber));
        }
        return addressRepository.findAll(spec).stream()
                .filter(organization -> {
                    return organization.getContragents().stream()
                            .anyMatch(contragent -> !contragent.getIsDeleted());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Address update(Address adr) {

        if (adr.getId() == null) {
            throw new NotFoundIdException();
        }

        Optional<Address> optionalAddress = addressRepository.findById(adr.getId());
        if (!optionalAddress.isPresent()) {
            throw new NotFoundAddressException();
        }

        String newCountry = adr.getCountry().toUpperCase();
        String newCity = adr.getCity().toUpperCase();
        String newStreet = adr.getStreet().toUpperCase();

        Address address = optionalAddress.get();
        address.setIndex(adr.getIndex());
        address.setCountry(newCountry);
        address.setCity(newCity);
        address.setStreet(newStreet);
        address.setHouseNumber(adr.getHouseNumber());
        address.setApartmentNumber(adr.getApartmentNumber());

        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {

        Optional<Address> addressOptional = addressRepository.findById(id);
        if (!addressOptional.isPresent()) {
            throw new NotFoundAddressException();
        }
        Address address = addressOptional.get();

        address.getContragents().forEach(contragent -> {
            contragent.setIsDeleted(true);
            contragentService.save(contragent);
        });
    }
}
