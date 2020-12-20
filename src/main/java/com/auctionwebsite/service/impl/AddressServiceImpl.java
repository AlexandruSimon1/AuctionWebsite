package com.auctionwebsite.service.impl;

import com.auctionwebsite.dto.AddressDTO;
import com.auctionwebsite.exception.ApplicationException;
import com.auctionwebsite.exception.ExceptionType;
import com.auctionwebsite.mapper.AddressMapper;
import com.auctionwebsite.mapper.NotificatorMappingContext;
import com.auctionwebsite.mapper.UserMapper;
import com.auctionwebsite.model.Address;
import com.auctionwebsite.repository.AddressRepository;
import com.auctionwebsite.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(address -> AddressMapper.INSTANCE.toAddressDto(address, new NotificatorMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(int id) {
        final Address getAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        return AddressMapper.INSTANCE.toAddressDto(getAddress, new NotificatorMappingContext());
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        final Address createAddress = AddressMapper.INSTANCE.fromAddressDto(addressDTO, new NotificatorMappingContext());
        final Address saveAddress = addressRepository.save(createAddress);
        return AddressMapper.INSTANCE.toAddressDto(saveAddress, new NotificatorMappingContext());
    }

    @Override
    public AddressDTO updateAddressById(AddressDTO addressDTO, int id) {
        final Address updateAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        updateAddress.setUser(UserMapper.INSTANCE.fromUserDto(addressDTO.getUserDTO(), new NotificatorMappingContext()));
        updateAddress.setCity(addressDTO.getCity());
        updateAddress.setProvince(addressDTO.getAddress());
        updateAddress.setAddress(addressDTO.getAddress());
        addressRepository.save(updateAddress);
        return AddressMapper.INSTANCE.toAddressDto(updateAddress, new NotificatorMappingContext());
    }

    @Override
    public AddressDTO deleteAddressById(int id) {
        final Address deleteAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionType.ADDRESS_NOT_FOUND));
        return AddressMapper.INSTANCE.toAddressDto(deleteAddress, new NotificatorMappingContext());
    }
}
