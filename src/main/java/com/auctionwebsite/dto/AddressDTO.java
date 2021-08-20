package com.auctionwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AddressDTO {
    private int id;
    private String province;
    private String city;
    private String address;
    @JsonIgnore
    private UserDTO userDTO;
}
