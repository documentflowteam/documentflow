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

        if (ContragentUtils.isEmpty(addressesDto)) {
            throw new NotFoundAddressException();
        }
        List<Address> addresses = Arrays.stream(addressesDto)
                .map(Address::new)
                .filter(ContragentUtils::isNotEmpty)
                .map(this::checkDuplicateAndGetAddress)
                .collect(Collectors.toList());
        return addressRepository.saveAll(addresses);
    }

    @Override
    public Address save(@NonNull Address address) {
        return checkDuplicateAndGetAddress(ContragentUtils.normalizeAddress(address));
    }

    @Override
    public List<Address> findAll(String postIndex, String country, String city, String street, String houseNumber, String apartrmentNumber) {
        Specification<Address> spec = Specification.where(null);
        if (!ObjectUtils.isEmpty(postIndex)) {
            spec = spec.and(AddressSpecifications.postIndexEq(postIndex));
        }
        if (!StringUtils.isEmpty(country)) {
            spec = spec.and(AddressSpecifications.countryEq(country.toUpperCase()));
        }
        if (!StringUtils.isEmpty(city)) {
            spec = spec.and(AddressSpecifications.cityEq(city.toUpperCase()));
        }
        if (!StringUtils.isEmpty(street)) {
            spec = spec.and(AddressSpecifications.streetEq(street.toUpperCase()));
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
    public Address strongFind(Address address) {
        Specification<Address> spec = Specification.where(null);
        if (!ObjectUtils.isEmpty(address.getIndex())) {
            spec = spec.and(AddressSpecifications.postIndexEq(address.getIndex().toString()));
        } else {
            spec = spec.and(AddressSpecifications.postIndexIsNull());
        }
        if (!StringUtils.isEmpty(address.getCountry())) {
            spec = spec.and(AddressSpecifications.countryEq(address.getCountry().toUpperCase()));
        }
        if (!StringUtils.isEmpty(address.getCity())) {
            spec = spec.and(AddressSpecifications.countryEq(address.getCountry().toUpperCase()));
        }
        if (!StringUtils.isEmpty(address.getStreet())) {
            spec = spec.and(AddressSpecifications.streetEq(address.getStreet().toUpperCase()));
        }
        if (!ObjectUtils.isEmpty(address.getHouseNumber())) {
            spec = spec.and(AddressSpecifications.houseNumberEq(address.getHouseNumber()));
        } else {
            spec = spec.and(AddressSpecifications.houseNumberEq(""));
        }
        if (!ObjectUtils.isEmpty(address.getApartmentNumber())) {
            spec = spec.and(AddressSpecifications.apartmentNumberEq(address.getApartmentNumber()));
        } else {
            spec = spec.and(AddressSpecifications.apartmentNumberEq(""));
        }
        return addressRepository.findOne(spec).orElse(null);
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

        String newCountry = ContragentUtils.toUpperCase(adr.getCountry());
        String newCity = ContragentUtils.toUpperCase(adr.getCity());
        String newStreet = ContragentUtils.toUpperCase(adr.getStreet());

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
        });
    }


    /**
     * We check the address for presence in the database.
     * If the address exists, we take it, if it does not exist, we save it
     *
     * @param address address to check and save
     * @return Address the address was found or saved
     */
    private Address checkDuplicateAndGetAddress (Address address) {
        Address findAddress = strongFind(address);
        Address correctAddress;

        if (findAddress != null) {
            correctAddress = findAddress;
        } else {
            correctAddress = addressRepository.save(ContragentUtils.normalizeAddress(address));
        }

        return correctAddress;
    }
}
