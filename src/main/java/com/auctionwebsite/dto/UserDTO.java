package com.auctionwebsite.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime creationDate;
    private String type;
    private List<AddressDTO> addresses;
    private String role;
}
