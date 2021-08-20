package com.auctionwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class BiddingDTO {
    private int id;
    private AuctionDTO auction;
    private UserDTO user;
}
