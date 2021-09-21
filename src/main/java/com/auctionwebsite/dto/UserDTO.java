package com.auctionwebsite.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime creationDate;
    private String type;
    private List<AddressDTO> addresses;
    private Set<RoleDTO> role = new HashSet<>();
}
