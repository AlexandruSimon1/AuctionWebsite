package com.auctionwebsite.controller;

import com.auctionwebsite.dto.UserDTO;
import com.auctionwebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private final UserService userService;

    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    //Mapping name
    @GetMapping(value = "/{userId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    //Mapping name
    @PostMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    //Mapping name
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable int userId, @RequestBody UserDTO userDTO) {
        return userService.updateUserById(userDTO, userId);
    }

    //Mapping name
    @DeleteMapping(value = "/{userId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDTO deleteUserById(@PathVariable int userId) {
        return userService.deleteUserById(userId);
    }
}
