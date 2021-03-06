package com.auctionwebsite.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String name;
    private Date creationDate;
    private String type;
    private AddressDTO address;
    private List<BiddingDTO> bidding;
    private List<PurchasingDTO> purchasing;
    private String role;
}
