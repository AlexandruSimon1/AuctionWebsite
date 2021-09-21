package com.auctionwebsite.service;

import com.auctionwebsite.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUserById(UserDTO userDTO, Long id);

    UserDTO deleteUserById(Long id);

    UserDTO getUserByEmail(String email) throws Exception;
}
