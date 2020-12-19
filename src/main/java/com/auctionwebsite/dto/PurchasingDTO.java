package com.auctionwebsite.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchasingDTO {
    private int id;
    private AuctionDTO auctionDTO;
    private List<UserDTO> userDTOList;
}
