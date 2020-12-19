package com.auctionwebsite.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String name;
    private Date creationDate;
    private String type;
    private AddressDTO addressDTO;
}
