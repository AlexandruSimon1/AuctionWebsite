package com.auctionwebsite.service;

import com.auctionwebsite.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(int id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUserById(UserDTO userDTO, int id);

    UserDTO deleteUserById(int id);
}
