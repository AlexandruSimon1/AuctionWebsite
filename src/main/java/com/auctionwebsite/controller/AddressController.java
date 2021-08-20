package com.auctionwebsite.controller;

import com.auctionwebsite.dto.AddressDTO;
import com.auctionwebsite.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final AddressService addressService;

    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO getAddressById(@PathVariable int addressId) {
        return addressService.getAddressById(addressId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO createAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(addressDTO);
    }

    @PatchMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO updateAddressById(@PathVariable int addressId, @RequestBody AddressDTO addressDTO) {
        return addressService.updateAddressById(addressDTO, addressId);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AddressDTO deleteAddressById(@PathVariable int addressId) {
        return addressService.deleteAddressById(addressId);
    }
}
