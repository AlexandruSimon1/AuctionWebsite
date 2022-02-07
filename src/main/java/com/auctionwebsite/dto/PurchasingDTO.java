package com.auctionwebsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class PurchasingDTO {
    private int id;
    private AuctionDTO auction;
    @JsonIgnoreProperties(value = "password,email")
    private UserDTO user;
}
