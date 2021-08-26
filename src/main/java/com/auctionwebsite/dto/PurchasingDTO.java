package com.auctionwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PurchasingDTO {
    private int id;
    private AuctionDTO auction;
    @JsonIgnore
    private UserDTO user;
}
