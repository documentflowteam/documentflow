package com.documentflow.services;

import com.documentflow.AbstractContragentTest;
import com.documentflow.entities.Address;
import com.documentflow.entities.Contragent;
import com.documentflow.entities.dto.ContragentDtoAddress;
import com.documentflow.exceptions.NotFoundAddressException;
import com.documentflow.repositories.AddressRepository;
import com.documentflow.repositories.ContragentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Test for {@link AddressService}
 */
public class AddressServiceImplTest extends AbstractContragentTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContragentRepository contragentRepository;

    @Test
    public void testSaveAddress() {
        Address newAddress = createRandomAddress();

        List<Address> listResultNotFound = addressService.findAll(newAddress.getIndex().toString(),
                newAddress.getCountry(),
                newAddress.getCity(),
                newAddress.getStreet(),
                newAddress.getHouseNumber(),
                newAddress.getApartmentNumber());

        Assert.assertEquals(Collections.emptyList(), listResultNotFound);

        Address address = addressService.save(newAddress);

        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testSaveNullAddress() {
        addressService.save((Address) null);
    }

    @Test(expected = NullPointerException.class)
    public void testSaveAddressNotValidNotCountry() {
        Address addressNotCountry = Address.builder()
                .city("city")
                .street("street")
                .build();
        addressService.save(addressNotCountry);
    }

    @Test(expected = NullPointerException.class)
    public void testSaveAddressNotValidNotCity() {

        Address addressNotCity = Address.builder()
                .country("country")
                .street("street")
                .build();
        addressService.save(addressNotCity);
    }

    @Test(expected = NullPointerException.class)
    public void testSaveAddressNotValidNotStreet() {

        Address addressNotStreet = Address.builder()
                .country("country")
                .city("city")
                .build();
        addressService.save(addressNotStreet);
    }

    @Test
    public void testSaveContragentDtoAddress() {
        ContragentDtoAddress[] dtoAddresses = new ContragentDtoAddress[10];
        for (int i = 0; i < 10; i++) {
            dtoAddresses[i] = createRandomDtoAddress();
        }
        List<Address> addresses = addressService.save(dtoAddresses);

        Assert.assertNotNull(addresses);
        Assert.assertEquals(10, addresses.size());

        for (int i = 0; i < 10; i++) {
            Assert.assertNotNull(addresses.get(i).getId());
            Assert.assertEquals(dtoAddresses[i].getCountry().toUpperCase(), addresses.get(i).getCountry());
            Assert.assertEquals(dtoAddresses[i].getCity().toUpperCase(), addresses.get(i).getCity());
            Assert.assertEquals(dtoAddresses[i].getStreet().toUpperCase(), addresses.get(i).getStreet());
            Assert.assertEquals(dtoAddresses[i].getHouseNumber(), addresses.get(i).getHouseNumber());
            Assert.assertEquals(dtoAddresses[i].getApartrmentNumber(), addresses.get(i).getApartmentNumber());
        }
    }

    @Test
    public void testUpdateAddress() {
        Address address = createAndSaveRandomAddress();

        Address updateAddress = Address.builder()
                .id(address.getId())
                .index(address.getIndex())
                .country("COUNTRY")
                .city("CITY")
                .street("STREET")
                .houseNumber(address.getHouseNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();

        Address updatedAddress = addressService.save(updateAddress);

        Assert.assertEquals(address.getId(), updatedAddress.getId());
        Assert.assertEquals(updatedAddress.getCountry(), address.getCountry().toUpperCase());
        Assert.assertEquals(updatedAddress.getCity(), address.getCity().toUpperCase());
        Assert.assertEquals(updatedAddress.getStreet(), address.getStreet().toUpperCase());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNullAddress() {
        addressService.update(null);
    }

    @Test(expected = NotFoundAddressException.class)
    public void testUpdateNotFoundAddress() {
        Address address = createAndSaveRandomAddress();
        address.setId(0L);
        addressService.update(address);
    }

    @Test
    public void testDeleteAddress() {
        Address address = createAndSaveRandomAddress();

        addressService.delete(address.getId());
        Optional<Address> optionalAddress = addressRepository.findById(address.getId());

        Assert.assertTrue(optionalAddress.isPresent());
        Assert.assertEquals(optionalAddress.get().getContragents().get(0).getIsDeleted(), true);
    }

    @Test(expected = NotFoundAddressException.class)
    public void testDeleteNotFoundAddress() {
        addressService.delete(0L);
    }

    @Test()
    public void testFindAll() {
        Address address = createAndSaveRandomAddress();
        Contragent contragent = address.getContragents().get(0);
        List<Address> addresses = addressService.findAll(
                null,
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                null,
                null
        );

        Assert.assertTrue(addresses.contains(address));

        addresses = addressService.findAll(
                null,
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                null,
                address.getApartmentNumber()
        );

        Assert.assertTrue(addresses.contains(address));

        addresses = addressService.findAll(
                null,
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getApartmentNumber()
        );

        Assert.assertTrue(addresses.contains(address));

        //Delete address
        contragent.setIsDeleted(true);
        contragentRepository.save(contragent);

        addresses = addressService.findAll(
                null,
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                null,
                null
        );

        Assert.assertEquals(Collections.emptyList(), addresses);
    }

    @Test()
    public void testStrongFind() {
        Address address = createAndSaveRandomAddress();
        Address foundAddress = addressService.strongFind(address);

        Assert.assertEquals(address, foundAddress);

        Address searchAddress = Address.builder()
                .index(null)
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();

        Address notFoundAddress = addressService.strongFind(searchAddress);

        Assert.assertNull(notFoundAddress);
    }
}
