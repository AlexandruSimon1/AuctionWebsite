package com.auctionwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    @JsonIgnore
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date creationDate;
    private String type;
    private List<AddressDTO> addresses;
    private Set<RoleDTO> role = new HashSet<>();
}
