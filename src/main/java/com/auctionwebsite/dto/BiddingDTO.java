package com.auctionwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class BiddingDTO {
    private int id;
    private AuctionDTO auction;
    @JsonIgnoreProperties(value = { "bidding", "purchasing" }, ignoreUnknown = true)
    private UserDTO user;
}
