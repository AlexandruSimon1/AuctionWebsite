package com.auctionwebsite.controller;

import com.auctionwebsite.dto.BiddingDTO;
import com.auctionwebsite.dto.PurchasingDTO;
import com.auctionwebsite.dto.UserDTO;
import com.auctionwebsite.service.BiddingService;
import com.auctionwebsite.service.PurchasingService;
import com.auctionwebsite.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.LazyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final BiddingService biddingService;
    private final PurchasingService purchasingService;

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
    @PatchMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUserById(@PathVariable int userId, @RequestBody UserDTO userDTO) {
        return userService.updateUserById(userDTO, userId);
    }

    //Mapping name
    @DeleteMapping(value = "/{userId}")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDTO deleteUserById(@PathVariable int userId) {
        return userService.deleteUserById(userId);
    }

    //Mapping name
    @GetMapping(value = "/{userId}/bidding")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<BiddingDTO> getBiddingByUserId(@PathVariable int userId) {
        return biddingService.findAllBiddingByUserId(userId);
    }

    //Mapping name
    @GetMapping(value = "/{userId}/purchasing")
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    public List<PurchasingDTO> getPurchasesByUserId(@PathVariable int userId) {
        return purchasingService.findAllPurchasingByUserId(userId);
    }
}
