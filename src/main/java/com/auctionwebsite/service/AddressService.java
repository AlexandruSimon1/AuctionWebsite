package com.auctionwebsite.service;

import com.auctionwebsite.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(int id);

    AddressDTO createAddress(AddressDTO addressDTO);

    AddressDTO updateAddressById(AddressDTO addressDTO, int id);

    AddressDTO deleteAddressById(int id);

    List<AddressDTO> findAllAddressByUserId(int userId);
}
