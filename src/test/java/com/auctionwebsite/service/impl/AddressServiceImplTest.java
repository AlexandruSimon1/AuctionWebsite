package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.AddressDTO;
import com.auctionwebsite.model.Address;
import com.auctionwebsite.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {
    private static final int ID_VALUE = 1;
    private Address secondAddress;
    private Address firstAddress;
    @InjectMocks
    private AddressServiceImpl addressService;
    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressService = new AddressServiceImpl(addressRepository);

        firstAddress = new Address();
        firstAddress.setId(ID_VALUE);
        firstAddress.setCity("Douala");
        firstAddress.setProvince("Cameron");
        firstAddress.setAddress("United");

        secondAddress = new Address();
        secondAddress.setId(ID_VALUE);
        secondAddress.setCity("Douala");
        secondAddress.setProvince("Cameron");
        secondAddress.setAddress("United");
    }

    @Test
    void getAllAddresses() {
        //given
        List<Address> getAllAddresses = new ArrayList<>();
        getAllAddresses.add(firstAddress);
        getAllAddresses.add(secondAddress);
        //when
        when(addressRepository.findAll()).thenReturn(getAllAddresses);
        List<AddressDTO> addressDTOS = addressService.getAllAddresses();
        //then
        assertEquals(getAllAddresses.size(), addressDTOS.size());
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    void getAddressById() {
        //when
        when(addressRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstAddress));
        AddressDTO addressDTO = addressService.getAddressById(ID_VALUE);
        //then
        assertEquals(firstAddress.getId(), addressDTO.getId());
        assertEquals(firstAddress.getAddress(), addressDTO.getAddress());
        assertEquals(firstAddress.getCity(), addressDTO.getCity());
        assertEquals(firstAddress.getProvince(), addressDTO.getProvince());
        verify(addressRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteAddressById() {
        //when
        when(addressRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstAddress));
        addressRepository.deleteById(ID_VALUE);
        //then
        verify(addressRepository, times(1)).deleteById(ID_VALUE);
        AddressDTO addressDTO = addressService.deleteAddressById(ID_VALUE);
        assertEquals(firstAddress.getId(), addressDTO.getId());
        assertEquals(firstAddress.getAddress(), addressDTO.getAddress());
        assertEquals(firstAddress.getCity(), addressDTO.getCity());
        assertEquals(firstAddress.getProvince(), addressDTO.getProvince());
    }

    @Test
    void updateAddressById() {
        //given
        AddressDTO dto = new AddressDTO();
        dto.setId(1);
        dto.setCity("Douala");
        dto.setProvince("Cameron");
        dto.setAddress("United");
        //when
        when(addressRepository.findById(ID_VALUE)).thenReturn(Optional.of(firstAddress));
        when(addressRepository.save(firstAddress)).thenReturn(firstAddress);
        AddressDTO updatedAddress = addressService.updateAddressById(dto, ID_VALUE);
        //then
        assertNotNull(updatedAddress);
        assertEquals(firstAddress.getId(), updatedAddress.getId());
        assertEquals(firstAddress.getAddress(), updatedAddress.getAddress());
        assertEquals(firstAddress.getCity(), updatedAddress.getCity());
        assertEquals(firstAddress.getProvince(), updatedAddress.getProvince());
        verify(addressRepository, times(1)).save(firstAddress);
    }

//    @Test
//    void createAddress() {
//        //given
//        AddressDTO dto = new AddressDTO();
//        dto.setId(1);
//        dto.setCity("Douala");
//        dto.setProvince("Cameron");
//        dto.setAddress("United");
//        //when
//        when(addressRepository.save(firstAddress)).thenReturn(firstAddress);
//        AddressDTO addressDTO = addressService.createAddress(dto);
//        //then
//        assertNotNull(firstAddress);
//        assertEquals(firstAddress.getId(), addressDTO.getId());
//        assertEquals(firstAddress.getAddress(), addressDTO.getAddress());
//        assertEquals(firstAddress.getCity(), addressDTO.getCity());
//        assertEquals(firstAddress.getProvince(), addressDTO.getProvince());
//    }
}
