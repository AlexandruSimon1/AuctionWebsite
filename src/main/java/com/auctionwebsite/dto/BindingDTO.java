package com.auctionwebsite.dto;

import lombok.Data;

@Data
public class BindingDTO {
    private int id;
    private AuctionDTO auction;
    private UserDTO user;
}
