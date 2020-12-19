package com.auctionwebsite.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AuctionDTO {
    private int id;
    private String title;
    private String description;
    private String photos;
    private int minimumPrice;
    private int buyNow;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CategoryDTO categoryDTO;
    private List<BiddingDTO> biddingDTOList;
    private PurchasingDTO purchasingDTO;
}
