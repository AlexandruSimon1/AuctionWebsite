package com.auctionwebsite.dto;

import lombok.Data;

@Data
public class PurchasingDTO {
    private int id;
    private AuctionDTO auctionDTO;
    private UserDTO userDTO;
}
