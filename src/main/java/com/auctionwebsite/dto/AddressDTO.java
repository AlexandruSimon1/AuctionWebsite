package com.auctionwebsite.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private int id;
    private String province;
    private String city;
    private String address;
}
