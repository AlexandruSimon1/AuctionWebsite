package com.auctionwebsite.dto;

import lombok.Data;

@Data
public class BiddingDTO {
    private int id;
    private AuctionDTO auctionDTO;
    private UserDTO userDTO;
}
